package com.jprotractor.bys;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ByRepeater extends JsBy {
	private final String repeater;

	public ByRepeater(String repeater, WebDriver executor) {
		super(executor);
		this.repeater = repeater;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WebElement> findElements(SearchContext context) {
		return (List<WebElement>) executor.executeScript(getScriptContent()
				+ "return findAllRepeaterRows(arguments[0], arguments[1])", repeater,
				getElement(context));
	}

	@Override
	public String toString() {
		return "By.repeater: " + repeater;
	}

	@Override
	protected String getFilename() {
		return "repeater.js";
	}

}
