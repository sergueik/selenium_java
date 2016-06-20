package com.paulhammant.fluentSeleniumExamples.hipmunk;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static org.openqa.selenium.By.id;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class Home extends BasePage {
    public Home(FirefoxDriver delegate) {
        super(delegate);
        url().shouldMatch(".*hipmunk.com/");
    }

    protected void waitForExpandoToComplete() {
        div(id("flight-search")).getAttribute("class").within(secs(3)).shouldContain("expanded");
    }

    protected FluentWebElement searchButton() {
        return form(id("flight")).button();
    }

    protected FluentWebElement toAirportField() {
        return input(id("fac2flight"));
    }

    protected FluentWebElement fromAirportField() {
        return input(id("fac1flight"));
    }

}
