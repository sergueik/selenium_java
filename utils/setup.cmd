@echo OFF
set GROOVY_VERSION=2.3.8
set JAVA_VERSION=1.6.0_45
set JAVA_VERSION=1.7.0_65
set ANT_VERSION=1.9.3

set MAVEN_VERSION=3.2.1
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%

set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

set ANT_HOME=c:\java\apache-ant-%ANT_VERSION%

PATH=%JAVA_HOME%\bin;%PATH%;%ANT_HOME%\bin;%GROOVY_HOME%\bin;%M2%

REM http://www.tutorialspoint.com/maven/maven_environment_setup.htm
REM http://stackoverflow.com/questions/5134953/noclassdeffounderror-in-java