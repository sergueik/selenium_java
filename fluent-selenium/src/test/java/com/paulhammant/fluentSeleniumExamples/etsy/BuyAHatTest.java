package com.paulhammant.fluentSeleniumExamples.etsy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BuyAHatTest {

    private FirefoxDriver wd;

    @Before
    public void makeWebDriverAndGotoSite() {
        wd = new FirefoxDriver();
        wd.get("http://etsy.com");
    }

    @After
    public void killWebDriver() {
        wd.quit();
    }

    @Test
    public void adding_a_hat_to_the_cart() {

        new Home(wd) {{
            searchFor().sendKeys("Hat");
            searchButton().click();
        }};

        new SearchResults(wd) {{
            numberMatchingSearchHeader().shouldMatch(".*\\d items.*");
            firstNonSponsoredListing().link().click();
        }};

        new Listing(wd) {{
            addToCartButton().click();
        }};

        new Cart(wd) {{
            numberOfItemsInCartHeader().shouldContain("1 Item in Your Cart");
        }};
    }
}
