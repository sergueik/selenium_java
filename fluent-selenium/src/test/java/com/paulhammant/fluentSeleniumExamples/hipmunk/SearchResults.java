package com.paulhammant.fluentSeleniumExamples.hipmunk;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class SearchResults extends BasePage {
    public SearchResults(FirefoxDriver delegate) {
        super(delegate);
        within(secs(2)).url().shouldContain("hipmunk.com/flights/");

        // move past interstitial
        within(secs(20)).div(id("sub-graph-1"));
    }

    protected void waitForFlightListFor(final String flights) {
        div(id("leg-selector-1")).div(className("selected")).getText().within(secs(2)).shouldMatch(flights + ".*");
    }

    protected FluentWebElement firstShownLeg() {
        return div(className("select-leg"));
    }

}
