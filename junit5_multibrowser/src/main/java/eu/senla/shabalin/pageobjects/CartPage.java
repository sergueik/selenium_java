package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CartPage {
    private ElementsCollection allCartItems = $$(By.className("cart_item"));
    private SelenideElement firstItemQuantity = $(By.className("cart_quantity_input"));
    private List<String> allPrices = $$("td.cart_total>span").texts();
    private SelenideElement totalProductPrice = $("tr.cart_total_price>td#total_product");
    private ElementsCollection allDeleteItemButtons = $$(By.className("cart_quantity_delete"));
    private  SelenideElement proceedToCheckoutButton = $(By.className("standard-checkout"));

    public int getCartItemsCount() {
        return allCartItems.size();
    }

    public CartPage setFirstCartItemQuantity(int quantity) {
        firstItemQuantity.sendKeys(String.valueOf(quantity));
        return this;
    }

    public int getAllProductsSumPrice() {
        return allPrices.stream().mapToInt(a -> Integer.parseInt(a.substring(1).replace(".", ""))).sum();
    }

    public int getAllProductsFinalPrice() {
        return Integer.parseInt(totalProductPrice.text().substring(1).replace(".", ""));
    }

    public CartPage deleteAllProducts() {
        allDeleteItemButtons.forEach(a -> {
            a.click();
            a.should(Condition.hidden);
        });
        return this;
    }

    public AuthenticationPage clickProceedToCheckoutButton() {
        proceedToCheckoutButton.click();
        return new AuthenticationPage();
    }
}
