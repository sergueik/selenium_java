#!/usr/bin/env bash
VERSION='0.1.0-SNAPSHOT'
JAR="mongodb_transaction-${VERSION}-jar-with-dependencies.jar"
MAIN_CLASS='example.ChangeStreams'
java -cp target/$JAR $MAIN_CLASS mongodb://localhost/test
