@echo OFF

REM allow override from the environment
set TOOL_HOME=c:\java
if "%GRADLE_VERSION%"=="" set GRADLE_VERSION=3.0
set GRADLE_HOME=%TOOL_HOME%\gradle-%GRADLE_VERSION%

if "%ANT_VERSION%"=="" set ANT_VERSION=1.9.7
set ANT_HOME=%TOOL_HOME%\apache-ant-%ANT_VERSION%

if "%IVY_VERSION%"=="" set IVY_VERSION=2.4.0
set IVY_HOME=%TOOL_HOME%\apache-ivy-%IVY_VERSION%
REM cannot symlink
REM copy %IVY_HOME%\ivy-%IVY_VERSION%.jar %ANT_HOME%\lib

if "%GROOVY_VERSION%"==""  set GROOVY_VERSION=2.4.4
set GROOVY_HOME=%TOOL_HOME%\groovy-%GROOVY_VERSION%

if "%JAVA_VERSION%"=="" set JAVA_VERSION=1.8.0_101
set JAVA_HOME=%TOOL_HOME%\jdk%JAVA_VERSION%
set JAVA_OPTS=-Xms256m -Xmx512m

if "%MAVEN_VERSION%"=="" set MAVEN_VERSION=3.3.9
set M2_HOME=%TOOL_HOME%\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=%JAVA_OPTS%

PATH=%JAVA_HOME%\bin;%PATH%;%ANT_HOME%\bin;%GROOVY_HOME%\bin;%GRADLE_HOME%\bin;%M2%

set PHANTOMJS_HOME=c:\tools\phantomjs
PATH=%PATH%;%PHANTOMJS_HOME%\bin

REM http://www.tutorialspoint.com/maven/maven_environment_setup.htm
REM http://stackoverflow.com/questions/5134953/noclassdeffounderror-in-java
