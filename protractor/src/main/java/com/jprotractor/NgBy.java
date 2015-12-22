package com.jprotractor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.jprotractor.bys.ByBinding;
import com.jprotractor.bys.ByModel;
import com.jprotractor.bys.ByOptions;
import com.jprotractor.bys.ByRepeater;
import com.jprotractor.bys.ByButtonText;

public abstract class NgBy extends By {
	public static By model(final String model, final WebDriver driver) {
		return new ByModel(model, driver);
	}

	public static By binding(final String binding, final WebDriver driver) {
		return new ByBinding(driver, binding);
	}

	public static By options(final String options, final WebDriver driver) {
		return new ByOptions(options, driver);
	}

	public static By buttonText(final String buttonText, final WebDriver driver) {
		return new ByButtonText(buttonText, driver);
	}

	public static By repeater(final String repeater, final WebDriver driver) {
		return new ByRepeater(repeater, driver );
	}


}
