#!/bin/env bash
MODULE='example.app'
if [[ $JAVA_HOME = "" ]] ; then
  D=$(dirname $(readlink $(readlink $(which javac))))
  pushd $D
  cd ..
  JAVA_HOME=$(pwd)
  popd
fi
printf 'Using JAVA_HOME=%s\n' $JAVA_HOME
jlink --module-path mods/:$JAVA_HOME/jmods --add-modules $MODULE --output "${MODULE}-image"
# Error: Module java.base not found, required by example.app
