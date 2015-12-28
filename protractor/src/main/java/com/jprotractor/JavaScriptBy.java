package com.jprotractor;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

public class JavaScriptBy extends By
{
	private String Script;
	private Object[] Args;
	public WebElement RootElement;
	
	
	public JavaScriptBy(String script, Object ... args)
	{
		this.Script = script;
		this.Args = args;
	}
	
	
	@Override
	public WebElement findElement(SearchContext context) 
	{
		List<WebElement> elements = this.findElements(context);
		if(elements.size() > 0)
		{
			return elements.get(0);
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public List<WebElement> findElements(SearchContext arg0) throws WebDriverException
	{
		Object[] scriptArgs = new Object[this.Args.length +1];
		
		scriptArgs[0] = this.RootElement;
		System.arraycopy(this.Args, 0, scriptArgs, 1, this.Args.length);
		JavascriptExecutor jsExecutor = null;
		
		// JavaScript Executor
		if(!(arg0 instanceof WebElement))
		{
			jsExecutor = (JavascriptExecutor)arg0;
		}
		
		if(jsExecutor == null)
		{
			WrapsDriver wrapsDriver =  (WrapsDriver)arg0;
			if(wrapsDriver != null)
			{
				jsExecutor = (JavascriptExecutor)wrapsDriver.getWrappedDriver();
			}
		}
		
		if(jsExecutor == null)
		{
			throw new WebDriverException("Could not get an IJavaScriptExecutor instance from the context.");
		}
		
		@SuppressWarnings("unchecked")
		List<WebElement> elements = (List<WebElement>)jsExecutor.executeScript(this.Script, scriptArgs);
		if(elements == null)
		{
			elements = new ArrayList<WebElement>();
		}
		
		return elements;
	}

}
