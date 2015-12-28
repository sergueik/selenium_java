package com.jprotractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.JavascriptExecutor;

public class NgWebDriver implements WebDriver, WrapsDriver
{
	@SuppressWarnings("unused")
	private String AngularDeferBootstrap = "NG_DEFER_BOOTSTRAP!";
	
	private WebDriver driver;
	private JavascriptExecutor jsExecutor;
	private String rootElement;
	@SuppressWarnings("unused")
	private NgModule[] mockModules;
	public boolean IgnoreSynchronization;
	// little max iteration count for protector
	public int MaxIterations;
	private int iterationCount;

	public NgWebDriver(WebDriver driver)
	{
		this.driver = driver;
		this.jsExecutor = (JavascriptExecutor)driver;
		this.rootElement = "body";
		MaxIterations = 30;
		iterationCount = 0;
	}
	
	public NgWebDriver(WebDriver driver, boolean ignoreSync)
	{
		this(driver);
		this.IgnoreSynchronization = ignoreSync;
	}

	public NgWebDriver(WebDriver driver, NgModule[] mockModules)
	{
		this(driver, "body", mockModules);
	}
	
	public NgWebDriver(WebDriver driver, String rootElement ,NgModule[] mockModules)
	{
		if(!(driver instanceof JavascriptExecutor))
		{
			throw new WebDriverException("The WebDriver instance must implement the JavaScriptExecutor interface.");
		}
		
		this.driver = driver;
		this.jsExecutor = (JavascriptExecutor)driver;
		this.rootElement = rootElement;
		this.mockModules = mockModules;
	}
	
	
	public String getRootElement()
	{
		return this.rootElement;
	}

	public WebDriver getWrappedDriver() {
		return this.driver;
	}

	public void close() {
		this.driver.close();
		
	}

	public NgWebElement findElement(By arg0)
	{
		this.WaitForAngular();
		NgWebElement tempEle = new NgWebElement(this, this.driver.findElement(arg0));
		return tempEle;
	}
	
	/*@Override
	public WebElement findElement(By arg0) {
		this.WaitForAngular();
		return new NgWebElement(this, this.driver.findElement(arg0));
	}*/
	
	public List<NgWebElement> findNGElements(By arg0)
	{
		this.WaitForAngular();
        List<WebElement> temp = this.driver.findElements(arg0);
        // not sure idf this is correct
        List<NgWebElement> returnElements = new ArrayList<NgWebElement>();
        for(WebElement currrentEle : temp)
        {
        	returnElements.add(new NgWebElement(this, currrentEle));
        }
        return returnElements;
	}

	public List<WebElement> findElements(By arg0) {
		return this.driver.findElements(arg0);
	}

	public void get(String arg0) {
		this.WaitForAngular();
		this.driver.navigate().to(arg0);
		
	}

	public String getCurrentUrl() {
		this.WaitForAngular();
		return this.driver.getCurrentUrl();
	}

	public String getPageSource() {
		this.WaitForAngular();
		return this.driver.getPageSource();
	}

	public String getTitle() {
		this.WaitForAngular();
		return driver.getTitle();
	}

	public String getWindowHandle() {
		this.WaitForAngular();
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		this.WaitForAngular();
		return driver.getWindowHandles();
	}

	public Options manage() {
		//this.WaitForAngular();
		return this.driver.manage();
	}

	public Navigation navigate() {
		return new NgNavigation(this, this.driver.navigate());
	}

	public void quit() {
		this.driver.quit();
		
	}

	public TargetLocator switchTo() {
		return this.driver.switchTo();
	}
	
	public void WaitForAngular()
    {
        if (!this.IgnoreSynchronization)
        {
        	iterationCount++;
            this.jsExecutor.executeAsyncScript(ClientSideScripts.WaitForAngular, this.rootElement);
        }
    }
	
}
