@echo OFF
call mvn clean package install
rem call mvn package install
set TARGET=%CD%\target

set MAIN_APP_CLASS=study.myswt.hello.HelloWorld
set MAIN_APP_CLASS=study.myswt.layouts.ButtonsEx
set MAIN_APP_CLASS=study.myswt.layouts.ButtonsEx

set MAIN_APP_PACKAGE=study.myswt.dialog
set MAIN_APP_PACKAGE=study.myswt.menus_toolbars
set MAIN_APP_CLASS=MessageBoxEx2
set MAIN_APP_CLASS=PopupMenuEx
set SELENIUM_VERSION=2.53.0
set SELENUM_HOME=c:\java\selenium\
REM adding selenium-standalone to the CLASSPATH is not necessary.
REM java -cp %TARGET%\myswt-0.0.1-SNAPSHOT.jar;%SELENUM_HOME%\selenium-server-standalone-%SELENIUM_VERSION%.jar;%TARGET%\lib\* %MAIN_APP_PACKAGE%.%MAIN_APP_CLASS%
java -cp target\myswt-0.0.1-SNAPSHOT.jar;target\lib\*  %MAIN_APP_PACKAGE%.%MAIN_APP_CLASS%
goto :EOF
