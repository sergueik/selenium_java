### Info

This directory contains a combined replica of xml2json,json2xml exersize project [uk0/xml2json](https://github.com/uk0/xml2json)
project with unwanted extra jar dependencies to qualify for a locked environment when only jars already available in tomcat app directory, can be used and a real no dependencies example [tetsurom/xml2json](https://github.com/tetsurom/xml2json)
This relies heavily on `net.sf.json.JSONSerializer.toJSON` [](https://github.com/jenkinsci/json-lib/blob/master/src/main/java/net/sf/json/JSONSerializer.java) which has a number of additional dependencies.

### Usage

```sh
mvn clean package
java -jar target/app.jar example.json
```
or
```sh
java -cp target\lib\*;target\xml2json-2.0-SNAPSHOT.jar example.JSON2Xml example.json > example.xml
```
followed by
```sh
xmlstarlet fo example.xml
```
and
```sh
java -cp target/app.jar example.Xml2JSON example.xml  | tee  output.json
```
followed by
```sh
jq "." output.json
```
NOTE: the resulting XML appears to be  quite noisy

### See Also:
  * https://github.com/jenkinsci/json-lib
  * standalone JSON object [(de)serialization](https://github.com/nayuki/JSON-library-Java)

