@echo OFF
REM works
call mvn clean package install
set TARGET=%CD%\target
set MAIN_APP_CLASS=com.mycompany.app.App
set SELENIUM_VERSION=2.44.0
REM adding selenium-standalone to the CLASSPATH is not necessary.
REM java -cp %TARGET%\app-1.1-SNAPSHOT.jar;c:\java\selenium\selenium-server-standalone-%SELENIUM_VERSION%.jar;%TARGET%\lib\* %MAIN_APP_CLASS% 
java -cp target\app-1.1-SNAPSHOT.jar;target\lib\*  com.mycompany.app.App

goto :EOF

REM http://selenium-suresh.blogspot.com/2013/09/selenium-webdriver-methods-with-examples.html
REM https://groups.google.com/forum/#!topic/selenium-users/i_xKZpLfuTk
