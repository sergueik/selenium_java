package org.henrrich.jpagefactory.example.ngqualityshepherd;

import org.henrrich.jpagefactory.How;
import org.henrrich.jpagefactory.annotations.FindAll;
import org.henrrich.jpagefactory.annotations.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergueik on 31/07/2016.
 */
public class NgQualityShepherdPage {

	// the @FindBy annotation below gives an example of defining different
	// locators
	@FindAll({ @FindBy(how = How.REPEATER_COLUMN, using = "row in rows", column = "row") })
	private List<WebElement> friendNames;

	@FindBy(how = How.REPEATER_ELEMENT, using = "row in rows", column = "row", row = 1)
	private WebElement friendNameElement;

	public int getNumberOfFriendNames() {
		return friendNames.size();
	}

	// public String getFriendName(int index) {
	// WebElement friendName = friendNames.get(index);
	// return friendName.getText();
	// }
	public String getFriendName() {
		return friendNameElement.getText();
	}

}
