#!/bin/sh

mvn -f ./pom-java11.xml clean package
echo 'Launching with classpath + modulepath semantics'
java -Dprism.order=sw -cp target/example.javafx_markdown.jar:target/lib.* \
--module-path target/lib \
--add-modules=javafx.controls,javafx.base,javafx.graphics,commons.configuration \
example.Example
# surprisingly, observed the error
# java.lang.module.FindException: Module org.apache.logging.log4j not found
# when log4j jar was included in pom-java11.xml but unused in application
