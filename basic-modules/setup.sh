#!/bin/env bash

# sudo yum install -q -y java-1.8.0-openjdk
sudo yum install -q -y java-11-openjdk java-11-openjdk-devel
sudo yum erase -q -y java-1.8.0-openjdk java-1.8.0-openjdk-headless
java -version 2>&1 | grep -q '11.'
if [ $? -ne 0 ] ; then
  echo 'Failed to erase Java 1.8'
fi
