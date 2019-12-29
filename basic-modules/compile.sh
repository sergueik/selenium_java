#!/bin/env bash

java -version 2>&1 | grep -q '11.'
if [ $? -ne 0 ] ; then
  echo 'Failed to find the good version of Java'
fi
javac -d build --module-source-path src $(find src -name "*.java")
