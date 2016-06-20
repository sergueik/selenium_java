package com.paulhammant.fluentSeleniumExamples.jimmyjohns;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static com.paulhammant.fluentSeleniumExamples.jimmyjohns.Home.ngWait;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;

public class VerifyAddress extends BasePage {

    public VerifyAddress(FirefoxDriver wd) {
        super(wd);
        div(ngWait(className("headingBar"))).getText().shouldContain("VERIFY ADDRESS");
    }

    public FluentWebElement confirmButton() {
        return link(id("confirmDeliveryAddressBtn"));
    }

}
