package com.jprotractor;

import com.jprotractor.scripts.WaitForAngular;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

public class NgWebDriver implements WebDriver, WrapsDriver {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;
    private String rootElement;
    @SuppressWarnings("unused")
    private NgModule[] mockModules;
    public boolean ignoresync;
    // little max iteration count for protector
    public int maxIterations;
    private int iterationCount;

    public NgWebDriver(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.rootElement = "body";
        maxIterations = 30;
        iterationCount = 0;
    }

    public NgWebDriver(final WebDriver driver, final boolean ignoreSync) {
        this(driver);
        this.ignoresync = ignoreSync;
    }

    public NgWebDriver(final WebDriver driver, final NgModule[] mockModules) {
        this(driver, "body", mockModules);
    }

    public NgWebDriver(
        final WebDriver driver,
        final String rootElement,
        final NgModule[] mockModules
    ) {
        if (!(driver instanceof JavascriptExecutor)) {
            throw new WebDriverException(
                "The WebDriver instance must implement the " +
                    "JavaScriptExecutor interface."
            );
        }

        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        this.rootElement = rootElement;
        this.mockModules = mockModules;
    }

    public WebDriver getWrappedDriver() {
        return this.driver;
    }

    public void close() {
        this.driver.close();

    }

    public NgWebElement findElement(By by) {
        this.waitForAngular();
        return new NgWebElement(this, this.driver.findElement(by));
    }

    public List<NgWebElement> findNGElements(By by) {
        this.waitForAngular();
        List<WebElement> temp = this.driver.findElements(by);
        // not sure idf this is correct
		// NOTE: diamond operator is not supported in -source 1.6
        final List<NgWebElement> elements = new ArrayList<>();
        for (final WebElement element : temp) {
            elements.add(new NgWebElement(this, element));
        }
        return elements;
    }

    public List<WebElement> findElements(By by) {
        return this.driver.findElements(by);
    }

    public void get(String arg0) {
        this.waitForAngular();
        this.driver.navigate().to(arg0);

    }

    public String getCurrentUrl() {
        this.waitForAngular();
        return this.driver.getCurrentUrl();
    }

    public String getPageSource() {
        this.waitForAngular();
        return this.driver.getPageSource();
    }

    public String getTitle() {
        this.waitForAngular();
        return driver.getTitle();
    }

    public String getWindowHandle() {
        this.waitForAngular();
        return driver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        this.waitForAngular();
        return driver.getWindowHandles();
    }

    public Options manage() {
        //this.waitForAngular();
        return this.driver.manage();
    }

    public Navigation navigate() {
        return new NgNavigation(this, this.driver.navigate());
    }

    public void quit() {
        this.driver.quit();

    }

    public TargetLocator switchTo() {
        return this.driver.switchTo();
    }

    public void waitForAngular() {
        if (!this.ignoresync) {
            iterationCount++;
            this.jsExecutor.executeAsyncScript(
                new WaitForAngular().content(),
                this.rootElement
            );
        }
    }
}
