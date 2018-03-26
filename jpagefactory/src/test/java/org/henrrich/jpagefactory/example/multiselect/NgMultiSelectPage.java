package org.henrrich.jpagefactory.example.multiselect;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;

import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;
import com.github.sergueik.jprotractor.NgBy;

import org.junit.Assert;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Created by sergueik on 01/08/2016.
 */
public class NgMultiSelectPage {

	private NgWebDriver ngDriver;

	public void setDriver(NgWebDriver ngDriver) {
		this.ngDriver = ngDriver;
	}

	// the @FindBy annotation below gives an example of defining different
	// locators

	@FindBy(how = How.MODEL, using = "selectedCar")
	private WebElement directive;

	@FindBy(how = How.BUTTON_TEXT, using = "Select Some Cars")
	private WebElement toggleSelect;

	@FindAll({ @FindBy(how = How.REPEATER, using = "i in items") })
	private List<WebElement> cars;

	@FindBy(how = How.CSS, using = "am-multiselect > div > button")
	private WebElement button;

	public void openSelect() {
		Assert.assertThat("Should be able to select cars", directive.getTagName(),
				equalTo("am-multiselect"));

		Assert.assertTrue("Should be able to select cars",
				toggleSelect.isDisplayed());
		toggleSelect.click();
	}

	public int countCars() {
		return cars.size();
	}

	public void selectAllCars() {
		for (int rowNum = 0; rowNum != countCars(); rowNum++) {
			// For every row, click on the car name
			NgWebElement ngCar = new NgWebElement(ngDriver, directive)
					.findElement(NgBy.repeaterElement("i in items", rowNum, "i.label"));
			try {
				System.err.println(ngCar.getAttribute("innerHTML"));
			} catch (NullPointerException e) {
				// ignore
			}
			try {
				System.err.println("* " + ngCar.evaluate("i.label").toString());
				ngCar.click();
			} catch (WebDriverException e) {
				// ignore
			}
		}
	}

	public String getStatus() {
		return button.getText();
		// return _directive.getText();
	}
}
