package org.henrrich.jpagefactory.example.scrollabletable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;
import org.henrrich.jpagefactory.annotations.FindBys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.sergueik.jprotractor.NgWebDriver;
import com.github.sergueik.jprotractor.NgWebElement;

/**
 * Created by sergueik on 3/25/2018 
 */

public class NgScrollableTablePage {

	private NgWebDriver ngDriver;
	private static WebDriverWait wait;

	private static final int implicitWait = 10;
	private static final int flexibleWait = 5;
	private static long pollingInterval = 500;

	public void setDriver(NgWebDriver ngDriver) {
		this.ngDriver = ngDriver;
	}

	@FindAll({ @FindBy(how = How.REPEATER, using = "row in rowCollection") })
	private List<WebElement> rows;

	@FindAll({
			@FindBy(how = How.REPEATER_COLUMN, using = "row in rowCollection", column = "row.firstName") })
	private List<WebElement> firstNames;

	@FindBys({ @FindBy(how = How.REPEATER, using = "row in rowCollection"),
			@FindBy(how = How.BINDING, using = "row.lastName") })
	private List<WebElement> lastNames;

	public int countRows() {
		waitPageLoad();
		return rows.size();
	}

	private void waitPageLoad() {
		wait = new WebDriverWait(ngDriver.getWrappedDriver(), flexibleWait);
		wait.pollingEvery(pollingInterval, TimeUnit.MILLISECONDS);
		wait.until(ExpectedConditions
				.visibilityOf(ngDriver.findElement(By.className("table-container"))));
	}

	// count columns
	public int countFirstNames() {
		waitPageLoad();
		return firstNames.size();
	}

	// count columns, chained
	public int countLastNames() {
		waitPageLoad();
		return lastNames.size();
	}

	public String getFirstName(int index) {
		waitPageLoad();
		WebElement firstNameByIndex = firstNames.get(index);
		return firstNameByIndex.getText();
	}

	public String getLastName(int index) {
		waitPageLoad();
		WebElement lastNameByIndex = lastNames.get(index);
		return lastNameByIndex.getText();
	}

	public String getFullName(int index) {
		waitPageLoad();
		WebElement row = rows.get(index);
		NgWebElement ngRowElement = new NgWebElement(ngDriver, row);
		return String.format("%s %s",
				(String) ngRowElement.evaluate("row.firstName"),
				(String) ngRowElement.evaluate("row.lastName"));
	}

	public String getRow(int index) {
		waitPageLoad();
		WebElement row = rows.get(index);
		return row.getText();
	}
}
