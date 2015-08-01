package com.mycompany.app;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class App {
public static void main(String[] args) {

	final String sUrl = "http://www.google.co.in/";
	File file = new File("C:/java/selenium/IEDriverServer.exe");
	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	WebDriver oWebDriver = new InternetExplorerDriver();
	oWebDriver.get(sUrl);
	WebElement oSearchInputElem = oWebDriver.findElement(By.name("q")); // Use name locator to identify the search input field.
// oSearchInputElem.sendKeys(null);
	WebElement oGoogleSearchBtn = oWebDriver.findElement(By.xpath("//input[@name=’btnG’]"));
	oGoogleSearchBtn.click();

	try {
		Thread.sleep(5000);
	} catch(InterruptedException ex) {
		System.out.println(ex.getMessage());
	}
	oWebDriver.close();
}
}

