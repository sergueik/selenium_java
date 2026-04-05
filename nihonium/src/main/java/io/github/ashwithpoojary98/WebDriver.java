package io.github.ashwithpoojary98;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface WebDriver {

    void get(String url);

    String getCurrentUrl();

    String getTitle();

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

    String getPageSource();

    void close();

    void quit();

    Set<String> getWindowHandles();

    String getWindowHandle();

    TargetLocator switchTo();

    Navigation navigate();

    Options manage();

    interface TargetLocator {
        WebDriver frame(int index);
        WebDriver frame(String nameOrId);
        WebDriver frame(WebElement frameElement);
        WebDriver parentFrame();
        WebDriver window(String nameOrHandle);
        WebDriver defaultContent();
        WebElement activeElement();
    }

    interface Navigation {
        void back();
        void forward();
        void to(String url);
        void refresh();
    }

    interface Options {
        void addCookie(Cookie cookie);
        void deleteCookieNamed(String name);
        void deleteCookie(Cookie cookie);
        void deleteAllCookies();
        Set<Cookie> getCookies();
        Cookie getCookieNamed(String name);
        Timeouts timeouts();
        Window window();
    }

    interface Timeouts {
        Timeouts implicitlyWait(long time, TimeUnit unit);
        Timeouts setScriptTimeout(long time, TimeUnit unit);
        Timeouts pageLoadTimeout(long time, TimeUnit unit);
    }

    interface Window {
        Dimension getSize();
        void setSize(Dimension targetSize);
        Point getPosition();
        void setPosition(Point targetPosition);
        void maximize();
        void minimize();
        void fullscreen();
    }
}
