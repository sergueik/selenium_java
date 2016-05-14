@echo OFF

call mvn clean package install
set SELENIUM_HOME=c:\java\selenium

set TARGET=%CD%\target
set MAIN_APP_CLASS=com.mycompany.app.App
set SELENIUM_VERSION=2.47.1
set APP_VERSION=1.1-SNAPSHOT

set LAUNCHER_OPTS=-XX:PermSize=512M -XX:MaxPermSize=512M -Xmn128M -Xms512M -Xmx512M

java.exe %LAUNCHER_OPTS%  -cp ^
%TARGET%\app-%APP_VERSION%.jar;^
%SELENIUM_HOME%\selenium-server-standalone-%SELENIUM_VERSION%.jar;^
%TARGET%\lib\* ^
%MAIN_APP_CLASS%


