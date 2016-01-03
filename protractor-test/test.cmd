@echo OFF

REM This script expects the environment to be set up and Java tool chain in the PATH

call mvn.bat clean package
set TARGET=%CD%\target
set SELENIUM_VERSION=2.44.0
set PROTRACTOR_VERSION=1.0-SNAPSHOT
set MONTE_VERSION=1.0
set M2_REPOSITORY=%USERPROFILE%\.m2\repository


java.exe -cp ^
target\app-0.1-SNAPSHOT.jar;^
src\main\resources\jprotractor-1.0-SNAPSHOT.jar;^
%USERPROFILE%\.m2\repository\com\pojosontheweb\monte-repack\1.0\monte-repack-1.0.jar;^
c:\java\selenium\selenium-server-standalone-2.44.0.jar;^
target\lib\* ^
com.mycompany.app.App



goto :EOF

REM http://selenium-suresh.blogspot.com/2013/09/selenium-webdriver-methods-with-examples.html
REM https://groups.google.com/forum/#!topic/selenium-users/i_xKZpLfuTk


-Dfile=src\main\resources\jprotractor-1.0-SNAPSHOT.jar -DgroupId=com.jprotractor -DartifactId=jprotractor -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true