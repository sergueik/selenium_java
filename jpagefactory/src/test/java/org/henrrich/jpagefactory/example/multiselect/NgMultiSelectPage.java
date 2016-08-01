package org.henrrich.jpagefactory.example.multiselect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jprotractor.NgWebDriver;
import com.jprotractor.NgWebElement;
import com.jprotractor.NgBy;

/**
 * Created by sergueik on 01/08/2016.
 */
public class NgMultiSelectPage {

	private NgWebDriver _driver;

	public void setDriver(NgWebDriver driver) {
		this._driver = driver;
	}

	public NgWebDriver getDriver() {
		return _driver;
	}

	// the @FindBy annotation below gives an example of defining different
	// locators

	@FindBy(how = How.MODEL, using = "selectedCar")
	private WebElement _directive;

	@FindBy(how = How.BUTTON_TEXT, using = "Select Some Cars")
	private WebElement _toggleSelect;

	@FindAll({ @FindBy(how = How.REPEATER, using = "i in items") })
	private List<WebElement> _cars;

	@FindBy(how = How.CSS, using = "am-multiselect > div > button")
	private WebElement _button;

	public void openSelect() {
		Assert.assertThat("Should be able to select cars", _directive.getTagName(),
				equalTo("am-multiselect"));

		Assert.assertTrue("Should be able to select cars",
				_toggleSelect.isDisplayed());
		_toggleSelect.click();
	}

	public int countCars() {
		return _cars.size();
	}

	public void selectAllCars() {
		for (int rowNum = 0; rowNum != countCars(); rowNum++) {
			// For every row, click on the car name
			NgWebElement _ng_car = new NgWebElement(_driver, _directive)
					.findElement(NgBy.repeaterElement("i in items", rowNum, "i.label"));
			try {
        System.err.println(_ng_car.getAttribute("innerHTML"));
			} catch (NullPointerException e ) {
			 // ignore
			}
			try {
				System.err.println("* " + _ng_car.evaluate("i.label").toString());
				_ng_car.click();
			} catch(WebDriverException e) {
				// ignore 
      }
		}
	}

	public String getStatus() {
		return _button.getText();
		// return _directive.getText();
	}
}
