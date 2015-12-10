Info
----
Sample scripts to execute application configuration through Selenium a part of the provisioning (e.g. [__Octopusdeploy__](https://octopus.com/), [__tableau__](http://get.tableau.com/trial/tableau-software.html?cid=701600000005cS4&ls=Paid%20Search&lsd=Google%20AdWords%20-%20Tableau%20-%20Free%20Trial&adgroup=Tableau%20-%20Exact&kw=tableau&adused=60981532215&distribution=search&gclid=CJ3dtbCS0skCFQwjHwodA9IAqg)).

Structure of the script
-----------------------
One can use template variables or even [embedded subtemplates](http://stackoverflow.com/questions/16551717/how-to-include-a-subtemplate-in-a-puppet-template) to isolate domain specific functionality:


There are two script options to utilize web page navigation payload

 - control Selenium through Selenium driver of a specific browser 
 - control Selenium through a RemoteDriver. 
 
The latter will still end up in a specific driver, but will improve logging.

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
