#! /bin/sh

mvn clean package install
set TARGET=%CD%\target

MAIN_APP_PACKAGE='study.myswt.menus_toolbars'
MAIN_APP_CLASS='SimpleToolBarEx'

java -cp target/myswt-0.0.1-SNAPSHOT.jar:target/lib/* $MAIN_APP_PACKAGE.$MAIN_APP_CLASS
