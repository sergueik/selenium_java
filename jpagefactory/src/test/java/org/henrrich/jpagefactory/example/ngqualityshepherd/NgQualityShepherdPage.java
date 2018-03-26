package org.henrrich.jpagefactory.example.ngqualityshepherd;

import java.util.List;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;
import org.openqa.selenium.WebElement;

import com.github.sergueik.jprotractor.NgWebDriver;

/**
 * Created by sergueik on 31/07/2016. 
 * Changed on 3/24/2018 
 */
public class NgQualityShepherdPage {

	private NgWebDriver ngDriver;

	public void setDriver(NgWebDriver ngDriver) {
		this.ngDriver = ngDriver;
	}

	// the @FindBy annotation below gives an example of defining different
	// locators
	@FindAll({
			@FindBy(how = How.REPEATER_COLUMN, using = "row in rows", column = "row") })
	private List<WebElement> friendNames;

	@FindAll({ @FindBy(how = How.REPEATER, using = "row in rows") })
	private List<WebElement> friendRows;

	@FindBy(how = How.REPEATER_ELEMENT, using = "row in rows", column = "row", row = 1)
	private WebElement friendNameElement;

	public int countFriendNames() {
		return friendNames.size();
	}

	public int countFriendRows() {
		return friendRows.size();
	}

	public String getFriendName(int index) {
		WebElement friendName = friendNames.get(index);
		return friendName.getText();
	}

	public String getFirstFriendName() {
		return friendNameElement.getText();
	}

}
