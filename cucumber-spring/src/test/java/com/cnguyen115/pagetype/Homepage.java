package com.cnguyen115.pagetype;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import com.cnguyen115.api.BrowserDriver;

@Component
public class Homepage {

    private final By searchBox = By.id("lst-ib");
    private final By searchButton = By.xpath("//button[@class='lsb']");
    private final By resultStats = By.id("resultStats");

    public void isHomepageDisplayed() {
        BrowserDriver.driver.get("http://www.google.com/");
        BrowserDriver.waitForElementVisible(searchBox);
    }

    public void inputTextInSearchBox(String text) {
        BrowserDriver.driver.findElement(searchBox).sendKeys(text);
    }

    public void clickSearchButton() {
        BrowserDriver.waitForElementVisible(searchButton);
        BrowserDriver.driver.findElement(searchButton).click();
    }

    public void isResultPageDispalyed() {
        BrowserDriver.waitForElementVisible(resultStats, 5);
    }
}
