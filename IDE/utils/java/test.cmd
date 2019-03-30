@echo OFF
REM works
call mvn clean package install
set TARGET=%CD%\target
set MAIN_APP_CLASS=com.mycompany.app.App
set SELENIUM_VERSION=2.53.0
set APP_VERSION=0.0.3-SNAPSHOT
REM adding selenium-standalone to the CLASSPATH is not necessary.
REM java -cp %TARGET%\app-%APP_VERSION%.jar;c:\java\selenium\selenium-server-standalone-%SELENIUM_VERSION%.jar;%TARGET%\lib\* %MAIN_APP_CLASS%
java -cp %TARGET%\app-%APP_VERSION%.jar;%TARGET%\lib\*  %MAIN_APP_CLASS%

goto :EOF
