@echo OFF
pushd %~dp0
set HTTP_PORT=4444
set HTTPS_PORT=-1
set GROOVY_VERSION=2.3.8
set JAVA_VERSION=1.6.0_45
set MAVEN_VERSION=3.2.1
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

PATH=%JAVA_HOME%\bin;%PATH%;%GROOVY_HOME%\bin;%M2%
popd
goto :EOF
REM http://docs.groovy-lang.org/latest/html/documentation/grape.html
REM http://szimano.org/how-you-can-use-groovy-to-make-automatic-selenium-page-objects/
REM https://siking.wordpress.com/2011/08/22/groovy-selenium-webdriver-and-soapui-part-3/
REM http://sourceforge.net/p/sebase/code/HEAD/tree/sebase-groovy/src/test/groovy/net/sourceforge/sebase/


