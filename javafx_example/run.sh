#!/bin/bash
# set -x

PACKAGE_NAME='javafx_example_app'
SKIP_PACKAGE_VERSION=true

# TODO: xmllint the `pom.xml`
if [[ $SKIP_PACKAGE_VERSION ]] ; then
  PACKAGE_NAME='javafx_example_app'
  APP_JAR="$PACKAGE_NAME.jar"
else
  PACKAGE_NAME='javafx_example'
  PACKAGE_VERSION='0.0.1-SNAPSHOT'
  APP_JAR="$PACKAGE_NAME-$PACKAGE_VERSION.jar"
fi

MAIN_APP_PACKAGE='example'
MAIN_APP_CLASS=${1:-FlowPaneEx}

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
