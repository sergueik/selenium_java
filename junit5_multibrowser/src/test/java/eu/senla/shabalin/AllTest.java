package eu.senla.shabalin;

import com.codeborne.selenide.ElementsCollection;
import eu.senla.shabalin.pageobjects.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class AllTest extends DataFixture{
    private String myAccountPageUrl = "index.php?controller=my-account";
    private TopMenuBar topMenuBar = new TopMenuBar();;

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void loginWithCorrectEmailAndCorrectPasswordTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        MyAccountPage myAccountPage = (MyAccountPage) topMenuBar.clickInSingInButton().authenticationWithCredetials(correctEmail, correctPassword);
        assertEquals(baseUrl+myAccountPageUrl, myAccountPage.getPageUrl());
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void loginWithCorrectEmailAndIncorrectPasswordTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        AuthenticationPage authenticationPage = (AuthenticationPage) topMenuBar.clickInSingInButton().authenticationWithCredetials(correctEmail, incorrectPassword);
        assertTrue(authenticationPage.isAuthenticationFailedAlertPresent());
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void isWomenCategorySubmenuDisplayedTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        assertTrue(topMenuBar.isWomenCategorySubmenuDisplayed());
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void isDressesCategorySubmenuDisplayedTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        assertTrue(topMenuBar.isDressesCategorySubmenuDisplayed());
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void searchProductPositiveTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        ElementsCollection collection = topMenuBar.searchForProductsByRequest("T-shirt").getAllProductsInPage();
        assertTrue(collection.size()>0);
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void searchProductNegativeTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        ElementsCollection collection = topMenuBar.searchForProductsByRequest("pants").getAllProductsInPage();
        assertFalse(collection.size()>0);
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void itemsWereAddedToTheCartTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        int allProductsInPage = topMenuBar.chooseSummerDressesMenuCategoryAcrossDressesButton()
                .addAllProductsInCart()
                .getAllProductsInPage()
                .size();
        int allProductsInCart = topMenuBar.clickToCartButton().getCartItemsCount();
        assertEquals(allProductsInPage, allProductsInCart);
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void productsTotalSumAndTotalPriceCheckTest(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        topMenuBar.chooseSummerDressesMenuCategoryAcrossWomenButton().addAllProductsInCart();
        topMenuBar.clickToCartButton();
        CartPage cartPage = new CartPage();
        cartPage.setFirstCartItemQuantity(5);
        int sum = cartPage.getAllProductsSumPrice();
        int total = cartPage.getAllProductsFinalPrice();
        assertEquals(sum, total);
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void deleteAllProductsFromCart(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        topMenuBar.chooseSummerDressesMenuCategoryAcrossWomenButton().addAllProductsInCart();
        CartPage cartPage = topMenuBar.clickToCartButton().deleteAllProducts();
        cartPage.deleteAllProducts();
        assertEquals(0, cartPage.getCartItemsCount());
    }

    @ParameterizedTest
    @MethodSource("browserArguments")
    public void createOrderAndCheckOrderCode(String browser, String version) {
        setCapabilitiesByArguments(browser, version);
        topMenuBar.chooseSummerDressesMenuCategoryAcrossWomenButton().addAllProductsInCart();
        CreateOrderPage createOrderPage = (CreateOrderPage) topMenuBar.clickToCartButton()
                .clickProceedToCheckoutButton()
                .authenticationWithCredetials(correctEmail, correctPassword);
        String orderCode = createOrderPage.createOrder();
        assertTrue(topMenuBar.clickToMyAccountPageButton().isOrderPresent(orderCode));
    }
}
