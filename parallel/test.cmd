@echo OFF
REM works
call mvn clean test-compile package install
set TARGET=%CD%\target

java -cp target\app-1.1-SNAPSHOT.jar;c:\java\selenium\selenium-server-standalone-2.53.0.jar;target\lib\*  com.mycompany.app.App

goto :EOF
REM http://stackoverflow.com/questions/2235276/how-to-run-junit-test-cases-from-the-command-line