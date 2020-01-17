### Info

This directory contains a combined replica of xml2json,json2xml exersize projct [uk0/xml2json](https://github.com/uk0/xml2json)
project with unwanted extra jar dependencies to qualify for a locked environment when only jars already available in tomcat app directory, can be used and a real no dependencies example [tetsurom/xml2json](https://github.com/tetsurom/xml2json)

### Usage 

```sh
mvn clean package
java -cp target/xml2json-1.0-SNAPSHOT.jar xml2json.Program pom.xml example.json
jq "." example.json
```



