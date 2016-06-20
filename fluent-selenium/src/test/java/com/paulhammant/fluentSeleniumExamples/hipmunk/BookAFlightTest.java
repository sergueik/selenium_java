package com.paulhammant.fluentSeleniumExamples.hipmunk;

import com.paulhammant.fluentSeleniumExamples.WholeSuiteListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Monitor;

import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class BookAFlightTest {

    private FirefoxDriver wd;
    private Monitor.Timer bizOperationTiming;
    private int claimedUnitedPrice;
    private String hipmunkWindowHandle;

    @Before
    public void makeWebDriverAndGotoSite() {
        wd = new FirefoxDriver();
        wd.get("http://hipmunk.com");
    }

    @After
    public void killWebDriver() {
        wd.quit();
    }

    @Test
    public void a_booking_through_to_united_affiliate() {

        new Home(wd) {{
            fromAirportField().sendKeys("DAL");
            waitForExpandoToComplete();
            toAirportField().sendKeys("IAH");
            timeBizOperation("DAL->IAH Initial Search Results");
            searchButton().click();
        }};

        new SearchResults(wd) {{
            waitForFlightListFor("DAL\nIAH");
            bizOperationTiming.end(true);
            timeBizOperation("DAL->IAH Return Leg Search Results");
            firstShownLeg().click();
            waitForFlightListFor("IAH\nDAL");
            bizOperationTiming.end(true);
            firstShownLeg().click();

            new BookingOverlay(wd) {{
                hipmunkWindowHandle = delegate.getWindowHandle();
                FluentWebElement unitedRow = unitedRow();
                claimedUnitedPrice = Integer.parseInt(unitedRow.div().getText().toString().replace("$", ""));
                timeBizOperation("DAL->IAH United.com Transfer");
                unitedRow.link().click(); // purchase
                changeWebDriverWindow(delegate);
            }};
        }};

        new UnitedAirlinesBooking(wd) {{
            float actualPrice = getActualPrice(); // includes cents
            assertThat("price", Math.round(actualPrice), equalTo(claimedUnitedPrice));
            bizOperationTiming.end(true);
        }};

    }

    private void changeWebDriverWindow(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        for (String popupHandle : handles) {
            if (!popupHandle.contains(hipmunkWindowHandle)) {
                driver.switchTo().window(popupHandle);
            }
        }
    }

    private void timeBizOperation(String description) {
        bizOperationTiming = WholeSuiteListener.codahaleMetricsMonitor.start(description + " (End User Experience)");
    }

}
