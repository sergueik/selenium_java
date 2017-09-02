package demo;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestNGDD {
	
	private RemoteWebDriver driver;
	
	@BeforeSuite
	public void setup() throws MalformedURLException {
		String username = "you%40yourdomain.com";
		String authkey = "yourauthkey";
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", "Chrome");	// Automatically pulls the latest version of Chrome
		caps.setCapability("platform", "Windows 10");	// To specify a version, add setCapability("version", "desired version")
		
		driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey +"@hub.crossbrowsertesting.com:80/wd/hub"), caps);
	}
	
	public Sheet getSpreadSheet() {
		File file = new File("//Path//To//Test.xlsx");
		
		FileInputStream inputStream = null;
		Workbook wb = null;
		try {
			inputStream = new FileInputStream(file);
			wb = WorkbookFactory.create(inputStream);
			System.out.println(wb.toString());
		} catch (IOException ex) {
			System.out.println("Error Message " + ex.getMessage());
		} catch (InvalidFormatException e) {
			System.out.println("Invalid File format!");
		}
	
		Sheet mySheet = wb.getSheet("MySheet");
		
		return mySheet;
	}
	@Test
	public void loginPage() {
		driver.get("http://crossbrowsertesting.github.io/login-form.html");
		
		Sheet mySheet = getSpreadSheet();
		
		String user = mySheet.getRow(0).getCell(0).toString();
		String pass = mySheet.getRow(0).getCell(1).toString();
		
		// the first time around, it should not work!
		driver.findElementByName("username").sendKeys(user);
        
        // then by entering the password
        System.out.println("Entering password");
        driver.findElementByName("password").sendKeys(pass);
        
        // then by clicking the login button
        System.out.println("Logging in");
        driver.findElementByCssSelector("div.form-actions > button").click();
        
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("/html/body/div/div/div/div[1]"), "Username or password is incorrect"));
		
        // however, with the correct credentials, it should!
        driver.get("http://crossbrowsertesting.github.io/login-form.html");
		
		user = mySheet.getRow(1).getCell(0).toString();
		pass = mySheet.getRow(1).getCell(1).toString();
		
		driver.findElementByName("username").sendKeys(user);
        
        // then by entering the password
        System.out.println("Entering password");
        driver.findElementByName("password").sendKeys(pass);
        
        // then by clicking the login button
        System.out.println("Logging in");
        driver.findElementByCssSelector("div.form-actions > button").click();
		
        // let's wait here to ensure the page has loaded completely
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"logged-in-message\"]/h2")));
        
        String welcomeMessage = driver.findElementByXPath("//*[@id=\"logged-in-message\"]/h2").getText();
        Assert.assertEquals("Welcome tester@crossbrowsertesting.com", welcomeMessage);
        
        System.out.println("TestFinished");
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
	
}

