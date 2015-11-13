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

import com.jprotractor.NgBy;
import com.jprotractor.categories.Integration;
import com.jprotractor.unit.NgDriverEnchancer;

@RunWith(Enclosed.class)
@Category(Integration.class)
public class NgByTest {
	private static WebDriver driver;

	@BeforeClass
	public static void setup() throws IOException {
		driver = NgDriverEnchancer.enchance(new FirefoxDriver(), NgByTest.class
				.getClassLoader().getResource("integrationTests.properties"));
	}

	@AfterClass
	public static void teardown() {
		driver.close();
	}

	public static class WithAngularJsHomePage {

		@Before
		public void beforeEach() {
			driver.navigate().to("https://angularjs.org");
		}

		@Test
		public void testByModel() throws Exception {
			assertThat(driver.findElement(NgBy.model("yourName")),
					notNullValue());
		}

		@Test
		public void testByBinding() throws Exception {
			driver.findElement(NgBy.model("yourName")).sendKeys("jProtractor");
			WebElement element = driver.findElements(NgBy.binding(
					"yourName", driver)).get(0);
			assertThat(element.getText(), equalTo("Hello jProtractor!"));
		}

		@Test
		public void testByBindingWithParent() throws Exception {
			driver.findElement(NgBy.model("yourName")).sendKeys("jProtractor");
			WebElement element = driver
					.findElement(NgBy.cssSelector(".container"))
					.findElements(NgBy.binding("yourName", driver)).get(0);
			assertThat(element.getText(), equalTo("Hello jProtractor!"));
		}

	}

	public static class WithAngularSelectDocumentationPage {

		@Before
		public void init() {
			driver.navigate()
					.to("https://docs.angularjs.org/api/ng/directive/select");
			driver.switchTo().frame("example-example49");
		}

		@After
		public void after() {
			driver.switchTo().defaultContent();
		}

		@Test
		public void testByOptions() throws Exception {
			By selector = NgBy
					.options("color.name for color in colors", driver);
			WebElement colors = driver.findElement(selector);
			assertThat(colors.getText(), equalTo("black"));
		}
	}
}
