### Note

This github project is the 'development branch' of the collaborated project
[__jProtractor__](https://github.com/caarlos0/jProtractor). It may be few commits ahead.

#### Info

The [__Angular Protractor__](https://github.com/angular/protractor)
is a very popular Selenium Web Driver based project.

There have been recenly few some deeply pessimistic posts about
the Google Angular team plans to __end the development of Protractor at the end of 2022__
(links at the end of the README) and a number of older debates of (dis) advantages of
various asyncronuos syntaxes intrinsic to Javascipt, this project is not about specifics of Javascript testsute development
but constructs a set of plain Java `By`-alike classes
on top of the 4-year old Protractor's own vanilla Javascript [Client Script Library](https://github.com/angular/protractor/blob/master/lib/clientsidescripts.js) in much the same way one may
choose to invoke straight vanilla Javascript `querySelector(selector)` and  `querySelectorAll(selector)`
instead of `findElement` and `findElements` with an `By.cssSelector(...))` argument.

However being a Javascript testing tool, depreated or not, genuine __Protractor__ does force one to fully buy in
by (re)writing the entire test suite in Javascript which few find an acceptable price.

This project and its twins [C# Protractor Client Framework](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)
and [pytractor](https://github.com/kpodl/pytractor) manage to make such sacrifice unnecessary.

__Angular 1.x__  a.k.a. __AngularJS__ did introduce few unique (from tester perspective) features:
 `ng-repeat` and `ng_model`, which __Protractor__ takes advantage of by providing unique MVC-style [locator strategies](http://www.protractortest.org/#/api?view=ProtractorBy)

The __jProtractor__ project makes these, plus few extra, locator extensions available to Java:

```javascript
findBindings = function(binding, exactMatch, using, rootSelector)
```
finds a list of elements in the page by their `angular` binding
```javascript
findByButtonText = function(searchText, using)
```
finds buttons by textual content
```javascript
findByCssContainingText = function(cssSelector, searchText, using)
```
finds elements by css selector and textual content
```javascript
findByModel = function(model, using, rootSelector)
```
finds elements by model name
```javascript
findByOptions = function(options, using)
```
finds elements by options
```javascript
findByPartialButtonText = function(searchText, using)
```
finds button(s) by textual content fragment
```javascript
findAllRepeaterRows = function(using, repeater)
```
finds an array of elements matching a row within an `ng-repeat`
```javascript
findRepeaterColumn = function(repeater, exact, binding, using, rootSelector)
```
finds the elements in a column of an `ng-repeat`
```javascript
findRepeaterElement = function(repeater, exact, index, binding, using, rootSelector)
```
finds an element within an `ng-repeat` by its row and column.
```javascript
findSelectedOption = function(model, using)
```
finds the selected option elements by model name (Angular 1.x).
```javascript
findSelectedRepeaterOption = function(repeater, using)
```
finds selected option elements in the select implemented via repeater without a model.
```javascript
TestForAngular = function(attempts)
```
tests whether the angular global variable is present on a page
```javascript
waitForAngular = function(rootSelector, callback)
```
waits until Angular has finished rendering
```javascript
return angular.element(element).scope().$eval(expression)
```
evaluates an Angular expression in the context of a given element.

These are 
implemented in a form of Javascript snippets, one file per method, 
borrowed from __Protractor__  project's [`clientsidescripts.js`](https://github.com/angular/protractor/blob/master/lib/clientsidescripts.js)
and can be found in the `src/main/java/resources` directory:
```bash
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

Many __AngularJS__-specific locators [aren't any longer supported](https://stackoverflow.com/questions/36201691/protractor-angular-2-failed-unknown-error-angular-is-not-defined) by __Angular 2__.

The newest Protractor [`By.deepCss` (Shadow DOM)](http://www.protractortest.org/#/api?view=ProtractorBy.prototype.deepCss) and [flexible `By.addLocator`](http://www.protractortest.org/#/api?view=ProtractorBy.prototype.addLocator) are not yet supported.

The __E2E_Tests__ section of the [document](https://angular.io/guide/upgrade) covers the migration.

The standard pure Java Selenium locators are also supported.

#### Forum
There is a [Protractor Java and c# Client](https://gctor Java and c# Clientroups.google.com/forum/#!forum/protractor-java--c-client) forum.

#### Presentation on June 2016 South Florida Tester Meetup

![ Providing Protractor to Java / .Net](https://github.com/sergueik/selenium_java/blob/master/protractor/jProtractor.odp?raw=true)

### Building the jar

The snapshot release of `jprotractor.jar` is published to https://oss.sonatype.org/content/repositories/snapshots/com/github/sergueik/jprotractor/jprotractor/ and to make it the dependency add the following into the `pom.xml`:
```xml
    <dependency>
      <groupId>com.github.sergueik.jprotractor</groupId>
      <artifactId>jprotractor</artifactId>
      <version>1.12-SNAPSHOT</version>
      <exclusions>
        <exclusion>
          <groupId>org.seleniumhq.selenium</groupId>
          <artifactId>selenium-java</artifactId>
        </exclusion>
        <exclusion>
          <groupId>org.seleniumhq.selenium</groupId>
          <artifactId>selenium-server</artifactId>
        </exclusion>
      </exclusions>
    </dependency>

```
and create or modify the `repositories` section of the `pom.xml`:
```xml
  <repositories>
    <repository>
      <id>ossrh</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
  </repositories>
```

You can build the `jprotractor.jar` locally from the source by cloning the repository
```bash
git clone https://github.com/sergueik/jProtractor
```
and running the following in console:

#### Windows
```cmd
set M2=c:\java\apache-maven-3.2.1\bin
set M2_HOME=c:\java\apache-maven-3.2.1
set MAVEN_OPTS=-Xms256m -Xmx512m
set JAVA_VERSION=1.8.0_112
set JAVA_HOME=c:\java\jdk%JAVA_VERSION%
PATH=%JAVA_HOME%\bin;%PATH%;%M2%
REM
REM move %USERPROFILE%\.M2 %USERPROFILE%\.M2.MOVED
REM rd /s/q %USERPROFILE%\.M2
set TRAVIS=true
mvn -Dmaven.test.skip=true clean package
```
#### Linux
```bash
export TRAVIS=true
mvn -Dmaven.test.skip=true clean package
```

The project contains substantial number of 'integration tests' and by default maven will run all, which will take quite some time,
also some of the tests could fail in your environment.
After a test failure, maven will not package the jar.

Alternatively you can temporarily remove the `src/test/java` directory from the project:
```bash
rm -f -r src/test/java
mvn clean package
```

The jar will be in the `target` folder:
```cmd
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ jprotractor ---
[INFO] Building jar: C:\developer\sergueik\jProtractor\target\jprotractor-1.2-SNAPSHOT.jar
```
You can install it into your local `.m2` repository as  explained below

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

### Example Tests

There is a big number of examples in  [`src/test/java/com/jprotractor/integration`](https://github.com/sergueik/jProtractor/tree/master/src/test/java/com/jprotractor/integration) directory.

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
The project tested to work on Linux and Windows with Java 1.8, Selenium 2.53, Selenium 3.0.1, Firefox 45 or Chrome 56. The __jProtractor__ itself is fully compatible with Selenium  __3.x__ though its test suite might not be  fully compatible. 
Download desired version of Firefox from
[ubuntuzilla](https://sourceforge.net/projects/ubuntuzilla/files/mozilla/apt/pool/main/f/firefox-mozilla-build/) or [ftp.mozilla.org](https://ftp.mozilla.org/pub/firefox/)) and `selenium-server-standalone-2.47.1.jar'. Below is the hub setup commands:

```bash
export NODE_PORT=5555
export HUB_IP_ADDRESS=127.0.0.1
export HUB_PORT=4444
export SELENIUM_JAR_VERSION=2.53.1
export DISPLAY_PORT=0
export SELENIUM_HOME=`pwd`
export LAUNCHER_OPTS='-XX:MaxPermSize=256M -Xmn128M -Xms256M -Xmx256M'
export NODE_CONFIG=node.json
export NODE_HOST=127.0.0.1

java $LAUNCHER_OPTS -jar ${SELENIUM_HOME}/selenium-server-standalone-${SELENIUM_JAR_VERSION}.jar -role node -host $NODE_HOST -port $NODE_PORT -hub http://${HUB_IP_ADDRESS}:${HUB_PORT}/hub/register -nodeConfig $NODE_CONFIG -browserTimeout 12000 -timeout 12000 -ensureCleanSession true -trustAllSSLCertificates

export HUB_PORT=4444

java $LAUNCHER_OPTS -jar ${SELENIUM_HOME}/selenium-server-standalone-${SELENIUM_JAR_VERSION}.jar -role hub  -port $HUB_PORT
```
### Keyword-Driven Frameworks

For example of [Keyword-Driven Framework](http://toolsqa.com/selenium-webdriver/keyword-driven-framework/introduction/), incorporating the Selenium Protractor methods see [sergueik\SKDF](https://github.com/sergueik/SKDF). It contains both __jProtractor__ and a similar Java-based Protractor Client Library wrapper ptoject, [paul-hammant/ngWebDriver](https://github.com/paul-hammant/ngWebDriver).

### Related Projects
  - [Protractor-jvm](https://github.com/F1tZ81/Protractor-jvm)
  - [ngWebDriver](https://github.com/paul-hammant/ngWebDriver)
  - [angular/protractor](https://github.com/angular/protractor)
  - [bbaia/protractor-net](https://github.com/bbaia/protractor-net)
  - [sergueik/protractor-net](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)
  - [henrrich/jpagefactory](https://github.com/henrrich/jpagefactory)
  - [PiotrWysocki/globalsqa.com](https://github.com/PiotrWysocki/globalsqa.com) (NOTE: currently using regular Selenium locators with Angular pages)
  - [http://www.globalsqa.com/angularjs-protractor-practice-site](http://www.globalsqa.com/angularjs-protractor-practice-site/)

### Work in progress

Converting the integration tests to Selenium 3.3.x `ExpectedCondition` [deprecation](https://selenium2.ru/blog/187-predicates-in-explicit-waits.html) - [forum (russian)](http://software-testing.ru/forum/index.php?/topic/34732-selenium-331-java-perestal-rabotat-predikat-kto-pobedil/)

### Note

The [ngWebDriver](https://github.com/paul-hammant/ngWebDriver) and the __jProtractor__ projects are very similar in that they construct Java classes wrapping avascript Protractor methods.
The internal class hierachy is different, of course.

It appears easier to construct the Page object factory `By` annotations (interfaces) describing the
__jProtractor__ library methods than to do the same with the __ngWebDriver__.

The other difference is that __jProtractor__ splits the original javascript helper libary into
small chunks and during execution of a `find` method sends only the specific fragment where the locator in question is implements,
to the  browser.  The __ngWebDriver__ always sends the whole library.

This also allows one to patch the individual api (it was important a few years ago) and adding new api (currently, only one such API was added, the `selectedRepeaterOption`. This of course comes at a
higher cost of integrating the "upstream" changes, but the genuine Protracror project is
no longer evolving as quickly as it used to.
### See Also

 * [curious Case of Protractor and Page Synchronization](https://jarifibrahim.github.io/blog/protractor-and-page-synchronization/) blog
 * older post on [deprecation ana soon future removal of the WebDriverJS promise manager from SelnoumHQ](https://github.com/SeleniumHQ/selenium/issues/2969)
 * post on [Protractor end of life or support what next Future of Angular E2E](https://ganeshsirsi.medium.com/protractor-end-of-life-what-next-can-cypress-replace-the-protractor-3c58c0ecbb1c)
 * issue on SeleniumHQ on [future of Angular E2E & Plans for Protractor](https://github.com/angular/protractor/issues/5502)

### Authors
* [Carlos Alexandro Becker](caarlos0@gmail.com)
* [Serguei Kouzmine](kouzmine_serguei@yahoo.com)
