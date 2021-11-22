package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage implements PageObject{
    private ElementsCollection allProducts = $$("ul.product_list>li.ajax_block_product");
    private By addToCartButtonSelector = byText("Add to cart");
    private SelenideElement continueShoppingButton = $(By.className("continue"));
    private SelenideElement noResultsWarning = $("p.alert-warning");

    public ElementsCollection getAllProductsInPage() {
        if (noResultsWarning.exists()) {
            if(noResultsWarning.text().contains("No results were found for your search")) {
                return allProducts;
            }
        } else {
            $("ul.product_list>li.ajax_block_product").shouldBe(Condition.visible);
        }
        return allProducts;
    }

    public MainPage addAllProductsInCart() {
        ElementsCollection collection = getAllProductsInPage();
        collection.forEach(a -> {
            a.hover().$(addToCartButtonSelector).shouldBe(Condition.visible).click();
            continueShoppingButton.click();
        });
        return this;
    }
}
