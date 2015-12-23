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

import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.jprotractor.NgBy;
import com.jprotractor.categories.Integration;
import com.jprotractor.unit.NgDriverEnchancer;

@RunWith(Enclosed.class)
@Category(Integration.class)
	public class NgByIntegrationTest {
	private static WebDriver ngDriver;
	private static WebDriver seleniumDriver;
	static WebDriverWait wait;
	static Actions actions;

	@BeforeClass
	public static void setup() throws IOException {
		seleniumDriver = new PhantomJSDriver();
		ngDriver = NgDriverEnchancer.enchance(seleniumDriver , NgByIntegrationTest.class
				.getClassLoader().getResource("integrationTests.properties"));
	}

	@AfterClass
	public static void teardown() {
		ngDriver.close();
        seleniumDriver.quit();		
	}
	
	private static void highlight(WebElement element, long highlight_interval) throws InterruptedException {
		int flexible_wait_interval = 5;
		long wait_polling_interval = 500;
		if (wait == null)         {
			wait = new WebDriverWait(seleniumDriver, flexible_wait_interval );
		}
		wait.pollingEvery(wait_polling_interval,TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions.visibilityOf(element));
		execute_script("arguments[0].style.border='3px solid yellow'", element);
		Thread.sleep(highlight_interval);
		execute_script("arguments[0].style.border=''", element);
	}

	public static Object execute_script(String script,Object ... args){
		if (seleniumDriver instanceof JavascriptExecutor) {
			JavascriptExecutor javascriptExecutor=(JavascriptExecutor)seleniumDriver;
			return javascriptExecutor.executeScript(script,args);
		}
		else {
			throw new RuntimeException("Script execution is only available for WebDrivers that implement " + "the JavascriptExecutor interface.");
		}
	}

	public static class CalculatorTests{

		public static String baseUrl = "http://juliemr.github.io/protractor-demo/";
   		@Before
		public void beforeEach() {		    
			ngDriver.navigate().to(baseUrl);
		}

		@Test
		public void testAddition() throws Exception {
			WebElement element = ngDriver.findElement(NgBy.model("first", ngDriver));
			assertThat(element,notNullValue());
			highlight(element, 100);
			element.sendKeys("40");
			element = ngDriver.findElement(NgBy.model("second", ngDriver));
			assertThat(element,notNullValue());
			highlight(element, 100);
			element.sendKeys("2");
			element = ngDriver.findElement(NgBy.options("value for (key, value) in operators", ngDriver));
			assertThat(element,notNullValue());
			element.click();
                        element = ngDriver.findElement(NgBy.buttonText("Go", ngDriver));
			assertThat(element,notNullValue());
			element = seleniumDriver.findElement(By.xpath("//button[contains(.,'Go')]"));
			assertThat(element,notNullValue());
			element.click();
			Thread.sleep(3000);
			element = ngDriver.findElement(NgBy.binding("latest", ngDriver)); 
			assertThat(element,notNullValue());
			assertThat(element.getText(), equalTo("42"));
			highlight(element, 100);
		}
	}
	public static class Way2AutomationTests {

		public static String baseUrl = "http://www.way2automation.com/angularjs-protractor/banking";
   		@Before
		public void beforeEach() throws InterruptedException{
			ngDriver.navigate().to(baseUrl);
			Thread.sleep(3000);
		}

		@Test
		public void testButtons() throws Exception {
			WebElement element = ngDriver.findElement(NgBy.buttonText("Customer Login", ngDriver));
			highlight(element, 100);
		}
	}
}