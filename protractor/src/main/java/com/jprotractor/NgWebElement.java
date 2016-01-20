package com.jprotractor;

import com.jprotractor.scripts.Evaluate;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

/**
 * Angular Web Element.
 */
public final class NgWebElement implements WebElement, WrapsElement {

    /**
     * Angular Web Driver.
     */
    private final transient NgWebDriver driver;

    /**
     * Element.
     */
    private final transient WebElement element;

    /**
     * Ctor.
     * @param drv Driver.
     * @param elm Element.
     */
    public NgWebElement(final NgWebDriver drv, final WebElement elm) {
        this.driver = drv;
        this.element = elm;
    }

    @Override
    public WebElement getWrappedElement() {
        return this.element;
    }

    @Override
    public void clear() {
        this.driver.waitForAngular();
        this.element.clear();
    }

    @Override
    public void click() {
        this.driver.waitForAngular();
        this.element.click();
    }

    public Object evaluate(final String expression) {
        this.driver.waitForAngular();
        final JavascriptExecutor executor = (JavascriptExecutor) this.driver
            .getWrappedDriver();
        return executor.executeScript(
            new Evaluate().content(),
            this.element,
            expression
        );
    }

    @Override
    public NgWebElement findElement(By by) {
        if (by instanceof JavaScriptBy) {
            ((JavaScriptBy) by).root = this.element;
        }
        this.driver.waitForAngular();
        return new NgWebElement(this.driver, this.element.findElement(by));
    }

    public List<NgWebElement> findNgElements(By by) {
        final List<WebElement> temp = findElements(by);
        final List<NgWebElement> elements = new ArrayList<>();
        for (final WebElement element : temp) {
            elements.add(new NgWebElement(this.driver, element));
        }
        return elements;
    }

    public List<WebElement> findElements(By by) {
        if (by instanceof JavaScriptBy) {
            ((JavaScriptBy) by).root = this.element;
        }
        final List<WebElement> returnElements = this.element.findElements(by);
        this.driver.waitForAngular();
        return returnElements;
    }

    public String getAttribute(String arg0) {
        this.driver.waitForAngular();
        return this.element.getAttribute(arg0);
    }

    public String getCssValue(String arg0) {
        this.driver.waitForAngular();
        return this.element.getCssValue(arg0);
    }

    public Point getLocation() {
        this.driver.waitForAngular();
        return this.element.getLocation();
    }

    public Dimension getSize() {
        this.driver.waitForAngular();
        return this.element.getSize();
    }

    public String getTagName() {
        this.driver.waitForAngular();
        return this.element.getTagName();
    }

    public String getText() {
        this.driver.waitForAngular();
        return this.element.getText();
    }

    public boolean isDisplayed() {
        this.driver.waitForAngular();
        return this.element.isDisplayed();
    }

    public boolean isEnabled() {
        this.driver.waitForAngular();
        return this.element.isEnabled();
    }

    public boolean isSelected() {
        this.driver.waitForAngular();
        return this.element.isSelected();
    }

    public void sendKeys(CharSequence... arg0) {
        this.driver.waitForAngular();
        this.element.sendKeys(arg0);

    }

    public void submit() {
        this.driver.waitForAngular();
        this.element.submit();
    }
}
