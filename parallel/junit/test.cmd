@echo OFF
call mvn clean test-compile package install
set TARGET=%CD%\target
set SELENIUM_VERSION=2.53.0
java -cp target\app-1.1-SNAPSHOT.jar;target\lib\junit-4.8.1.jar;c:\java\selenium\selenium-server-standalone-%SELENIUM_VERSION%.jar;target\lib\* org.junit.runner.JUnitCore com.mycompany.app.App
goto :EOF
REM http://stackoverflow.com/questions/2235276/how-to-run-junit-test-cases-from-the-command-line