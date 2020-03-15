### Info

This directory contains a combined replica of xml2json,json2xml exersize project [uk0/xml2json](https://github.com/uk0/xml2json)
project with unwanted extra jar dependencies to qualify for a locked environment when only jars already available in tomcat app directory, can be used and a real no dependencies example [tetsurom/xml2json](https://github.com/tetsurom/xml2json) 
This relies heavily on 
`net.sf.json.JSONSerializer.toJSON` [](https://github.com/jenkinsci/json-lib/blob/master/src/main/java/net/sf/json/JSONSerializer.java)

### Usage

```sh
mvn clean install
java -cp target/xml2json-1.0-SNAPSHOT.jar xml2json.Program pom.xml example.json
jq "." example.json
```

```sh
java -cp target\lib\*;target\xml2json-1.0-SNAPSHOT.jar example.XmlTest example.json > example.xml
```
NOTE: the resulting XML appears to be  quite noisy

### See Also:
  * https://github.com/jenkinsci/json-lib
  * https://github.com/nayuki/JSON-library-Java/blob/master/src/io/nayuki/json/JsonTest.java
  * https://github.com/nayuki/JSON-library-Java
