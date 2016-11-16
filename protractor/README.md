### Note

This github project is the 'development branch' of the collaborated project 
[jProtractor](https://github.com/caarlos0/jProtractor). It may be using a different version of Java compiler but otherwise identical, just few commits ahead.

#### Info
The [Angular Protractor](https://github.com/angular/protractor) is a very popular Selenium Web Driver based project. 
However being a Javascript testing tool, __Protractor__ enforces one to (re)write the whole web page test suite in Javascript - which may not be acceptable solution.

This project and the sibling [C# Protractor Client Framework](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net) try to make this unnecessary.
On the other hand Protractor offers some advanced [locator strategies](https://github.com/angular/protractor/blob/master/lib/clientsidescripts.js) which selenum Webdriver does not have. 

These strategier take advantage of __Angular__ features -  
this project allows the Java developer to use those strategies by supporting all Javascript methods __Protractor__ has plus few extra:

```javascript
findBindings = function(binding, exactMatch, using, rootSelector)
```
Find a list of elements in the page by their angular binding.

```javascript
findByButtonText = function(searchText, using)
```
Find buttons by textual content
```javascript
findByCssContainingText = function(cssSelector, searchText, using)
```
Find elements by css selector and textual content
```javascript
findByModel = function(model, using, rootSelector)
```
Find elements by model name
```javascript
findByOptions = function(options, using)
```
Find elements by options
```javascript
findByPartialButtonText = function(searchText, using)
```
Find buttons by textual content fragment
```javascript
findAllRepeaterRows = function(using, repeater)
```
Find an array of elements matching a row within an ng-repeat
```javascript
findRepeaterColumn = function(repeater, exact, binding, using, rootSelector)
```
Find the elements in a column of an ng-repeat
```javascript
findRepeaterElement = function(repeater, exact, index, binding, using, rootSelector)
```
Find an element within an ng-repeat by its row and column.
```javascript
findSelectedOption = function(model, using)
```
Find selected option elements by model name
```javascript
findSelectedRepeaterOption = function(repeater, using)
```
Find selected option elements in the select implemented via repeater without a model.
```javascript
TestForAngular = function(attempts)
```
Tests whether the angular global variable is present on a page
```javascript
waitForAngular = function(rootSelector, callback)
```
Wait until Angular has finished rendering
```javascript
return angular.element(element).scope().$eval(expression)
```
Evaluate an Angular expression in the context of a given element.

Each method borrowed from __Protractor__ is a Javascript snippet stored in a separate file under
`src/main/java/resources`:
```
binding.js
buttonText.js
cssContainingText.js
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
selectedRepeaterOption.js
testForAngular.js
waitForAngular.js
```
#### Forum
There is a [Protractor Java and c# Client](https://gctor Java and c# Clientroups.google.com/forum/#!forum/protractor-java--c-client) forum.

#### Presentation on June 2016 South Florida Tester Meetup 

![ Providing Protractor to Java / .Net](https://github.com/sergueik/selenium_java/blob/master/protractor/jProtractor.odp?raw=true)

#### Building
To compile the project in console use the regular maven setup script:

##### Windows

```cmd
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
##### Linux
```bash
export TRAVIS=true
mvn clean package
```

#### Using `jProtractor` with existing Java projects

##### Maven
Copy `target\jprotractor-1.0-SNAPSHOT.jar` into your project `src/main/resources`:

```
+---src
    +---main
            +---java
            |   +---com
            |       +---mycompany
            |           +---app
            +---resources

```
Add the reference to the project `pom.xml` (a sample project is checked in) 
```xml
<properties>
    <jprotractor.version>1.0-SNAPSHOT</jprotractor.version>
</properties>
```
```xml
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
Import jProractor classes in your code
```java
import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;
  
```

##### Ant

Copy the `target\jprotractor-1.0-SNAPSHOT.jar` in the same location oher dependency jars, e.g. `c:\java\selenium`,
Use the `build.xml` from a sample project provided or merge with your existing build file(s):
```xml
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
Import jProractor classes in your code
```java
import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;
```

### Example Test
For desktop browser testing, run a Selenium node and Selenium hub on port 4444. Likewise, for Vagrant box browser testing have localhost port 4444 forwarded to the hub 4444
Then create a typical TestNG test class with the annotated methods like below.

```java
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
```java
@BeforeClass
public static void setup() throws IOException {
	seleniumDriver = new PhantomJSDriver();
	ngDriver = new NgWebDriver(seleniumDriver);
}
```

### Note
PhantomJs allows loading Angular samples from `file://` context:

```java
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

### Test setup
The project work on Linux with Java 1.7, Selenium 2.47, Firefox 40 (download desired version of firefox from
[ubuntuzilla](https://sourceforge.net/projects/ubuntuzilla/files/mozilla/apt/pool/main/f/firefox-mozilla-build/) or [ftp.mozilla.org](https://ftp.mozilla.org/pub/firefox/)) and `selenium-server-standalone-2.47.1.jar'. Below is the hub setup commands:

```bash
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
### Related Projects 
  - [Protractor-jvm](https://github.com/F1tZ81/Protractor-jvm)
  - [ngWebDriver](https://github.com/paul-hammant/ngWebDriver)
  - [angular/protractor](https://github.com/angular/protractor) 
  - [bbaia/protractor-net](https://github.com/bbaia/protractor-net)
  - [sergueik/protractor-net](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)
  - [henrrich/jpagefactory](https://github.com/henrrich/jpagefactory)

Author
------
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
