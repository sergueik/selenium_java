package com.paulhammant.fluentSeleniumExamples.hipmunk;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class UnitedAirlinesBooking extends BasePage {
    public UnitedAirlinesBooking(FirefoxDriver delegate) {
        super(delegate);
        // move past a few redirects to the page in question
        within(secs(20)).url().shouldMatch("united\\.com\\/web\\/.*\\/apps\\/booking");
        within(secs(15)).div(className("priceContinerB"));
    }

    public float getActualPrice() {
        return Float.parseFloat(td(id("ctl00_ContentInfo_priceRevenueSummary_Price_ctl02_tdDisplayValue")).getText().toString().replace("\n", "").replace("$", "").trim());
    }


}
