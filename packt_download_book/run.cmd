@echo OFF

if "%JAVA_VERSION%"=="" set JAVA_VERSION=1.8.0_101
if "%JAVA_HOME%"=="" set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set JAVA_OPTS=-Xms256m -Xmx512m

if "%MAVEN_VERSION%"=="" set MAVEN_VERSION=3.3.9
if "%M2_HOME%"=="" set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
if "%M2%"=="" set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

PATH=%JAVA_HOME%\bin;%M2%;%PATH%

set TARGET=%CD%\target

set PACKAGE_NAME=book-downloader
set PACKAGE_VERSION=1.0-SNAPSHOT

call mvn jfx:jar

java -jar %TARGET%\jfx\app\%PACKAGE_NAME%-%PACKAGE_VERSION%-jfx.jar
goto :EOF
