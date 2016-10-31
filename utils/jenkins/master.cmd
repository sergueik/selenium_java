@echo OFF
pushd %~dp0
set GIT_HOME=C:\Progra~1\GIT
set JENKINS_HOME=%CD:\=/%
set MASTER_HOST=127.0.0.1
set MASTER_PORT=8080
set GROOVY_VERSION=2.3.8
set JAVA_VERSION=1.7.0_79
set MAVEN_VERSION=3.2.1
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m
PATH=%JAVA_HOME%\bin;%PATH%;%GROOVY_HOME%\bin
java.exe -jar %JENKINS_HOME%/jenkins.war
goto :EOF
REM plugins https://updates.jenkins-ci.org/download/plugins/
