Note
----

This github project is the 'development branch' of the collaborated project 
[jProtratractor](https://github.com/caarlos0/jProtractor). It may be using a different version of java compiler but otherwise is nearly identical.


Info
====

Being  a JS testing tool, [Angular Protractor](https://github.com/angular/protractor) enforces one to (re)write the web page tests in Javascript - this project (and the sibling [C# Protractor Client Framework](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)) tries to make this unnecessary.
On the other hand Protractor offers some [locator strategies](https://github.com/angular/protractor/blob/master/lib/clientsidescripts.js) that take advantage of Angular's features -  
this is what this project tries to keep.

Currently supported Antular Proractor methods:
```
binding.js
buttonText.js
evaluate.js
getLocationAbsUrl.js
model.js
options.js
partialButtonText.js
repeater.js
repeaterColumn.js
repeaterElement.js
repeaterRows.js
resumeAngularBootstrap.js
selectedOption.js
testForAngular.js
waitForAngular.js
```


Building
========
Windows (jdk1.7.0_65, 32 bit)
-----------------------------
The following commands compile the project in console.
```
set M2=c:\java\apache-maven-3.2.1\bin
set M2_HOME=c:\java\apache-maven-3.2.1
set MAVEN_OPTS=-Xms256m -Xmx512m
set JAVA_HOME=c:\java\jdk1.7.0_65
set JAVA_VERSION=1.7.0_65
PATH=%JAVA_HOME%\bin;%PATH%;%M2%
REM
REM move %USERPROFILE%\.M2 %USERPROFILE%\.M2.MOVED
REM rd /s/q %USERPROFILE%\.M2
set TRAVIS=true
mvn clean package
```
Linux
-----
```
export TRAVIS=true
mvn clean package
```

Using with existing Java projects
=================================

Maven
-----

  * Copy `target\jprotractor-1.0-SNAPSHOT.jar` to your project `src/main/resources`:

```
+---src
    +---main
            +---java
            |   +---com
            |       +---mycompany
            |           +---app
            +---resources

```
  * Add reference to the project `pom.xml` (a sample project is checked in) 
```
<properties>
    <jprotractor.version>1.0-SNAPSHOT</jprotractor.version>
</properties>
```
```
<dependencies>
<dependency>
     <groupId>com.jprotractor</groupId>
     <artifactId>jprotractor</artifactId>
     <version>${jprotractor.version}</version>
     <scope>system</scope>
     <systemPath>${project.basedir}/src/main/resources/jprotractor-${jprotractor.version}.jar</systemPath>
</dependency>
</dependencies>
```
  * Add reference to the code:
```
import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;
  
```

Ant
---

* Copy the `target\jprotractor-1.0-SNAPSHOT.jar`  in the same location oher dependency jars, e.g. `c:\java\selenium`,
* Use the bolierplate `build.xml` (a sample project is checked in) or merge with your existing build file(s):
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<project name="example" basedir=".">
  <property name="build.dir" value="${basedir}/build"/>
  <property name="selenium.jars" value="c:/java/selenium"/>
  <property name="src.dir" value="${basedir}/src"/>
  <target name="loadTestNG" depends="setClassPath">
    <taskdef resource="testngtasks" classpath="${test.classpath}"/>
  </target>
  <target name="setClassPath">
    <path id="classpath_jars">
      <pathelement path="${basedir}/"/>
      <fileset dir="${selenium.jars}" includes="*.jar"/>
    </path>
    <pathconvert pathsep=";" property="test.classpath" refid="classpath_jars"/>
  </target>
  <target name="clean">
    <delete dir="${build.dir}"/>
  </target>
  <target name="compile" depends="clean,setClassPath,loadTestNG">
    <mkdir dir="${build.dir}"/>
    <javac destdir="${build.dir}" srcdir="${src.dir}">
      <classpath refid="classpath_jars"/>
    </javac>
  </target>
  <target name="test" depends="compile">
    <testng classpath="${test.classpath};${build.dir}">
      <xmlfileset dir="${basedir}" includes="testng.xml"/>
    </testng>
  </target>
</project>

```
* Add reference to the code:
```
import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;

```

Example Test
============

For desktop browser testing, run a Selenium node and Selenium hub on port 4444 and 
```
@BeforeClass
public static void setup() throws IOException {
    DesiredCapabilities capabilities =   new DesiredCapabilities("firefox", "", Platform.ANY);
    FirefoxProfile profile = new ProfilesIni().getProfile("default");
    capabilities.setCapability("firefox_profile", profile);
    seleniumDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    ngDriver = new NgWebDriver(seleniumDriver);
}

@Before
public void beforeEach() {
	String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";    
	ngDriver.navigate().to(baseUrl);
}
@Test
public void testCustomerLogin() throws Exception {
	NgWebElement element = ngDriver.findElement(NgBy.buttonText("Customer Login"));
	highlight(element, 100);
	element.click();
	element = ngDriver.findElement(NgBy.input("custId"));
	assertThat(element.getAttribute("id"), equalTo("userSelect"));
	Enumeration<WebElement> elements = Collections.enumeration(ngDriver.findElements(NgBy.repeater("cust in Customers")));

	while (elements.hasMoreElements()){
    WebElement next_element = elements.nextElement();
    if (next_element.getText().indexOf("Harry Potter") >= 0 ){
    	System.err.println(next_element.getText());
    	next_element.click();
    }
	}
	NgWebElement login_element = ngDriver.findElement(NgBy.buttonText("Login"));
	assertTrue(login_element.isEnabled());	
	login_element.click();
	assertThat(ngDriver.findElement(NgBy.binding("user")).getText(),containsString("Harry"));
	
	NgWebElement account_number_element = ngDriver.findElement(NgBy.binding("accountNo"));
	assertThat(account_number_element, notNullValue());
	assertTrue(account_number_element.getText().matches("^\\d+$"));
}
```
for CI build replace the Setup () with
```
@BeforeClass
public static void setup() throws IOException {
	seleniumDriver = new PhantomJSDriver();
	ngDriver = new NgWebDriver(seleniumDriver);
}
```


Note
----
PhantomJs allows loading Angular samples from `file://` content:

```
    seleniumDriver = new PhantomJSDriver();
    seleniumDriver.manage().window().setSize(new Dimension(width , height ));
    seleniumDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS).implicitlyWait(implicitWait, TimeUnit.SECONDS).setScriptTimeout(10, TimeUnit.SECONDS);
    wait = new WebDriverWait(seleniumDriver, flexibleWait );
    wait.pollingEvery(pollingInterval,TimeUnit.MILLISECONDS);
    actions = new Actions(seleniumDriver);    
    ngDriver = new NgWebDriver(seleniumDriver);
    localFile = "local_file.htm";
    URI uri = NgByIntegrationTest.class.getClassLoader().getResource(localFile).toURI();
    ngDriver.navigate().to(uri);
    WebElement element = ngDriver.findElement(NgBy.repeater("item in items"));
    assertThat(element, notNullValue());

```
Certain tests ( e.g. involving `NgBy.selectedOption()` ) currently fail under [travis](https://travis-ci.org/) CI build.

Test setup
----------
The project work on Linux with Java 1.7, Firefox 40 (download desired version of firefox from
[ubuntuzilla](https://sourceforge.net/projects/ubuntuzilla/files/mozilla/apt/pool/main/f/firefox-mozilla-build/)) and `selenium-server-standalone-2.47.1.jar'. Below is the hub setup commands:

```
export NODE_PORT=5555
export HUB_IP_ADDRESS=127.0.0.1
export HUB_PORT=4444
export SELENIUM_JAR_VERSION=2.47.1
export DISPLAY_PORT=0
export SELENIUM_HOME=`pwd`
export LAUNCHER_OPTS='-XX:MaxPermSize=256M -Xmn128M -Xms256M -Xmx256M'
export NODE_CONFIG=node.json
export NODE_HOST=127.0.0.1
java $LAUNCHER_OPTS  -jar ${SELENIUM_HOME}/selenium-server-standalone-${SELENIUM_JAR_VERSION}.jar -role node -host $NODE_HOST -port $NODE_PORT -hub http://${HUB_IP_ADDRESS}:${HUB_PORT}/hub/register -nodeConfig $NODE_CONFIG -browserTimeout 12000 -timeout 12000 -ensureCleanSession true -trustAllSSLCertificates

export HUB_PORT=4444
java $LAUNCHER_OPTS -jar ${SELENIUM_HOME}/selenium-server-standalone-${SELENIUM_JAR_VERSION}.jar -role hub  -port $HUB_PORT

```

Related Projects 
================
  - [Protractor-jvm](https://github.com/F1tZ81/Protractor-jvm)
  - [ngWebDriver](https://github.com/paul-hammant/ngWebDriver)
  - [angular/protractor](https://github.com/angular/protractor) 
  - [bbaia/protractor-net](https://github.com/bbaia/protractor-net)
  - [sergueik/protractor-net](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)


Author
------
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
