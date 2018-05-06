#!/bin/bash
# set -x

JAVA_VERSION='1.8.0_121'
MAVEN_VERSION='3.3.9'

PACKAGE_NAME='swt_node_selector_sample'
PACKAGE_VERSION='0.0.1-SNAPSHOT'

MAIN_APP_PACKAGE='example'
MAIN_APP_CLASS=${1:-ConfigFormEx}

if $(uname -s | grep -qi Darwin)
then
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk$JAVA_VERSION.jdk/Contents/Home
  export M2_HOME="$HOME/Downloads/apache-maven-$MAVEN_VERSION"
  export M2="$M2_HOME/bin"
  export MAVEN_OPTS='-Xms256m -Xmx512m'
  export PATH=$M2_HOME/bin:$PATH
  # http://stackoverflow.com/questions/3976342/running-swt-based-cross-platform-jar-properly-on-a-mac
  LAUNCH_OPTS='-XstartOnFirstThread'
fi
mvn -Dmaven.test.skip=true package install
java $LAUNCH_OPTS -cp target/$PACKAGE_NAME-$PACKAGE_VERSION.jar:target/lib/* $MAIN_APP_PACKAGE.$MAIN_APP_CLASS
