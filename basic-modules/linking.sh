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
#  TODO: this is Java 9 module path
# jlink --module-path mods/:$JAVA_HOME/jmods --add-modules $MODULE --output "${MODULE}-image"
# Error: Module java.base not found, required by example.app
# https://dzone.com/articles/java-9-modules-introduction-part-1
# java.base is always implicitly added
# java --list-modules 2>&1 | grep base | sed 's|\@[0-9][0-9.]*$||'
# find $JAVA_HOME -iname $(java --list-modules 2>&1 | grep base | sed  's|\@[0-9][0-9.]*$||')

jlink --module-path mods/:$JAVA_HOME/lib/ --add-modules $MODULE,java.base --output "${MODULE}-image"
# still can not find it:
# Error: Module java.base not found

# strings build/example.greetings/module-info.class
# SourceFile
# module-info.java
# Module
# 11.0.5
# module-info
# example.greetings
#         java.base
# example/greetings
#
# jar xvf mods/example.app\@1.0.jar module-info.class; strings module-info.class
#  inflated: module-info.class
# module-info
# module-info.java
# example.app
# ModuleMainClass
# example/app/ModuleApp
# ModulePackages
# example/app
#         java.base
# 11.0.5
# example.greetings
# SourceFile
# Module
#
