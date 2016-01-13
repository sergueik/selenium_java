Note
----

This github project is the 'development branch' of the collaborated project 
[jProtratractor](https://github.com/caarlos0/jProtractor)
Info
----


Goal is to close the gap between [jProtractor locator snippets](https://github.com/sergueik/jProtractor/tree/master/src/main/resources) and [genuine protractor ones](https://github.com/angular/protractor/blob/master/lib/clientsidescripts.js)

Tests
-----
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
For testing your code with  jprotractor.jar, add it to `src/main/resources`:
add 
```
<dependency>
  <groupId>com.jprotractor</groupId>
  <artifactId>jprotractor</artifactId>
  <version>${jprotractor.version}</version>
  <scope>system</scope>
  <systemPath>${project.basedir}/src/main/resources/jprotractor-${jprotractor.version}.jar</systemPath>
</dependency>
```

Note
----
PhantomJs allows loading Angular samples via `file://` content:

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
Tests involving `NgBy.selectedOption()` currently fail under [travis](https://travis-ci.org/) build.


Related Projects 
----------------
  - [Protractor-jvm](https://github.com/F1tZ81/Protractor-jvm)
  - [ngWebDriver](https://github.com/paul-hammant/ngWebDriver)
  - [angular/protractor](https://github.com/angular/protractor) 
  - [bbaia/protractor-net](https://github.com/bbaia/protractor-net)
  - [sergueik/protractor-net](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)


Author
------
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
