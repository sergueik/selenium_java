package com.paulhammant.fluentSeleniumExamples.hipmunk;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentMatcher;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static org.openqa.selenium.By.className;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class BookingOverlay extends BasePage {
    public BookingOverlay(FirefoxDriver delegate) {
        super(delegate);
        within(secs(8)).div(className("booking-info-container"));

    }

    protected FluentWebElement unitedRow() {
        return divs(className("booking-link-row")).first(new FluentMatcher() {
            public boolean matches(WebElement webElement) {
                return webElement.getText().contains("United");
            }

            @Override
            public String toString() {
                return "Matches United Airlines";
            }
        });
    }

}
