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
java -cp target\xslt-0.0.1-SNAPSHOT.jar example.App example.xml transform.xsl output.xml
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
the [Saxon jar](https://www.saxonica.com/documentation9.5/using-xsl/commandline.html) is supposed to be [runnable](https://stackoverflow.com/questions/4604497/xslt-processing-with-java) but it refuses to:


```sh
java -jar target\lib\Saxon-HE-9.9.1-6.jar -repeat:1 -o:output.xml -s:example.xml -xsl:transform.xsl
```
fails with
```sh
Static error in {uuid:randomUUID()} in expression in xsl:variable/@select on line 8 column 65 of transform.xsl:
  XPST0017: Cannot find a 0-argument function named Q{java.util.UUID}randomUUID(). Reflexive calls to Java methods are not available under Saxon-HE
Errors were reported during stylesheet compilation
```

### See Also

 * 
