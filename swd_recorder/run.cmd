@echo OFF
call mvn clean package install
set TARGET=%CD%\target

set MAIN_APP_PACKAGE=study.myswt.table
set MAIN_APP_CLASS=TableEx5

java -cp target\myswt-0.0.1-SNAPSHOT.jar;target\lib\* ^
%MAIN_APP_PACKAGE%.%MAIN_APP_CLASS%

goto :EOF
