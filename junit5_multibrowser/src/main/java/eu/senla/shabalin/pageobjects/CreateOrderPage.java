package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class CreateOrderPage implements PageObject{
    private SelenideElement addressCheckbox = $(By.id("addressesAreEquals"));
    private SelenideElement proceedToCheckoutButtonInAddressPage = $(By.name("processAddress"));
    private SelenideElement termsOfServiceCheckbox = $(By.id("cgv"));
    private SelenideElement proceedToCheckoutButtonInShippingPage = $(By.name("processCarrier"));
    private SelenideElement payByBankWireButton = $(By.className("bankwire"));
    private SelenideElement iConfirmMyOrderButton = $("p#cart_navigation>button");
    private SelenideElement lineWithOrderCode = $(withText("Do not forget"));

    private CreateOrderPage doAddressCheckboxSelected() {
        if (!addressCheckbox.isSelected()) {
            addressCheckbox.click();
        }
        return this;
    }

    private CreateOrderPage doTermsOfServiceCheckboxSelected() {
        if (!termsOfServiceCheckbox.isSelected()) {
            termsOfServiceCheckbox.click();
        }
        return this;
    }

    private String getOrderCodeFromString(String lineWithOrderCode) {
        Pattern p = Pattern.compile("([A-Z]{8,10})");
        Matcher matcher = p.matcher(lineWithOrderCode);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }

    }

    public String createOrder() {
        doAddressCheckboxSelected().proceedToCheckoutButtonInAddressPage.click();
        doTermsOfServiceCheckboxSelected().proceedToCheckoutButtonInShippingPage.click();
        payByBankWireButton.click();
        iConfirmMyOrderButton.click();
        return getOrderCodeFromString(lineWithOrderCode.text());
    }
}
