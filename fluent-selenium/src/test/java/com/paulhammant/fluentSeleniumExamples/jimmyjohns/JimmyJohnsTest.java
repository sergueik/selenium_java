package com.paulhammant.fluentSeleniumExamples.jimmyjohns;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class JimmyJohnsTest {

    private FirefoxDriver wd;

    @Before
    public void makeWebDriverAndGotoSite() {
        wd = new FirefoxDriver();
        wd.get("https://online.jimmyjohns.com");
    }

    @After
    public void killWebDriver() {
        wd.quit();
    }

    @Test
    public void order_a_large_sarnie() {

        new Home(wd) {{
            deliveryOption().click();
            addressField().sendKeys("1600 Pennsylvania Ave NW, Washington");
            addressField().click();
            firstOfferedAddress().click();
            newDeliveryButton().click();
        }};

        new VerifyAddress(wd) {{
            confirmButton().click();
        }};

        final String sarnie = "THE J.J. GARGANTUAN®";

        new Menu(wd) {{
            selectItem(sarnie);
        }};

        new CustomizeYourOrder(wd) {{
            sandwichName().shouldBe(sarnie);
        }};

        // Stop here as we don't want The Secret Service calling.

    }

}
