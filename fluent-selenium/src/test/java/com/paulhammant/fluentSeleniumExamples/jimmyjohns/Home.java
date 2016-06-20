package com.paulhammant.fluentSeleniumExamples.jimmyjohns;

import com.paulhammant.fluentSeleniumExamples.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.seleniumhq.selenium.fluent.FluentBy;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;

public class Home extends BasePage {

    public Home(FirefoxDriver delegate) {
        super(delegate);
        div(className("startAnOrder"));
    }

    protected FluentWebElement deliveryOption() {
        return h2(id("newDeliveryOrderBtn"));
    }

    protected FluentWebElement addressField() {
        return input(id("placesInput"));
    }

    protected FluentWebElement firstOfferedAddress() {
        return div(ngWait(className("pac-container"))).div();
    }

    protected FluentWebElement newDeliveryButton() {
        return link(id("submitNewDeliveryAddressBtn"));
    }

    public static By ngWait(final By by) {
        return new FluentBy() {
            @Override
            public void beforeFindElement(WebDriver driver) {
                driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
                ((JavascriptExecutor) driver).executeAsyncScript("var callback = arguments[arguments.length - 1];" +
                        "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
                super.beforeFindElement(driver);
            }

            @Override
            public List<WebElement> findElements(SearchContext context) {
                return by.findElements(context);
            }

            @Override
            public WebElement findElement(SearchContext context) {
                return by.findElement(context);
            }

            @Override
            public String toString() {
                return "ngWait(" + by.toString() + ")";
            }
        };
    }

}
