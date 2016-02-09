package com.mycompany.app;

import java.util.List;      
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;





import com.jprotractor.NgBy;
import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;


import org.testng.*;
import org.testng.annotations.*;
// http://www.guru99.com/using-apache-ant-with-selenium.html
public class Program {

	private static NgWebDriver ngDriver;
	private static WebDriver driver;
	static WebDriverWait wait;
	static Actions actions;
	static Alert alert;
	static int implicitWait = 10;
	static int flexibleWait = 5;
	static long pollingInterval = 500;
	static int width = 600;
	static int height = 400;

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite( ITestContext context ) throws InterruptedException {
	}
	
    @Test      
    public void TestMethod(){
        driver = new FirefoxDriver(); 
        driver.get("http://guru99.com");
        List<WebElement> links = driver.findElements(By.xpath("//div[@class='canvas-middle']//a"));                                 
		for (WebElement webElement : links) {
			System.out.println(webElement.getAttribute("href"));
		}
	}
	@AfterSuite(alwaysRun = true,enabled =true) 
	public void cleanupSuite() {
			System.out.println("testClass1.cleanupSuite: after suite");
			driver.quit();
	    }

}       
