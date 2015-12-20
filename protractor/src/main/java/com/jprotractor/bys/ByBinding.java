package com.jprotractor.bys;

import java.util.List;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ByBinding extends JsBy {
	private final String model;

	public ByBinding(WebDriver driver, String model) {
		super(driver);
		this.model = model;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WebElement> findElements(SearchContext context) {
		return (List<WebElement>) executor.executeScript(getScriptContent()
				+ "return findBindings(arguments[0], true, arguments[1])", model,
				getElement(context));
	}

	@Override
	public String toString() {
		return "By.binding: " + model;
	}

	@Override
	protected String getFilename() {
		return "binding.js";
	}
}
