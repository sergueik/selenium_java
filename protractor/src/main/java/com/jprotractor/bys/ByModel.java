package com.jprotractor.bys;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ByModel extends By {
	private final String model;

	public ByModel(String model) {
		super();
		this.model = model;
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		return By.cssSelector("[ng-model='" + model + "']")
				.findElements(context);
	}

	@Override
	public String toString() {
		return "By.model: " + model;
	}
}
