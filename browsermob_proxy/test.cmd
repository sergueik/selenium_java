@echo OFF
REM works

set JAVA_VERSION=1.8.0_101
set SELENIUM_VERSION=2.53.0
set GROOVY_VERSION=2.4.4
set MAVEN_VERSION=3.3.9

set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%

set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

PATH=%JAVA_HOME%\bin;%PATH%;%GROOVY_HOME%\bin;%M2%
call mvn -Dmaven.test.skip=true -DskipTests=true package install

set TARGET=%CD%\target

java -cp target\app-1.1-SNAPSHOT.jar;c:\java\selenium\selenium-server-standalone-%SELENIUM_VERSION%.jar;target\lib\* com.mycompany.app.App

goto :EOF


REM https://groups.google.com/forum/#!topic/selenium-users/i_xKZpLfuTk
