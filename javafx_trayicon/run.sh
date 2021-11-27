#!/bin/sh
CLASS=ChartEx
mvn clean package
echo 'Launching with classpath  + modulepath semantics'
java -Dprism.order=sw -cp target/treeview.jar:target/lib.* \
--module-path target/lib \
--add-modules=javafx.controls,javafx.base,javafx.graphics,commons.configuration,commons.csv,org.apache.logging.log4j \
example.$CLASS -resource data.json -type json
