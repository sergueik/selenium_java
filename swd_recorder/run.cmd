@echo OFF
call mvn package install
set TARGET=%CD%\target

set MAIN_APP_PACKAGE=study.myswt.menus_toolbars
set MAIN_APP_CLASS=SimpleToolBarEx

java -cp target\myswt-0.0.1-SNAPSHOT.jar;target\lib\* ^
%MAIN_APP_PACKAGE%.%MAIN_APP_CLASS%

goto :EOF
