@echo OFF
set HTTP_PORT=4444
set HTTPS_PORT=-1
set APP_VERSION=2.42.2
set GROOVY_VERSION=2.4.4
set JAVA_VERSION=1.7.0_79
set MAVEN_VERSION=3.3.9
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m
PATH=%JAVA_HOME%\bin;%PATH%;%GROOVY_HOME%\bin
CHOICE /T 1 /C ync /CS /D y
REM This setting needs adjustment.
set MASTER_HOST=192.168.56.102
set MASTER_PORT=8080
set JENKINS_SLAVE_NAME=Windows_slave
java.exe -jar slave.jar -jnlpUrl http://%MASTER_HOST%:%MASTER_PORT%/computer/%JENKINS_SLAVE_NAME%/slave-agent.jnlp
goto :EOF