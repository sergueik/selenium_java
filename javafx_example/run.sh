#!/bin/bash
# set -x

PACKAGE='javafx_example_app'
SKIP_PACKAGE_VERSION=true
PACKAGE='example'
if [[ $SKIP_PACKAGE_VERSION ]] ; then
  PACKAGE='javafx_example_app'
  APP_JAR="$PACKAGE_NAME.jar"
else
  PACKAGE='javafx_example'
  APP_VERSION='0.0.1-SNAPSHOT'
  APP_JAR="$PACKAGE-$APP_VERSION.jar"
fi

DEFAULT_MAIN_CLASS='FlowPaneEx'

which xmllint > /dev/null

if [  $? -eq  0 ] ; then
  APP_VERSION=$(xmllint -xpath "/*[local-name() = 'project' ]/*[local-name() = 'version' ]/text()" pom.xml)
  PACKAGE=$(xmllint -xpath "/*[local-name() = 'project' ]/*[local-name() = 'groupId' ]/text()" pom.xml)
  APP_NAME=$(xmllint -xpath "/*[local-name() = 'project' ]/*[local-name() = 'artifactId' ]/text()" pom.xml)
  DEFAULT_MAIN_APP_CLASS=$(xmllint -xpath "/*[local-name() = 'project' ]/*[local-name() = 'properties' ]/*[local-name() = 'mainClass']/text()" pom.xml)
fi

MAIN_APP_CLASS=${1:-$DEFAULT_MAIN_APP_CLASS}

if $(uname -s | grep -qi Darwin)
then
  JAVA_VERSION='1.8.0_121'
  MAVEN_VERSION='3.3.9'
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk$JAVA_VERSION.jdk/Contents/Home
  export M2_HOME="$HOME/Downloads/apache-maven-$MAVEN_VERSION"
  export M2="$M2_HOME/bin"
  export MAVEN_OPTS='-Xms256m -Xmx512m'
  export PATH=$M2_HOME/bin:$PATH

  # https://bugs.openjdk.java.net/browse/JDK-8167419
  # Problematic frame:
  # C  [libGL.dylib+0x1c3d]  glGetString+0x1c
  # may need to install some of https://github.com/phracker/MacOSX-SDKs

fi

mvn -Dmaven.test.skip=true package install
echo "java -cp target/$APP_JAR:target/lib/* $MAIN_APP_PACKAGE.$MAIN_APP_CLASS"
java -cp target/$APP_JAR:target/lib/* $MAIN_APP_PACKAGE.$MAIN_APP_CLASS
# mvn clean spring-boot:run
