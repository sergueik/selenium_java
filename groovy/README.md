Info
----
Sample scripts to execute application configuration through Selenium a part of the provisioning (e.g. [__Octopusdeploy__](https://octopus.com/), [__tableau__](http://get.tableau.com/trial/tableau-software.html?cid=701600000005cS4&ls=Paid%20Search&lsd=Google%20AdWords%20-%20Tableau%20-%20Free%20Trial&adgroup=Tableau%20-%20Exact&kw=tableau&adused=60981532215&distribution=search&gclid=CJ3dtbCS0skCFQwjHwodA9IAqg)).

Structure of the script
-----------------------
One can use template variables or even [embedded subtemplates](http://stackoverflow.com/questions/16551717/how-to-include-a-subtemplate-in-a-puppet-template) to isolate domain specific functionality:


There are two script options to utilize web page navigation payload

 - control Selenium through a specific browser driver.
 - control Selenium through __RemoteDriver__. 
 
The latter will still end up in a specific driver, but will improve logging

To run interactively on the node 

```
export GROOVY_HOME=/usr/local/groovy-2.4.5
export PATH=$PATH:$GROOVY_HOME/bin
export PATH=$PATH:/home/vncuser/selenium/firefox
export DISPLAY=:99
groovy basic.groovy
```

The value of `DISPLAY` would vary whether one runs in a vm with `Xvfb` (99) or `vnc` (1). When a __RemoteDriver__ is used these settings are already in `run_node.sh`.

Dependencies
------------

The groovy script can be deployed and run from a [selenium vagrant box](https://github.com/sergueik/selenium_vagrant) as part of automating some configuration. The following jar file layout is to be present on the machine locally (assuming groovy script is run by the root user):


```
/root/.groovy/grapes/
cglib
com.google.code.gson
com.google.guava
commons-codec
commons-io
commons-logging
net.java.dev.jna
org.apache
org.apache.commons
org.apache.httpcomponents
org.gebish
org.json
org.seleniumhq.selenium
org.sonatype.oss

```
with the following file layout for each jar directory:
```
/root/.groovy/grapes/commons-io/commons-io/ivy-2.4.xml
/root/.groovy/grapes/commons-io/commons-io/ivy-2.4.xml.original
/root/.groovy/grapes/commons-io/commons-io/jars/commons-io-2.4.jar
/root/.groovy/grapes/commons-io/commons-io/ivydata-2.4.properties

```
(total or 88 jars).


### CDP Project

Instead of Selenium one may use [Java client of Chrome DevTools Protocol](https://github.com/webfolderio/cdp4j).

This leads to significant reduction in the number of depenencies;
```shell
~/.groovy/grapes
~/.groovy/grapes/io.webfolder
~/.groovy/grapes/io.webfolder/cdp4j
~/.groovy/grapes/io.webfolder/cdp4j/ivy-2.2.2.xml
~/.groovy/grapes/io.webfolder/cdp4j/ivy-2.2.2.xml.original
~/.groovy/grapes/io.webfolder/cdp4j/ivydata-2.2.2.properties
~/.groovy/grapes/io.webfolder/cdp4j/jars
~/.groovy/grapes/io.webfolder/cdp4j/jars/cdp4j-2.2.2.jar

```
