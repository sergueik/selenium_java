@echo OFF
call mvn clean package install
set TARGET=%CD%\target
set MAIN_APP_CLASS=com.mycompany.app.App
set SELENIUM_VERSION=2.53.0
java -cp target\app-2.0-SNAPSHOT.jar;target\lib\* %MAIN_APP_CLASS%

goto :EOF
REM adding selenium-standalone to the CLASSPATH appears to not be necessary.
java -cp %TARGET%\app-2.0-SNAPSHOT.jar;^
c:\java\selenium\selenium-server-standalone-%SELENIUM_VERSION%.jar;^
%TARGET%\lib\* ^
%MAIN_APP_CLASS% 
goto :EOF

REM http://selenium-suresh.blogspot.com/2013/09/selenium-webdriver-methods-with-examples.html
REM https://groups.google.com/forum/#!topic/selenium-users/i_xKZpLfuTk
