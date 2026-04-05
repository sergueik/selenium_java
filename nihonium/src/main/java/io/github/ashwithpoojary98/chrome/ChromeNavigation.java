package io.github.ashwithpoojary98.chrome;

import io.github.ashwithpoojary98.WebDriver;

class ChromeNavigation implements WebDriver.Navigation {

    private final ChromeDriver driver;

    ChromeNavigation(ChromeDriver driver) {
        this.driver = driver;
    }

    @Override
    public void back() {
        try {
            driver.getRuntimeDomain().evaluate("window.history.back()", false).join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate back", e);
        }
    }

    @Override
    public void forward() {
        try {
            driver.getRuntimeDomain().evaluate("window.history.forward()", false).join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate forward", e);
        }
    }

    @Override
    public void to(String url) {
        driver.get(url);
    }

    @Override
    public void refresh() {
        try {
            driver.getPageDomain().reload().join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to refresh page", e);
        }
    }
}
