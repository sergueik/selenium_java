package com.paulhammant.fluentSeleniumExamples.jimmyjohns;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.TestableString;

import static com.paulhammant.fluentSeleniumExamples.jimmyjohns.Home.ngWait;
import static org.openqa.selenium.By.className;

public class CustomizeYourOrder extends BasePage {

    public CustomizeYourOrder(FirefoxDriver wd) {
        super(wd);
        div(ngWait(className("headingBar"))).getText().shouldContain("CUSTOMIZE YOUR ORDER");
    }

    public TestableString sandwichName() {
        return span(className("sandwichName")).getText();
    }

}
