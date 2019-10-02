@echo OFF
SETLOCAL

REM NOTE: Cannot set SETLOCAL ENABLEDELAYEDEXPANSION
REM Because of inline ie script in :CALL_JAVASCRIPT

if "%TOOLS_DIR%"=="" set TOOLS_DIR=c:\java
if "%JAVA_VERSION%"=="" set JAVA_VERSION=1.8.0_101
if "%JAVA_HOME%"=="" set JAVA_HOME=%TOOLS_DIR%\jdk%JAVA_VERSION%
set JAVA_OPTS=-Xms256m -Xmx512m

if "%MAVEN_VERSION%"=="" set MAVEN_VERSION=3.6.1
if "%M2_HOME%"=="" set M2_HOME=%TOOLS_DIR%\apache-maven-%MAVEN_VERSION%
if "%M2%"=="" set M2=%M2_HOME%\bin

set MAVEN_OPTS=-Xms256m -Xmx512m
REM TODO: monochrome
set TERM=

PATH=%JAVA_HOME%\bin;%M2%;%PATH%
set TARGET=%CD%\target
set DEBUG=false
set SKIP_TEST=tue
set SKIP_PACKAGE_VERSION=true

REM this currently is not loaded from pom.xml
set DEFAULT_MAIN_CLASS=SwetMainFlowPanel
REM These get extracted from the pom.xml, keeping the default values for backwad compatibility
set APP_NAME=swet_javafx_app
set APP_PACKAGE=com.github.sergueik.swet_javafx
set APP_VERSION=0.0.2-SNAPSHOT

set APP_JAR=%APP_NAME%.jar

if /i "%SKIP_PACKAGE_VERSION%"=="true" goto :SKIP_PACKAGE_VERSION
set APP_JAR=%APP_NAME%-%APP_VERSION%.jar
:SKIP_PACKAGE_VERSION

call :CALL_JAVASCRIPT groupId
set APP_PACKAGE=%VALUE%

call :CALL_JAVASCRIPT artifactId
set APP_NAME=%VALUE%

call :CALL_JAVASCRIPT version
set APP_VERSION=%VALUE%

REM Cannot get the mainClass currently

if /i NOT "%VERBOSE%"=="true" goto :CONTINUE

call :SHOW_VARIABLE APP_VERSION
call :SHOW_VARIABLE APP_NAME
call :SHOW_VARIABLE APP_PACKAGE
call :SHOW_VARIABLE APP_JAR

:CONTINUE

set MAIN_CLASS=%1
if NOT "%MAIN_CLASS%" == "" shift
if "%MAIN_CLASS%"=="" set MAIN_CLASS=%DEFAULT_MAIN_CLASS%
set APP_HOME=%CD:\=/%

REM omit the extension - varies with Windows releases to be mvn.bat or mvn.cmd

if "%SKIP_TEST%"=="" (
REM Test
call mvn test
REM Compile
call mvn package install
) else (
REM compile
call mvn -Dmaven.test.skip=true package install
)

REM Run
REM NOTE: shift does not modify %*
REM The log4j configuration argument seems to be ignored
REM -Dlog4j.configuration=file:///%APP_HOME%/src/main/resources/log4j.properties ^
set COMMAND=^
java ^
  -cp %TARGET%\%APP_JAR%;%TARGET%\lib\* ^
  %APP_PACKAGE%.%MAIN_CLASS% ^
  %1 %2 %3 %4 %5 %6 %7 %8 %9
echo %COMMAND%>&2
%COMMAND%
ENDLOCAL
exit /b
goto :EOF

:CALL_JAVASCRIPT

REM This script extracts project g.a.v a custom property from pom.xml using mshta.exe and DOM selectSingleNode method
set "SCRIPT=javascript:{"
set "SCRIPT=%SCRIPT% var fso = new ActiveXObject('Scripting.FileSystemObject');"
set "SCRIPT=%SCRIPT% var out = fso.GetStandardStream(1);"
set "SCRIPT=%SCRIPT% var fh = fso.OpenTextFile('pom.xml', 1, true);"
set "SCRIPT=%SCRIPT% var xd = new ActiveXObject('Msxml2.DOMDocument');"
set "SCRIPT=%SCRIPT% xd.async = false;"
set "SCRIPT=%SCRIPT% data = fh.ReadAll();"
set "SCRIPT=%SCRIPT% xd.loadXML(data);"
set "SCRIPT=%SCRIPT% root = xd.documentElement;"
set "SCRIPT=%SCRIPT% var xpath = '%~1';"
set "SCRIPT=%SCRIPT% var xmlnode = root.selectSingleNode( xpath);"
set "SCRIPT=%SCRIPT% if (xmlnode != null) {"
set "SCRIPT=%SCRIPT%   out.Write(xpath + '=' + xmlnode.text);"
set "SCRIPT=%SCRIPT% } else {"
set "SCRIPT=%SCRIPT%   out.Write('ERR');"
set "SCRIPT=%SCRIPT% }"
set "SCRIPT=%SCRIPT% close();}"

for /F "tokens=2 delims==" %%_ in ('mshta.exe "%SCRIPT%" 1 ^| more') do set VALUE=%%_
ENDLOCAL
exit /b
goto :EOF



:SHOW_VARIABLE
SETLOCAL ENABLEDELAYEDEXPANSION
set VAR=%1
if /i "%DEBUG%"=="true" echo>&2 VAR=!VAR!
set RESULT=!VAR!
call :SHOW_VARIABLE_VALUE !%VAR%!
set RESULT=!RESULT!="!DATA!"
echo>&2 !RESULT!
ENDLOCAL
goto :EOF

:SHOW_VARIABLE_VALUE
set VAL=%1
set DATA=%VAL%
if /i "%DEBUG%"=="true" echo>&2 VALUE=%VAL%
goto :EOF
