### Build:

#### Into default Selenium jar.  

* Clone https://github.com/SeleniumHQ/selenium
* Name and place servet into the source tree
```cmd 
c:\developer\sergueik\selenium-master\java\server\src\org\openqa\grid\web\servlet\MyConsoleServlet.java`
```
* Rebuild the full selenium tree using their build tools

* Run using the oririnal __hub__ launch command.

#### Standalone servelet class 


with the stock Selenium jar
1.
start hub with the servet :
```cmd
pushd C:\developer\sergueik\selenium-master\build\java\server\src\org\openqa\grid\selenium

java -cp selenium-standalone.jar org.openqa.grid.selenium.GridLauncher -role hub -servlets org.openqa.grid.web.servlet.MyConsoleServlet
```
#### Standalong Jar together with Selenium jar
* package into separate jar
 
* start node as usual
```cmd
@echo OFF
pushd %~dp0
set SELENIUM_HOME=%CD:\=/%
set HTTP_PORT=4444
set HTTPS_PORT=-1
set SELENIUM_VERSION=2.53.0
set GROOVY_VERSION=2.4.4
set JAVA_VERSION=1.8.0_101
set MAVEN_VERSION=3.3.3
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
set GROOVY_HOME=c:\java\groovy-%GROOVY_VERSION%
set M2_HOME=c:\java\apache-maven-%MAVEN_VERSION%
set M2=%M2_HOME%\bin
set MAVEN_OPTS=-Xms256m -Xmx512m

PATH=%JAVA_HOME%\bin;%PATH%;%GROOVY_HOME%\bin;%M2%

PATH=%PATH%;c:\Program Files\Mozilla Firefox
set HUB_CONFIG=hub.json
set LOGFILE=hub.log

type NUL > %LOGFILE%

java %LAUNCHER_OPTS% ^
-classpath %SELENIUM_HOME%/log4j-1.2.17.jar;^
%SELENIUM_HOME%/ConsoleServlet-1.0-SNAPSHOT.jar;^
%SELENIUM_HOME%/json-20080701.jar;^
%SELENIUM_HOME%/selenium-server-standalone-%SELENIUM_VERSION%.jar; ^
-Dlog4j.configuration=hub.log4j.properties ^
org.openqa.grid.selenium.GridLauncher ^
-port %HTTP_PORT% ^
-role hub ^
-ensureCleanSession true ^
-trustAllSSLCertificates true ^
-maxSession 20 ^
-newSessionWaitTimeout 600000 ^
-servlets com.mycompany.app.MyConsoleServlet ^

```
#### Interact with servelet
Navigate to http://localhost:4444/grid/admin/MyConsoleServlet

``
