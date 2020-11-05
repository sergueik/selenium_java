### Info
Example exteral jar-clean java class for the Java driven Tomcat hosted node __XML__ configuration management

### Usage
#### Basic
```sh
mvn clean package
```

#### Legacy Invocation 

Probably only useful for debugging and adding new features

```sh
java -cp target/example.xslt.jar example.MergeDocumentFragments -in web.xml -out output.xml
```
inspect the modifications made
```sh
diff -w web.xml output.xml
```
on Windows use 
```cmd
windiff.exe web.xml output.xml
```
```sh
7a8,15
> <filter>
>   <filter-name>responseHeadersFilter</filter-name>
>   <filter-class>example.ResponseHeadersFilter</filter-class>
>   <init-param>
>     <param-name>Expires</param-name>
>     <param-value>0</param-value>
>   </init-param>
> </filter>
13a22,25
> <filter-mapping>
>    <filter-name>responseHeadersFilter</filter-name>
>    <url-pattern>/*</url-pattern>
> </filter-mapping>
```
This will put a XML comment around the selected node removing it from catalina configuration
```sh
java -cp target/example.xslt.jar example.CommentNode -in web.xml -out web_output.xml -name responseHeadersFilter -debug
transforming web.xml with embedded stylesheet
Loaded: web.xml
Written: web_output.xml
```
This will add DOM fragments `filter` and `filter-mapping` nodes from the resorce `fragment.xml` embedded in the Jar
```
```sh
java -cp target/example.xslt.jar example.MergeDocumentFragments -in web.xml -out output.xml -debug
```

It validates the presence of the node before adding it:

```sh
java -cp target/example.xslt.jar example.MergeDocumentFragments -in output.xml -out output2.xml -debug
```
```sh
Loaded: file:///C:/developer/sergueik/selenium_java/xslt-example/output.xml

Exploring node: filter
Exploring node: filter
Exploring node: filter
Already have the node: //filter/filter-name[text()="responseHeadersFilter"]
```

```xml
<filter xmlns:xxx="http://xmlns.jcp.org/xml/ns/javaee">
  <filter-name>httpHeaderSecurity</filter-name>
  <filter-class>org.apache.catalina.filters.HttpHeaderSecurityFilter</filter-class>
  <async-supported>true</async-supported>
</filter>
```
this will remove specific node altogether
```sh
java -cp target\example.xslt.jar example.RemoveNode -in web_output.xml -tag1 filter -tag3 filter-name -name responseHeadersFilter -debug -out web_output2.xml
```
verify with
```sh
WinDiff.Exe web_output .xml web_output2.xml
```

#### Running as Jar

Alternatively run

```sh
java -jar target\example.xslt.jar -op add -in web.xml -out output.xml -debug
```
this will log:
```sh
Loaded: file:///C:/developer/sergueik/selenium_java/xslt-example/web.xml
Exploring node: filter
Testing local file: jar:file:/C:/developer/sergueik/selenium_java/xslt-example/t
arget/example.xslt.jar!/fragment.xml
Exploring node: filter
Subnode list length: 2
Exploring nested node: filter-name
Found text failedRequestFilter (index 0)
Added filter
Exploring node: filter-mapping
Subnode list length: 3
Exploring nested node: filter-name
Found text failedRequestFilter (index 0)
Added filter-mapping
Written: output.xml
Done: add
```
Yet another alternative is to pass argument, e.g. op via environment like

```sh
export OP=add
java -jar target\example.xslt.jar -op env:op -in web.xml -out output.xml -debug
```

then
```cmd
java -jar target\example.xslt.jar -op comment -in output.xml -out output2.xml -debug
```
which will log
```sh
transforming output.xml with embedded stylesheet
Loaded: output.xml
Written: output2.xml
```
then

```cmd
java -jar target\example.xslt.jar -op transform -in output.xml -out output2.xml -debug -xsl src\main\resources\
```
which will log
```sh
transforming output.xml with src\main\resources\create_node.xsl
Loaded: output.xml
Written: output2.xml
Done: transform
```
which will  create a DOM node with undesired `xmlns:xxx` namespace-like attribute:
```xml
<filter-mapping xmlns:xxx="http://xmlns.jcp.org/xml/ns/javaee">
<filter-name>customFilter</filter-name>
<url-pattern>/*</url-pattern>
<dispatcher>REQUEST</dispatcher>
</filter-mapping>
#### Verbatim Data
```
The best way to add a DOM tree fragment of application-specific nodes to XML is by applying the style sheet with a lot of fard coded data. say  one intends to add `` to catalina `web.xml`:

```xml
<filter>
  <filter-name>customFilter</filter-name>
  <filter-class>example.CustomFilter</filter-class>
  <async-supported>true</async-supported>
</filter>
```
and
```xml
<filter-mapping>
  <filter-name>customFilter</filter-name>
  <url-pattern>/*</url-pattern>
  <dispatcher>REQUEST</dispatcher>
</filter-mapping>
```
After aplying
templates
```xml
  <xsl:template match="xxx:filter-mapping[last()]">

    <filter-mapping>
      <filter-name>customFilter</filter-name>
      <url-pattern>/*</url-pattern>
      <dispatcher>REQUEST</dispatcher>
    </filter-mapping>
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
```
to inject the custom filter-mapping DOM  fragment before the last `filter-mapping` node
and
```xml
  <xsl:template match="xxx:filter[xxx:filter-name[text()='httpHeaderSecurity']]">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
    <filter>
      <filter-name>customFilter</filter-name>
      <filter-class>example.CustomFilter</filter-class>
      <async-supported>true</async-supported>
    </filter>
  </xsl:template>
```
toappent the filter `customFilter` after the filter named `httpHeaderSecurity`
and
running the vanilla command:
```sh
java -cp target/example.xslt.jar example.App web.xml create_node.xsl web_output.xml
```
the following extraneous attribute will be found:
```
<filter-mapping xmlns:xxx="http://xmlns.jcp.org/xml/ns/javaee">
  <filter-name>customFilter</filter-name>
  <url-pattern>/*</url-pattern>
  <dispatcher>REQUEST</dispatcher>
</filter-mapping>
```
in both DOM fragments.
No known way to get rid of it atm without pathcing the tool (work in progress).
#### Non-working
`transform.xsl`:



```xml
<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:uuid="java.util.UUID" version="2.0" exclude-result-prefixes="uuid">
  <xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes"/>
  <xsl:template match="/">
    <xsl:for-each select="//student">
      <xsl:copy-of select="."/>
      <output>
        <xsl:variable name="random" select="uuid:randomUUID()"/>
        <xsl:value-of select="$random"/>
      </output>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
```

`example.xml`:

```xml
<?xml version="1.0"?>
<class>
  <student no="1">
    <name>Linus</name>
  </student>
  <student no="2">
    <name>Stallman</name>
  </student>
</class>
```

```sh
mvn install
java -cp target\example.xslt.jar example.App example.xml transform.xsl output.xml
```

`output.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
 <student no="1">
   <name>Linus</name>
 </student>
 <output>2f5ed0c2-1b90-45ad-9445-42334d62690f</output>
 <student no="2">
   <name>Stallman</name>
 </student>
 <output>f4224811-6795-41ee-b4ab-5cb013330aed</output>
```
the [Saxon jar](https://www.saxonica.com/documentation9.5/using-xsl/commandline.html) is supposed to be
[runnable](https://stackoverflow.com/questions/4604497/xslt-processing-with-java) but it refuses to:


```sh
java -jar target\lib\Saxon-HE-9.9.1-6.jar -repeat:1 -o:output.xml -s:example.xml -xsl:transform.xsl
```
fails with
```sh
Static error in {uuid:randomUUID()} in expression in xsl:variable/@select on line 8 column 65 of transform.xsl:
  XPST0017: Cannot find a 0-argument function named Q{java.util.UUID}randomUUID(). Reflexive calls to Java methods are not available under Saxon-HE
Errors were reported during stylesheet compilation
```

### New Features

To pass long list of arguments, the `CommandLineParser` class allows using `YAML` file as the `-apply` argument in a kubernetes style,with the following sytnax:
given the YAMLfile `test.yaml` with contents
```YAML
---
a: 1
b: true
c: "string"
```
```sh
java -cp target\example.xslt.jar;target\lib\* example.ManageConfig -apply @test.yaml -debug
```
this will priduce the debugging info:
```sh
key: a value: 1
key: b value: true
key: c value: string
```
### See Also

  *  transforming XML to JSON using [XSLT2JSON](https://github.com/bramstein/xsltjson)
  * amendment to [XSLT template](https://stackoverflow.com/questions/4964152/xslt-script-doesnt-work-when-a-namespace-is-declared-in-the-root-node) when transfrorm script doesn't work becase some namespace is declared in the root node of the subject
  * another discussion of [the same](https://stackoverflow.com/questions/1344158/xslt-with-xml-source-that-has-a-default-namespace-set-to-xmlns)
  * wrap a comment around [specific node](https://stackoverflow.com/questions/17607602/comment-a-single-node)
  * locating and exposing contents of [comments](https://stackoverflow.com/questions/3837169/how-do-i-select-all-comment-nodes-in-an-xml-file)
  * [deep copy](https://stackoverflow.com/questions/1769194/xslt-deep-child-copy)
  * [Streaming and Complex Event Processing engine](https://github.com/siddhi-io/siddhi)
