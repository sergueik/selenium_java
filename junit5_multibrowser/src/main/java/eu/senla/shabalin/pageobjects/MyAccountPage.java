package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byTitle;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyAccountPage implements PageObject {
	private SelenideElement orderHistoryButton = $(byTitle("Orders"));
	private ElementsCollection allOrdersCodeElements = $$("a.color-myaccount");

	public boolean isOrderPresent(String orderCode) {
		orderHistoryButton.click();
		return allOrdersCodeElements.texts().contains(orderCode);
	}
}
