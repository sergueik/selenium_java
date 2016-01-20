package com.jprotractor;

import com.jprotractor.scripts.WaitForAngular;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Angular WebDriver Implementation.
 */
public final class NgWebDriver implements WebDriver, WrapsDriver {
    private final WebDriver driver;
    private final JavascriptExecutor jsExecutor;
    private final String root;

    /**
     * Ctor.
     * @param drv Parent web driver.
     */
    public NgWebDriver(final WebDriver drv) {
        this(drv, "body");
    }

    /**
     * Ctor.
     * @param drv Parend Web Driver.
     * @param rootel Root Element.
     */
    public NgWebDriver(
        final WebDriver drv,
        final String rootel
    ) {
        if (!(drv instanceof JavascriptExecutor)) {
            throw new WebDriverException(
                "The WebDriver instance must implement the " +
                    "JavaScriptExecutor interface."
            );
        }

        this.driver = drv;
        this.jsExecutor = (JavascriptExecutor) drv;
        this.root = rootel;
    }

    @Override
    public WebDriver getWrappedDriver() {
        return this.driver;
    }

    @Override
    public void close() {
        this.driver.close();
    }

    @Override
    public NgWebElement findElement(final By by) {
        this.waitForAngular();
        return new NgWebElement(this, this.driver.findElement(by));
    }

    @Override
    public List<WebElement> findElements(final By by) {
        return this.driver.findElements(by);
    }

    @Override
    public void get(final String arg0) {
        this.waitForAngular();
        this.driver.get(arg0);
    }

    @Override
    public String getCurrentUrl() {
        this.waitForAngular();
        return this.driver.getCurrentUrl();
    }

    @Override
    public String getPageSource() {
        this.waitForAngular();
        return this.driver.getPageSource();
    }

    @Override
    public String getTitle() {
        this.waitForAngular();
        return this.driver.getTitle();
    }

    @Override
    public String getWindowHandle() {
        this.waitForAngular();
        return this.driver.getWindowHandle();
    }

    @Override
    public Set<String> getWindowHandles() {
        this.waitForAngular();
        return this.driver.getWindowHandles();
    }

    @Override
    public WebDriver.Options manage() {
        return this.driver.manage();
    }

    @Override
    public WebDriver.Navigation navigate() {
        return new NgNavigation(this.driver.navigate());
    }

    @Override
    public void quit() {
        this.driver.quit();

    }

    @Override
    public WebDriver.TargetLocator switchTo() {
        return this.driver.switchTo();
    }

    public void waitForAngular() {	
    	// Object result = 
        this.jsExecutor.executeAsyncScript(
            new WaitForAngular().content(),
            this.root
        );
	// System.err.println("waitForAngular -> " +result.toString());
    }
}
