package com.jprotractor.bys;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// model.js tries all prefixes 'ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:'
public class ByModel extends JsBy {
	private final String model;

	public ByModel(String model, WebDriver executor) {
		super(executor);
		this.model = model;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<WebElement> findElements(SearchContext context) {
		return (List<WebElement>) executor.executeScript(getScriptContent()
				+ "return findByModel(arguments[0], arguments[1])", model,
				getElement(context));
	}

	@Override
	public String toString() {
		return "By.model: " + model;
	}

	@Override
	protected String getFilename() {
		return "model.js";
	}

}
