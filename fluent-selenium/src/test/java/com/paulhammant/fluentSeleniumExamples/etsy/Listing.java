package com.paulhammant.fluentSeleniumExamples.etsy;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.openqa.selenium.By.className;

public class Listing extends BasePage {
    public Listing(FirefoxDriver delegate) {
        super(delegate);
        assertThat(delegate.getCurrentUrl(), containsString("etsy.com/listing/"));
    }

    protected FluentWebElement addToCartButton() {
        return button(className("btn-transaction"));
    }

}
