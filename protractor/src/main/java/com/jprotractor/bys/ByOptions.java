package com.jprotractor.bys;

import java.util.List;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ByOptions extends JsBy {
	private final String options;

	public ByOptions(String options, WebDriver executor) {
		super(executor);
		this.options = options;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WebElement> findElements(SearchContext context) {
		return (List<WebElement>) executor.executeScript(getScriptContent()
				+ "return findByOptions(arguments[0], arguments[1])", options,
				getElement(context));
	}

	@Override
	public String toString() {
		return "By.options: " + options;
	}

	@Override
	protected String getFilename() {
		return "options.js";
	}
}
