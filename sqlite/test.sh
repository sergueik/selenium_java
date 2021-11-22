#!/bin/sh
mvn  -Dmaven.test.skip=true clean install
GROUP='example'
ARTIFACT='sqlite'
PACKAGE=$GROUP.$ARTIFACT

CLASS=${1:-FileDatabase}
echo "testing $CLASS"
java -cp target/$PACKAGE.jar:target/lib/*  $GROUP.$CLASS

