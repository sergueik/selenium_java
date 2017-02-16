#!/bin/bash
# set -x

PROJECT_VERSION='0.0.3-SNAPSHOT'
MAIN_APP_PACKAGE='com.mycompany.app'
MAIN_APP_CLASS=${1:-SimpleToolBarEx}


ALIAS='opal'
JARFILE_VERSION='1.0.4'
JARFILE="$ALIAS-$JARFILE_VERSION.jar"
URL="https://github.com/lcaron/opal/blob/releases/V$JARFILE_VERSION/opal-$JARFILE_VERSION.jar?raw=true"
if [[ ! -f "src/main/resources/$JARFILE" ]] 
then
pushd 'src/main/resources/'
wget -O $JARFILE -nv $URL
popd
fi


mvn clean package install
java -cp target/myswt-$PROJECT_VERSION.jar:target/lib/* "$MAIN_APP_PACKAGE.$MAIN_APP_CLASS"