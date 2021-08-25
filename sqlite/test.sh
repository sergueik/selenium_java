#!/bin/sh
mvn  -Dmaven.test.skip=true clean install
PACKAGE=sqlite_test
CLASS=${1:-FileDatabase}
echo "testing $CLASS"
java -cp target/$PACKAGE-0.2-SNAPSHOT.jar:target/lib/*  $PACKAGE.$CLASS

