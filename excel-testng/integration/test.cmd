@echo OFF

REM This script expects the environment to be set up and Java tool chain in the PATH

call mvn.cmd clean install

set TARGET=%CD%\target
set APP_VERSION=0.1-SNAPSHOT
set EXCEL_TESTNG_VERSION=1.0-SNAPSHOT
set MAVEN_OPTS=-Xms256m -Xmx512m

set LAUNCHER_OPTS=-XX:PermSize=512M -XX:MaxPermSize=512M -Xmn128M -Xms512M -Xmx512M

java.exe %LAUNCHER_OPTS%  -cp ^
target\excel-testng-demo-%APP_VERSION%.jar;^
src\main\resources\excel-testng-%EXCEL_TESTNG_VERSION%.jar;^
target\lib\* ^
net.randomsync.googlesearch.TestRunner ^
test-input 2


goto :EOF

