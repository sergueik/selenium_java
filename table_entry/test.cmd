@echo OFF
echo building
call mvn clean package install
echo testing
set TARGET=%CD%\target
set MAIN_APP_CLASS=com.mycompany.app.App
set SELENIUM_VERSION=2.44.0
java -cp %TARGET%\app-1.1-SNAPSHOT.jar;c:\java\selenium\selenium-server-standalone-%SELENIUM_VERSION%.jar;%TARGET%\lib\* %MAIN_APP_CLASS% 

goto :EOF

