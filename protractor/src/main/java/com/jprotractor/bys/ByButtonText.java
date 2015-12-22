package com.jprotractor.bys;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// buttonText.js tries partial match of element.textContent or element.innerText. 
// It is not really using any Angular-specific MVC
public class ByButtonText extends JsBy {
	private final String buttonText;

	public ByButtonText(String buttonText, WebDriver executor) {
		super(executor);
		this.buttonText = buttonText;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<WebElement> findElements(SearchContext context) {
		return (List<WebElement>) executor.executeScript(getScriptContent()
				+ "return findByPartialButtonText(arguments[0], arguments[1])", buttonText,
				getElement(context));
	}

	@Override
	public String toString() {
		return "By.buttonText: " + buttonText;
	}

	@Override
	protected String getFilename() {
		return "buttonText.js";
	}

}
