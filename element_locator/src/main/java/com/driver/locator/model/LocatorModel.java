package com.driver.locator.model;

public class LocatorModel {

	private String locatorType;

	public String getLocatorType() {
		return locatorType;
	}

	public String getLocatorValue() {
		return locatorValue;
	}

	private String locatorValue;

	public LocatorModel(String locatorType, String locatorValue) {
		this.locatorType = locatorType;
		this.locatorValue = locatorValue;
	}

	@Override
	public String toString() {
		return locatorType + ":" + locatorValue;
	}

}
