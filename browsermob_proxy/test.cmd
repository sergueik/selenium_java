@echo OFF

set JAVA_VERSION=1.8.0_101
set MAVEN_VERSION=3.3.9
set DEFAULT_PROFILE=new_bmp
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

set BROWSER=chrome
REM set BROWSER=firefox
set APP_NAME=App
set APP_VERSION=0.4-SNAPSHOT
set APP_PACKAGE=com.github.sergueik.bmp
set MAIN_CLASS=App

set PROFILE=%~1
if NOT "%PROFILE%" == "" shift
if "%PROFILE%"=="" set PROFILE=%DEFAULT_PROFILE%

PATH=%JAVA_HOME%\bin;%PATH%;%M2%
call mvn -P%PROFILE% -Dmaven.test.skip=true -DskipTests=true clean compile package install

set TARGET=%CD%\target

java -cp %TARGET%\%APP_NAME%-%APP_VERSION%.jar;%TARGET%\lib\* %APP_PACKAGE%.%MAIN_CLASS%

goto :EOF

REM https://groups.google.com/forum/#!topic/selenium-users/i_xKZpLfuTk
