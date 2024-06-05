#!/bin/sh

mvn -f ./pom.xml clean package
java -cp target/example.javafx_markdown.jar:target/lib/* example.Example
