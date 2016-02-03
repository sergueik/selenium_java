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
	private final WebDriver webDriver;
	private final JavascriptExecutor jsExecutor;
	private final String rootElement;
	public boolean ignoreSynchronization;

    /**
     * Ctor.
     * @param webDriver Parent web driver.
     */
    public NgWebDriver(final WebDriver webDriver) {
        this(webDriver, "body");
    }

    /**
     * Ctor.
     * @param webDriver Parent Web Driver.
     * @param rootElement Root Element.
     */
    public NgWebDriver(
        final WebDriver webDriver,
        final String rootElement
    ) {
        if (!(webDriver instanceof JavascriptExecutor)) {
            throw new WebDriverException( "The WebDriver instance must implement JavaScriptExecutor interface.");
        }
        this.webDriver = webDriver;
        this.jsExecutor = (JavascriptExecutor) webDriver;
        this.rootElement = rootElement;
    }

    /**
     * Ctor.
     * @param webDriver Parent Web Driver.
     * @param ignoreSynchronization flag indicating non-Angular sites
     */
	public NgWebDriver(final WebDriver webDriver, final boolean ignoreSynchronization) {
		this(webDriver);
		this.ignoreSynchronization = ignoreSynchronization;
	}

    @Override
    public WebDriver getWrappedDriver() {
        return this.webDriver;
    }

    @Override
    public void close() {
        this.webDriver.close();
    }

    @Override
    public NgWebElement findElement(final By by) {
        this.waitForAngular();
        return new NgWebElement(this, this.webDriver.findElement(by));
    }

    @Override
    public List<WebElement> findElements(final By by) {
        return this.webDriver.findElements(by);
    }

    @Override
    public void get(final String property) {
        this.waitForAngular();
        this.webDriver.get(property);
    }

    @Override
    public String getCurrentUrl() {
        return this.webDriver.getCurrentUrl();
    }

    @Override
    public String getPageSource() {
		this.waitForAngular();
		return this.webDriver.getPageSource();
    }

    @Override
    public String getTitle() {
        this.waitForAngular();
        return this.webDriver.getTitle();
    }

    @Override
    public String getWindowHandle() {
        return this.webDriver.getWindowHandle();
    }

    @Override
    public Set<String> getWindowHandles() {
        return this.webDriver.getWindowHandles();
    }

    @Override
    public WebDriver.Options manage() {
        return this.webDriver.manage();
    }

    @Override
    public WebDriver.Navigation navigate() {
        return new NgNavigation(this.webDriver.navigate());
    }

    @Override
    public void quit() {
        this.webDriver.quit();

    }

    @Override
    public WebDriver.TargetLocator switchTo() {
        return this.webDriver.switchTo();
    }

    public void waitForAngular() {	
		if (!this.ignoreSynchronization) {
			this.jsExecutor.executeAsyncScript(new WaitForAngular().content(),this.rootElement);
		}
    }
}
