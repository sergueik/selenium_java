@echo OFF
pushd %~dp0
set SELENIUM_HOME=%CD:\=/%
set HTTP_PORT=4444
set HTTPS_PORT=-1
set SELENIUM_VERSION=4.0.0
set GROOVY_VERSION=2.3.8
set JAVA_VERSION=1.8.0_101
set MAVEN_VERSION=3.2.1
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

PATH=%JAVA_HOME%\bin;%PATH%;%GROOVY_HOME%\bin;%M2%

PATH=%PATH%;c:\Program Files\Mozilla Firefox

set LOGFILE=hub.log
type NuL  > %LOGFILE%


REM 
REM Error occurred during initialization of VM
REM The size of the object heap + VM data exceeds the maximum representable size
REM Error occurred during initialization of VM
REM Could not reserve enough space for object heap
REM Could not create the Java virtual machine.
REM Then
REM This setting needs adjustment.
REM 

REM set LAUNCHER_OPTS=-XX:PermSize=512M -XX:MaxPermSize=1028M -Xmn128M -Xms512M -Xmx1024M
set LAUNCHER_OPTS=-XX:MaxPermSize=1028M -Xmn128M

java %LAUNCHER_OPTS% -jar selenium-server-%SELENIUM_VERSION%.jar hub
