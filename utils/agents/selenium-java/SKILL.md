---
name: selenium-java
description: Expert-level guidance for browser automation and enterprise web testing using Selenium WebDriver with Java, emphasizing robustness, scalability, and clean architecture.
---

# Selenium Browser Automation

You are an expert in Selenium WebDriver using Java, specializing in building scalable, maintainable, and
high-performance automated test frameworks for modern web applications.

This skill is optimized for **enterprise-grade automation**, CI/CD integration, and long-term maintainability.

---

## Core Expertise

- Selenium WebDriver internals and Java bindings
- Browser drivers: Chrome, Firefox, Edge, Safari
- Element location strategies (CSS, XPath, accessibility-first selectors)
- Explicit waits and fluent waits for dynamic web apps
- Page Object Model (POM) and Page Factory patterns
- Test orchestration with TestNG and JUnit 5
- Parallel execution using Selenium Grid and cloud providers
- Maven & Gradle-based automation frameworks
- CI/CD integration with Jenkins, GitHub Actions, GitLab CI

---

## Guiding Principles

- Tests are **code**, not scripts - design them like production software
- Favor **explicit waits** and domain - specific abstractions
- Enforce **single responsibility** at page and test levels
- Keep tests **deterministic and isolated**
- Optimize for **parallelism first**, not as an afterthought
- Eliminate flakiness through synchronization, not retries

---

## Recommended Project Structure

```text
src
 └── test
     ├── java
     │   ├── base
     │   │   ├── BaseTest.java
     │   │   └── BasePage.java
     │   ├── pages
     │   │   ├── LoginPage.java
     │   │   └── DashboardPage.java
     │   ├── tests
     │   │   ├── LoginTests.java
     │   │   └── DashboardTests.java
     │   └── utils
     │       ├── DriverFactory.java
     │       ├── WaitUtils.java
     │       └── ConfigReader.java
     └── resources
         ├── config.properties
         └── testng.xml
 ```

This structure scales cleanly from 10 tests to 10,000 tests without entropy.

---

## WebDriver Setup

### Driver Factory Pattern

```java 
package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver(options));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove();
    }
}
```

ThreadLocal enables true parallel execution, not simulated concurrency.

---

## Base Test Setup

```java
package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver(true);
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
```
---

## Page Object Model

### Base Page

```java

package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getText();
    }
}
```
---

### Page Object Implementation

```java
package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.className("error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

}
```
---

### Element Location Strategy

Priority order:
1. id
2. name
3. data-testid
4. CSS selectors
5. XPath (only when relationships matter)

```java
    By.cssSelector("[data-testid='submit-button']");
    By.xpath("//label[text()='Email']/following-sibling::input");
```
---

## Wait Strategy

### Explicit & Fluent Waits

```java
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal")));
```

```java
    Wait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(15))
            .pollingEvery(Duration.ofMillis(500))
            .ignoring(NoSuchElementException.class);
```

Hard sleeps are automation debt—never amortize them.

---

## Test Writing (TestNG)

```java
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test
    public void loginWithValidCredentialsNavigatesToDashboard() {
        driver.get("https://example.com/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("valid_user", "valid_pass");

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @Test
    public void loginWithInvalidPasswordShowsError() {
        driver.get("https://example.com/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("valid_user", "wrong_pass");

        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Invalid credentials"));
    }

}
```
---

## Handling Complex Web Elements

### Dropdowns

```java
    Select select = new Select(driver.findElement(By.id("country")));
    select.selectByVisibleText("Germany");
```

### Alerts

```java
    driver.switchTo().alert().accept();
```

### Frames

```java
    driver.switchTo().frame("frameName");
    driver.switchTo().defaultContent();
```

### Multiple Windows

```java
    String parent = driver.getWindowHandle();
    for(String window : driver.getWindowHandles()){
        if(!window.equals(parent)){
            driver.switchTo().window(window);
            break;
        }
    }
```
---

## Parallel Execution

### TestNG Configuration

```java
    <suite name="Automation Suite" parallel="tests" thread-count="5">
        <test name="Login Tests">
            <classes>
                <class name="tests.LoginTests"/>
            </classes>
        </test>
    </suite>
```

Designed for seamless scaling into Selenium Grid or cloud providers.

---

### Key Dependencies (Maven)

```java
    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.x.x</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
        </dependency>
    </dependencies>
```
---

## Reliability & Observability

- Screenshot capture on failure
- Structured logging per test thread
- RetryAnalyzer only for environmental flakiness
- CI-friendly reports (Allure / Extent Reports)

---

## Debugging Playbook

- Run non-headless locally for DOM inspection
- Dump page source on failures
- Capture browser logs when supported
- Treat flaky tests as design bugs, not nuisances
