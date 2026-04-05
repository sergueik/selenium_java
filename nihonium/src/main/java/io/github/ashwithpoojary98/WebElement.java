package io.github.ashwithpoojary98;

import java.util.List;

public interface WebElement {

    void click();

    void submit();

    void sendKeys(CharSequence... keysToSend);

    void clear();

    String getTagName();

    String getAttribute(String name);

    boolean isSelected();

    boolean isEnabled();

    String getText();

    List<WebElement> findElements(By by);

    WebElement findElement(By by);

    /**
     * Convenience overload – equivalent to {@code findElement(By.cssSelector(cssSelector))}.
     * Allows passing a raw CSS selector string without wrapping it in {@link By}.
     */
    default WebElement findElement(String cssSelector) {
        return findElement(By.cssSelector(cssSelector));
    }

    /**
     * Convenience overload – equivalent to {@code findElements(By.cssSelector(cssSelector))}.
     * Allows passing a raw CSS selector string without wrapping it in {@link By}.
     */
    default List<WebElement> findElements(String cssSelector) {
        return findElements(By.cssSelector(cssSelector));
    }

    boolean isDisplayed();

    Point getLocation();

    Dimension getSize();

    Rectangle getRect();

    String getCssValue(String propertyName);
}
