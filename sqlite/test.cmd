@echo OFF

call mvn clean package install
set TARGET=%CD%\target
java -cp target\app-1.1-SNAPSHOT.jar;target\lib\*  com.mycompany.app.App
goto :EOF