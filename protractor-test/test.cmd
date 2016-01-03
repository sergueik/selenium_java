@echo OFF

REM This script expects the environment to be set up and Java tool chain in the PATH

call mvn.bat clean package

set TARGET=%CD%\target
set SELENIUM_HOME=c:\java\selenium
set SELENIUM_VERSION=2.44.0
set APP_VERSION=0.1-SNAPSHOT
set PROTRACTOR_VERSION=1.0-SNAPSHOT
set MONTE_VERSION=1.0
set M2_REPOSITORY=%USERPROFILE%\.m2\repository
set MAVEN_OPTS=-Xms256m -Xmx512m

set LAUNCHER_OPTS=-XX:PermSize=512M -XX:MaxPermSize=512M -Xmn128M -Xms512M -Xmx512M

java.exe %LAUNCHER_OPTS%  -cp ^
target\app-%APP_VERSION%.jar;^
src\main\resources\jprotractor-%PROTRACTOR_VERSION%.jar;^
%M2_REPOSITORY%\com\pojosontheweb\monte-repack\%MONTE_VERSION%\monte-repack-%MONTE_VERSION%.jar;^
%SELENIUM_HOME%\selenium-server-standalone-%SELENIUM_VERSION%.jar;^
target\lib\* ^
com.mycompany.app.App



goto :EOF

