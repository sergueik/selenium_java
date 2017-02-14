#!/bin/bash
# set -x
PROJECT_VERSION='0.0.2-SNAPSHOT'
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

MAIN_APP_PACKAGE='study.myswt.menus_toolbars'
MAIN_APP_CLASS='SimpleToolBarEx'

java -cp target/myswt-$PROJECT_VERSION.jar:target/lib/* "$MAIN_APP_PACKAGE.$MAIN_APP_CLASS"
