package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.File;
import java.net.URI;
import java.net.MalformedURLException;
import java.net.BindException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jprotractor.NgBy;
import com.jprotractor.categories.Integration;
import com.jprotractor.unit.NgDriverEnchancer;

@RunWith(Enclosed.class)
@Category(Integration.class)
public class NgByTestIntegrationTest {
	private static WebDriver ngDriver;
	private static WebDriver seleniumDriver;
	public static String seleniumBrowser = "chrome";

	@BeforeClass
	public static void setup() throws IOException {
	    
		DesiredCapabilities capabilities =   new DesiredCapabilities(seleniumBrowser, "", Platform.ANY);
		FirefoxProfile profile = new ProfilesIni().getProfile("default");
		capabilities.setCapability("firefox_profile", profile);
		seleniumDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
		
		/* PhantomJSDriver */ 
		/*
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
		seleniumDriver = new PhantomJSDriver(capabilities); 
		*/
		try{
			seleniumDriver.manage().window().setSize(new Dimension(600, 800));
			seleniumDriver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			seleniumDriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		}  catch(Exception ex) {
			System.out.println(ex.toString());
		}

		ngDriver = NgDriverEnchancer.enchance(seleniumDriver , NgByTestIntegrationTest.class
				.getClassLoader().getResource("integrationTests.properties"));
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
	}
	public static class WithAngularJsHomePage {

		@Before
		public void beforeEach() {
			ngDriver.navigate().to("https://angularjs.org");
		}

		@Test
		public void testByModel() throws Exception {
			assertThat(ngDriver.findElement(NgBy.model("yourName", ngDriver)),
					notNullValue());
		}

		@Test
		public void testByBinding() throws Exception {
			ngDriver.findElement(NgBy.model("yourName", ngDriver)).sendKeys("jProtractor");
			WebElement element = ngDriver.findElements(NgBy.binding(
					"yourName", ngDriver)).get(0);
			assertThat(element.getText(), equalTo("Hello jProtractor!"));
		}

		@Test
		public void testByBindingWithParent() throws Exception {
			ngDriver.findElement(NgBy.model("yourName", ngDriver)).sendKeys("jProtractor");
			WebElement element = ngDriver.findElement(NgBy.cssSelector(".container"))
					.findElements(NgBy.binding("yourName", ngDriver)).get(0);
			assertThat(element.getText(), equalTo("Hello jProtractor!"));
		}

	}
    
	public static class WithAngularSelectDocumentationPage {

		@Before
		public void init() {
			ngDriver.navigate()
					.to("https://docs.angularjs.org/api/ng/directive/select");
			ngDriver.switchTo().frame("example-example49");
		}

		@After
		public void after() {
			ngDriver.switchTo().defaultContent();
		}
		@Test
		public void testByOptions() throws Exception {
			By selector = NgBy
					.options("color.name for color in colors", ngDriver);
			WebElement colors = ngDriver.findElement(selector);
			assertThat(colors.getText(), equalTo("black"));
		}

	}

    
	public static class WithLocalAngularJsRepeaterExamplePage {
        private URI uri = new File("/c/developer/sergueik/selenium_java/protractor/src/main/resources/ng_table1.html").toURI();
		@Before
		public void beforeEach() throws MalformedURLException {
			ngDriver.navigate().to(/* uri.toURL()*/ "file:///c:/developer/sergueik/selenium_java/protractor/src/main/resources/ng_table1.html");
		}

		@Test
		public void testByRepeater() throws Exception {
			assertThat(ngDriver.findElement(NgBy.repeater("x in names", ngDriver)),
					notNullValue());
		}

	}
	public static class WithLocalAngularJsOptionsExamplePage {
        private URI uri = new File("c:/developer/sergueik/selenium_java/protractor/src/main/resources/ng_options_with_object_example.htm").toURI();
		@Before
		public void beforeEach() throws MalformedURLException {
			ngDriver.navigate().to( uri.toURL()/*  "file:///c:/developer/sergueik/selenium_java/protractor/src/main/resources/ng_options_with_object_example.htm"*/);
		}

		@Test
		public void testByOptions() throws Exception {
			assertThat(ngDriver.findElement(NgBy.options("c.name for c in colors", ngDriver)),
					notNullValue());
		}

	}

}
