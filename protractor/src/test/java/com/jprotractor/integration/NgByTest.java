package com.jprotractor.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

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
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.jprotractor.NgBy;
import com.jprotractor.categories.Integration;
import com.jprotractor.unit.NgDriverEnchancer;

@RunWith(Enclosed.class)
@Category(Integration.class)
public class NgByTest {
	private static WebDriver ngDriver;
	private static WebDriver seleniumDriver;

	@BeforeClass
	public static void setup() throws IOException {

     // Create instance of PhantomJS driver
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        PhantomJSDriver seleniumDriver = new PhantomJSDriver(capabilities);
 
		ngDriver = NgDriverEnchancer.enchance(seleniumDriver , NgByTest.class
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
}
