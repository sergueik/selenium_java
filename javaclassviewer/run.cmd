@echo OFF
if "%JAVA_VERSION%"=="" set JAVA_VERSION=1.8.0_101
if "%JAVA_VERSION%"=="" set JAVA_VERSION=11.0.12
set MAVEN_VERSION=3.3.3
set ANT_VERSION=1.9.7
set GROOVY_VERSION=2.4.4

if "%JAVA_HOME%"=="" set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
if "%JAVA_HOME%"=="" set JAVA_HOME=%TOOL_HOME%\jdk-%JAVA_VERSION%
set ANT_HOME=c:\java\apache-ant-%ANT_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
PATH=%JAVA_HOME%\bin;%PATH%;%ANT_HOME%\bin;%M2%

set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m


set MAIN_CLASS=Main
set APP_PACKAGE=org.freeinternals.javaclassviewer

for /F %%. in ('dir /b /ad ') do call :build %%.
goto :run
:build 
pushd  %1
echo build %1
call ant -keep-going 
popd
goto :EOF 
:run
echo run ^
java -classpath ^
CommonLib\dist\CommonLib.jar;FormatCLASS\dist\FormatCLASS.jar;JavaClassViewer\dist\JavaClassViewer.jar ^
  %APP_PACKAGE%.%MAIN_CLASS% ^
  %1 %2 %3 %4 %5 %6 %7 %8 %9

java -classpath ^
CommonLib\dist\CommonLib.jar;FormatCLASS\dist\FormatCLASS.jar;JavaClassViewer\dist\JavaClassViewer.jar ^
  %APP_PACKAGE%.%MAIN_CLASS% ^
  %1 %2 %3 %4 %5 %6 %7 %8 %9

goto :EOF 
