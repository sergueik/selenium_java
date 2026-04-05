# Nihonium

**Nihonium** is an **open-source, Selenium-API-compatible browser automation engine** built directly on **Chrome
DevTools Protocol (CDP)**.

It preserves Selenium’s public APIs while replacing the internal HTTP/ChromeDriver stack with a **modern, CDP-native
execution engine** featuring **Playwright-style auto-wait**, **locator-based interactions**, and **direct browser
control** — enabling **zero-migration adoption** for existing Selenium test suites.

---
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ashwithpoojary98/nihonium)
[![GitHub stars](https://img.shields.io/github/stars/ashwithpoojary98/nihonium.svg?style=flat)](https://github.com/ashwithpoojary98/nihonium/stargazers)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg?style=flat )](https://github.com/ashwithpoojary98/nihonium/pulls)
[![GitHub forks](https://img.shields.io/github/forks/ashwithpoojary98/nihonium.svg?style=social&label=Fork)](https://github.com/ashwithpoojary98/nihonium/network)

# maven-plugin

[![Build Status](https://github.com/ashwithpoojary98/nihonium/actions/workflows/build.yml/badge.svg)](https://github.com/ashwithpoojary98/nihonium/actions/workflows/build.yml)

## Installation

### Maven

Add this dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>io.github.ashwithpoojary98</groupId>
    <artifactId>nihonium</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'io.github.ashwithpoojary98:nihonium:1.0.0'
```

### Requirements

- **Java 21+**
- **No ChromeDriver needed** — Nihonium communicates directly with Chrome via WebSocket
- **No pre-installed browser needed** — Nihonium auto-downloads Chrome for Testing on first run

## Why Nihonium?

Modern web applications are asynchronous, dynamic, and frequently re-rendered.  
Traditional Selenium architectures often struggle with flakiness due to stale elements, fixed waits, and driver
indirection.

**Nihonium solves this at the engine level.**

- No ChromeDriver
- No HTTP polling
- No stale element IDs
- No manual waits
- No test rewrites

Just **direct CDP + smart retries**.

---

## Features

- **Native CDP over WebSocket**  
  Direct Chrome DevTools Protocol communication without intermediary drivers.

- **Playwright-like Auto-Wait (Built-in)**  
  Automatically waits for elements to be:
    - attached
    - visible
    - stable (no animations)
    - enabled
    - not obscured

- **Selenium-Compatible APIs**  
  Same `WebDriver`, `WebElement`, `By`, and driver usage — **no test code changes required**.

- **Locator-Based Elements**  
  Elements are resolved fresh on every action, eliminating stale element failures.


- **Network Idle Detection (Optional)**  
  Useful for SPA navigation and async workflows.

- **Auto Browser Download**
  Automatically downloads Chrome for Testing on first run. Cached under `~/.cache/nihonium/` — no manual setup needed.

- **Chrome / Chromium Support**
  Auto-detect an existing installation or supply a custom binary path.

---

## Quick Start

Here's a simple example to get you started:

```java
import io.github.ashwithpoojary98.WebDriver;
import io.github.ashwithpoojary98.WebElement;
import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.chrome.ChromeDriver;

public class Example {
    public static void main(String[] args) {
        // Launch Chrome
        WebDriver driver = new ChromeDriver();

        // Navigate to a website
        driver.get("https://example.com");

        // Find and interact with elements (auto-waits built-in)
        WebElement heading = driver.findElement(By.tagName("h1"));
        System.out.println(heading.getText());

        // Close browser
        driver.quit();
    }
}
```

**That's it!** No ChromeDriver setup, no manual waits, no flaky tests.

---

## Usage Examples

### Basic Navigation

```java
WebDriver driver = new ChromeDriver();

// Navigate to URL
driver.get("https://example.com");

// Browser controls
driver.navigate().back();

driver.navigate().forward();
driver.navigate().refresh();

// Get page information
String url = driver.getCurrentUrl();
String title = driver.getTitle();
```

### Finding Elements

Nihonium supports all standard Selenium locator strategies:

```java
// CSS Selector
driver.findElement(By.cssSelector("#username"));
driver.findElement(By.cssSelector(".btn-primary"));

// XPath
driver.findElement(By.xpath("//button[@type='submit']"));

// ID
driver.findElement(By.id("username"));

// Name
driver.findElement(By.name("email"));

// Class Name
driver.findElement(By.className("btn-primary"));

// Tag Name
driver.findElement(By.tagName("h1"));

// Link Text
driver.findElement(By.linkText("Click Here"));

// Partial Link Text
driver.findElement(By.partialLinkText("Click"));
```

### Element Interactions

All interactions include automatic waiting:

```java
WebElement element = driver.findElement(By.id("username"));

// Type text (waits for element to be visible and editable)
element.sendKeys("myusername");

// Click (waits for element to be clickable and not obscured)
element.click();

// Clear input field
element.clear();

// Get element properties
String text = element.getText();
String value = element.getAttribute("value");
String cssColor = element.getCssValue("color");
boolean isVisible = element.isDisplayed();
boolean isEnabled = element.isEnabled();
```

### Chrome Configuration

```java
import io.github.ashwithpoojary98.chrome.ChromeOptions;
import io.github.ashwithpoojary98.browser.BrowserType;

ChromeOptions options = new ChromeOptions()
        .setHeadless(true)                        // Run in headless mode
        .setWindowSize(1920, 1080)                // Set window size
        .setBrowserType(BrowserType.CHROMIUM)     // Optional: use Chromium instead of Chrome
        .setBrowserVersion("131.0.6778.108")      // Optional: pin a specific version
        .setAutoDownload(true);                   // Default: true — download if not cached

// Or specify an existing local binary (skips auto-download):
// options.setBinaryPath("/path/to/chrome");

WebDriver driver = new ChromeDriver(options);
```

The browser is cached under `~/.cache/nihonium/{browser}/{platform}/{version}/` and reused on subsequent runs.

### Advanced Auto-Wait Configuration

Customize auto-wait behavior for your specific needs:

```java
import io.github.ashwithpoojary98.wait.WaitConfig;

WaitConfig waitConfig = WaitConfig.builder()
        .timeout(15000)                           // 15 seconds timeout
        .pollingInterval(100)                     // Poll every 100ms
        .waitForVisibility(true)                  // Wait for element visibility
        .waitForClickability(true)                // Wait for clickability
        .waitForAnimations(true)                  // Wait for animations to complete
        .waitForNetworkIdle(true)                 // Wait for network idle
        .networkIdleMaxConnections(0)             // Max 0 active connections
        .networkIdleDuration(500)                 // Idle for 500ms
        .build();

WebDriver driver = new ChromeDriver(options, waitConfig);
```

### Complete Example: Login Flow

```java
import io.github.ashwithpoojary98.WebDriver;
import io.github.ashwithpoojary98.WebElement;
import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.chrome.ChromeDriver;
import io.github.ashwithpoojary98.chrome.ChromeOptions;

public class LoginTest {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions().setHeadless(false);
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to login page
            driver.get("https://example.com/login");

            // Fill credentials (auto-waits for elements to be ready)
            driver.findElement(By.id("username")).sendKeys("myuser");
            driver.findElement(By.id("password")).sendKeys("mypass");

            // Click login button (waits for clickability automatically)
            driver.findElement(By.cssSelector("button[type='submit']")).click();

            // Verify successful login (waits for element to appear)
            WebElement dashboard = driver.findElement(By.className("dashboard"));
            System.out.println("Login successful: " + dashboard.isDisplayed());

        } finally {
            driver.quit();
        }
    }
}
```

### Working with Multiple Elements

```java
// Find all links on the page
List<WebElement> links = driver.findElements(By.tagName("a"));

for(WebElement link :links){
String href = link.getAttribute("href");
String text = link.getText();
System.out.println(text +" -> "+href);
}
```

---

## Auto-Wait in Action

**Before (Traditional):**

```java
// Manual waits everywhere
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
element.click();
```

**After (Nihonium):**

```java
// Just write the action - auto-wait handles everything 🚀
driver.findElement(By.id("submit")).click();
```

Nihonium automatically waits for:

- Element to exist in DOM
- Element to be visible (not `display: none`, `visibility: hidden`, or `opacity: 0`)
- Element to be stable (no running animations)
- Element to not be covered by other elements
- Element to be enabled and editable (for inputs)

**Result:** No more flaky tests, no more `StaleElementReferenceException`, no more manual waits!

---

## Troubleshooting

### Chrome not found

By default Nihonium downloads Chrome for Testing automatically and caches it under `~/.cache/nihonium/`.
If you want to use an existing local installation instead, specify the path:

```java
ChromeOptions options = new ChromeOptions()
        .setAutoDownload(false)
        .setBinaryPath("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
```

### Port already in use

If you see "Address already in use", Chrome is already running in debug mode. Close all Chrome instances and try again.

### Element not found

Nihonium retries element location automatically. If it still fails, verify:

- The selector is correct
- The element exists on the page
- The page has fully loaded

---
## Known Limitations

- **Frame switching is not yet supported.** `driver.switchTo().frame(...)` and `parentFrame()` throw `UnsupportedOperationException`. Interacting with elements inside `<iframe>` elements requires a separate CDP child session, which is planned for a future release.
- **Named window targets** — `driver.switchTo().window("name")` requires a CDP target ID (as returned by `driver.getWindowHandles()`). Named windows opened via `window.open(..., 'name')` are not yet resolved by name.
- **Firefox and Safari** are not supported. Nihonium is CDP-only and targets Chromium-based browsers.

---

## Supported Browsers

- **Google Chrome** (fully supported)
- **Chromium** (fully supported)
- **Microsoft Edge** (Chromium-based) - Coming soon
- **Firefox**, **Safari** - Planned for future releases

---

## Performance

Nihonium is designed for speed and reliability:

- **Direct WebSocket connection** - No HTTP overhead
- **Event-driven architecture** - React to browser events in real-time
- **Smart retries** - Fresh element resolution eliminates stale element errors

---

## Links

- **GitHub Repository**: https://github.com/ashwithpoojary98/nihonium
- **Issue Tracker**: https://github.com/ashwithpoojary98/nihonium/issues
- **Maven Central**: https://repo.maven.apache.org/maven2/io/github/ashwithpoojary98/nihonium/
- **Documentation**: https://github.com/ashwithpoojary98/nihonium/wiki

---

## Contributing

We welcome contributions from the community! Here’s how you can help:

### How to Contribute

1. **Fork the Repository:**
    - Click the "Fork" button on the top right of the repository page to create a copy of the project under your GitHub
      account.

2. **Clone Your Fork:**
    - Clone your forked repository to your local machine:
      ```bash
      git clone https://github.com/ashwithpoojary98/nihonium.git
      ```

3. **Create a Branch:**
    - Create a new branch for your feature or bug fix:
      ```bash
      git checkout -b your-branch-name
      ```

4. **Make Changes:**
    - Make your changes in your branch. Be sure to follow the coding style and guidelines of the project.

5. **Commit Your Changes:**
    - Stage your changes:
      ```bash
      git add .
      ```
    - Commit with a clear and descriptive message:
      ```bash
      git commit -m "Add a feature or fix a bug"
      ```

6. **Push to Your Fork:**
    - Push your changes back to your fork:
      ```bash
      git push origin your-branch-name
      ```

7. **Create a Pull Request:**
    - Go to the original repository where you want to contribute. You should see a prompt to create a pull request for
      your branch.
    - Click "Compare & pull request."
    - Provide a clear title and description for your pull request, explaining the changes you made and why they are
      necessary.

### Guidelines

- **Code Style:** Follow the coding conventions used in the project. If you’re unsure, check existing code for guidance.
- **Testing:** If applicable, add tests for your new features or bug fixes. Ensure all tests pass before submitting your
  pull request.
- **Documentation:** Update documentation if your changes introduce new features or alter existing ones.

---

## Support This Project

If you find Nihonium useful, please consider:

- **Starring** the repository on GitHub
- **Reporting issues** you encounter
- **Suggesting features** you'd like to see
- **Contributing code** improvements
- **Spreading the word** to other developers

Your support helps make Nihonium better for everyone!

---

## License

Nihonium is released
under [![License: Apache](https://img.shields.io/badge/License-Apache-yellow.svg)](https://opensource.org/licenses/Apache-2.0).

```
Copyright (c) 2025 Nihonium Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---

**Made with ❤️ by the Nihonium community**

