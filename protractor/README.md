Info
----

Origins: 
  - [jProtractor](https://github.com/caarlos0/jProtractor)
  - [angular/protractor](https://github.com/angular/protractor) 
  - [bbaia/protractor-net](https://github.com/bbaia/protractor-net)
  - [sergueik/protractor-net](https://github.com/sergueik/powershell_selenium/tree/master/csharp/protractor-net)

Goal is to close the gap between [jProtractor locator snippets](https://github.com/sergueik/jProtractor/tree/master/src/main/resources) and [genuine protractor ones](https://github.com/angular/protractor/blob/master/lib/clientsidescripts.js)
Samples

For desktop browser testing, run a Selenium hub and node and 
------
```
    DesiredCapabilities capabilities =   new DesiredCapabilities("firefox", "", Platform.ANY);
    FirefoxProfile profile = new ProfilesIni().getProfile("default");
    capabilities.setCapability("firefox_profile", profile);
    seleniumDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    ngDriver = NgDriverEnchancer.enchance(seleniumDriver , NgByIntegrationTest.class
                                .getClassLoader().getResource("integrationTests.properties"));

    ngDriver.navigate().to("http://juliemr.github.io/protractor-demo/");
    WebElement element = ngDriver.findElement(NgBy.model("first", ngDriver));
    assertThat(element,notNullValue());
    element.sendKeys("40");
    element = ngDriver.findElement(NgBy.model("second", ngDriver));
    element.sendKeys("2");
    element = ngDriver.findElement(NgBy.options("value for (key, value) in operators", ngDriver));
    assertThat(element,notNullValue());
    element.click();
    element = ngDriver.findElement(NgBy.buttonText("Go", ngDriver));
    assertThat(element,notNullValue());
    element = seleniumDriver.findElement(By.xpath("//button[contains(.,'Go')]"));
    assertThat(element,notNullValue());
    element.click();
    Thread.sleep(5000);
    element = ngDriver.findElement(NgBy.binding("latest", ngDriver)); 
    assertThat(element,notNullValue());
    assertThat(element.getText(), equalTo("42"));
    highlight(element, 100);
    ngDriver.close();
    seleniumDriver.quit();

```
for CI build replace the Setup () with
```
	public static void setup() throws IOException {
		seleniumDriver = new PhantomJSDriver();
		ngDriver = NgDriverEnchancer.enchance(seleniumDriver , NgByIntegrationTest.class
				.getClassLoader().getResource("integrationTests.properties"));
	}

```
Testing
-------
PhantomJs allows loading Angular samples as `file://` content.

Author
------
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
