package eu.senla.shabalin.pageobjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TopMenuBar implements PageObject{
    private SelenideElement signInButton = $(By.className("login"));
    private SelenideElement womenCategoryButton = $("li>a[title='Women']");
    private SelenideElement womenCategorySubmenu = $("li>a[title='Women']~ul");
    private SelenideElement dressesCategoryButton = $("ul.sf-menu>li>a[title='Dresses']");
    private SelenideElement dressesCategorySubmenu = $("ul.sf-menu>li>a[title='Dresses']~ul");
    private SelenideElement searchField = $(By.className("search_query"));
    private SelenideElement searchButton = $(By.name("submit_search"));
    private SelenideElement summerDressesButtonInSubmenu = $("li.sfHover a[title='Summer Dresses']");
    private SelenideElement cartButton = $("div.shopping_cart>a");
    private SelenideElement dressesButtonInLeftMenu = $(By.xpath("//ul[@class='tree dynamized']/li/a[contains(text(), 'Dresses')]"));
    private SelenideElement summerDressesButtonInLeftMenu = $("li.last").$(withText("Summer Dresses"));
    private SelenideElement myAccountButton = $(By.className("account"));

    public AuthenticationPage clickInSingInButton() {
        signInButton.click();
        return new AuthenticationPage();
    }

    public boolean isWomenCategorySubmenuDisplayed() {
        womenCategoryButton.hover();
        return womenCategorySubmenu.isDisplayed();
    }
    public boolean isDressesCategorySubmenuDisplayed() {
        dressesCategoryButton.hover();
        return dressesCategorySubmenu.isDisplayed();
    }

    public MainPage searchForProductsByRequest(String request) {
        searchField.sendKeys(request);
        searchButton.click();
        return new MainPage();
    }

    public MainPage chooseSummerDressesMenuCategoryAcrossDressesButton() {
        dressesCategoryButton.hover();
        summerDressesButtonInSubmenu.click();
        return new MainPage();
    }

    public CartPage clickToCartButton() {
        cartButton.click();
        return new CartPage();
    }

    public MainPage chooseSummerDressesMenuCategoryAcrossWomenButton() {
        womenCategoryButton.click();
        dressesCategoryButton.click();
        summerDressesButtonInLeftMenu.click();
        return new MainPage();
    }

    public MyAccountPage clickToMyAccountPageButton() {
        myAccountButton.click();
        return new MyAccountPage();
    }
}
