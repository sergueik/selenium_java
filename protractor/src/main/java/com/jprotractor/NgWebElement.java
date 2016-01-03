package com.jprotractor;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

public class NgWebElement implements WebElement, WrapsElement
{

	private NgWebDriver ngDriver;
    private WebElement element;
    
    public NgWebElement(NgWebDriver ngDriver, WebElement element) 
    {
		this.ngDriver = ngDriver;
		this.element = element;
	}
    
	public WebElement getWrappedElement() {
		return this.element;
	}

	public void clear() {
		this.ngDriver.WaitForAngular();
		this.element.clear();
		
	}

	public void click() {
		this.ngDriver.WaitForAngular();
        this.element.click();
	}

	public Object evaluate(String expression){
		this.ngDriver.WaitForAngular();
		JavascriptExecutor jsExecutor = (JavascriptExecutor)this.ngDriver.getWrappedDriver();
		return jsExecutor.executeScript(ClientSideScripts.Evaluate, this.element, expression);
	}
	
	public NgWebElement findElement(By arg0) {
	
		if (arg0 instanceof JavaScriptBy)
        {
            ((JavaScriptBy)arg0).RootElement = this.element;
        }
		
		this.ngDriver.WaitForAngular();
        return new NgWebElement(this.ngDriver, this.element.findElement(arg0));
	}
	
	public List<NgWebElement> findNgElements(By arg0)
	{
		
		List<WebElement> temp = findElements(arg0);
        List<NgWebElement> returnElements = new ArrayList<NgWebElement>();
        for(WebElement currrentEle : temp)
        {
        	returnElements.add(new NgWebElement(ngDriver, currrentEle));
        }
        return returnElements;
	}

	public List<WebElement> findElements(By arg0) 
	{
		if (arg0 instanceof JavaScriptBy)
        {
            ((JavaScriptBy)arg0).RootElement = this.element;
        }
		List<WebElement> returnElements = this.element.findElements(arg0);
        this.ngDriver.WaitForAngular();
        return returnElements;
	}
	

	public String getAttribute(String arg0) {
		this.ngDriver.WaitForAngular();
		return this.element.getAttribute(arg0);
	}

	public String getCssValue(String arg0) {
		this.ngDriver.WaitForAngular();
		return this.element.getCssValue(arg0);
	}

	public Point getLocation() {
		this.ngDriver.WaitForAngular();
        return this.element.getLocation();
	}

	public Dimension getSize() {
		this.ngDriver.WaitForAngular();
        return this.element.getSize();
	}

	public String getTagName() {
		this.ngDriver.WaitForAngular();
		return this.element.getTagName();
	}

	public String getText() {
		this.ngDriver.WaitForAngular();
		return this.element.getText();
	}

	public boolean isDisplayed() {
		this.ngDriver.WaitForAngular();
		return this.element.isDisplayed();
	}

	public boolean isEnabled() {
		this.ngDriver.WaitForAngular();
		return this.element.isEnabled();
	}

	public boolean isSelected() {
		this.ngDriver.WaitForAngular();
		return this.element.isSelected();
	}

	public void sendKeys(CharSequence... arg0) {
		this.ngDriver.WaitForAngular();
		this.element.sendKeys(arg0);
		
	}

	public void submit() {
		this.ngDriver.WaitForAngular();
		this.element.submit();
		
	}

	public <X> X getScreenshotAs(OutputType<X> arg0) throws WebDriverException {
		// TODO Auto-generated method stub
		return null;
	}

}
