package io.github.ashwithpoojary98.chrome.nihonium;

import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.Dimension;
import io.github.ashwithpoojary98.WebDriver;
import io.github.ashwithpoojary98.WebElement;
import io.github.ashwithpoojary98.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebClientTest {
    WebDriver webDriver;

    @BeforeEach
    void setUp() {
        webDriver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    void testWebClientTest() {
        webDriver.manage().window().maximize();
        webDriver.get("https://asynccodinghub.in/");
        String title = webDriver.getTitle();
        Assertions.assertTrue(title != null && !title.isEmpty(), "Title should not be null or empty");
    }

    @Test
    void testNavigation() {
        webDriver.get("https://asynccodinghub.in/");
        String initialTitle = webDriver.getTitle();

        webDriver.navigate().to("https://www.google.com");
        String googleTitle = webDriver.getTitle();
        Assertions.assertNotEquals(initialTitle, googleTitle, "Titles should be different after navigation");

        webDriver.navigate().back();
        Assertions.assertEquals(initialTitle, webDriver.getTitle(), "Should navigate back to initial page");

        webDriver.navigate().forward();
        Assertions.assertEquals(googleTitle, webDriver.getTitle(), "Should navigate forward to Google");
    }

    @Test
    void testWindowManagement() {
        webDriver.get("https://asynccodinghub.in/");

        Dimension initialSize = webDriver.manage().window().getSize();
        webDriver.manage().window().maximize();
        Dimension maximizedSize = webDriver.manage().window().getSize();

        Assertions.assertTrue(maximizedSize.getWidth() >= initialSize.getWidth(), "Width should be greater or equal after maximizing");
        Assertions.assertTrue(maximizedSize.getHeight() >= initialSize.getHeight(), "Height should be greater or equal after maximizing");
    }

    @Test
    void testFindElement() {
        webDriver.get("https://asynccodinghub.in/");

        WebElement heading = webDriver.findElement(By.tagName("h1"));
        Assertions.assertNotNull(heading, "Heading element should not be null");
        Assertions.assertFalse(heading.getText().isEmpty(), "Heading text should not be empty");
    }

    @Test
    void testSendKeys() {
        webDriver.get("https://www.google.com");

        WebElement searchBox = webDriver.findElement(By.name("q"));
        searchBox.sendKeys("Nihonium browser automation");

        String value = searchBox.getAttribute("value");
        Assertions.assertEquals("Nihonium browser automation", value, "Search box value should match the input");
    }

    @Test
    void testClickElement() {
        webDriver.get("https://asynccodinghub.in/");

        WebElement aboutLink = webDriver.findElement(By.xpath("//a[contains(normalize-space(),'Start Learning')]"));
        aboutLink.click();

        String currentUrl = webDriver.getCurrentUrl();
        Assertions.assertEquals("https://asynccodinghub.in/roadmap.html", currentUrl, "URL should match after clicking the link");
    }

    // ── String locator overloads ──────────────────────────────────────────────

    @Test
    void testFindElementByRawCssString() {
        webDriver.get("https://www.google.com");
        // Pass a CSS selector string directly — no By wrapper needed
        WebElement searchBox = webDriver.findElement("input[name='q']");
        assertNotNull(searchBox, "Search box should be found via raw CSS string");
        searchBox.sendKeys("Nihonium");
        assertEquals("Nihonium", searchBox.getAttribute("value"),
                "Typed value should match");
    }

    @Test
    void testFindElementsByRawCssString() {
        webDriver.get("https://asynccodinghub.in/");
        List<WebElement> links = webDriver.findElements("a");
        assertTrue(links.size() > 1,
                "Raw CSS string 'a' should return multiple anchor elements");
    }

    @Test
    void testChildFindElementByRawCssString() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        WebElement form = webDriver.findElement(By.id("login"));
        // Child find using raw CSS string
        WebElement usernameInput = form.findElement("#username");
        assertNotNull(usernameInput, "Username input found via child string locator");
        usernameInput.sendKeys("tomsmith");
        assertEquals("tomsmith", usernameInput.getAttribute("value"));
    }

    // ── findElements returns ALL matches ─────────────────────────────────────

    @Test
    void testFindElementsReturnsManyElements() {
        webDriver.get("https://asynccodinghub.in/");
        List<WebElement> links = webDriver.findElements(By.tagName("a"));
        assertTrue(links.size() > 1,
                "findElements should return more than one <a> element, got: " + links.size());
    }

    @Test
    void testFindElementsEachElementIsInteractable() {
        webDriver.get("https://asynccodinghub.in/");
        List<WebElement> headings = webDriver.findElements(By.tagName("h2"));
        assertFalse(headings.isEmpty(), "Page should contain at least one <h2>");
        // Each element should be individually resolvable
        for (WebElement h : headings) {
            assertNotNull(h.getText(), "Each heading element should return text");
        }
    }

    @Test
    void testFindElementsByXPathReturnsMultiple() {
        webDriver.get("https://asynccodinghub.in/");
        List<WebElement> links = webDriver.findElements(By.xpath("//a"));
        assertTrue(links.size() > 1,
                "XPath findElements should return multiple elements");
    }

    // ── By.chained ────────────────────────────────────────────────────────────

    @Test
    void testByChainedAllCssLocator() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        // CSS chain: #login  →  input#username
        // Combined selector: "#login input#username"
        WebElement usernameInput = webDriver.findElement(
                By.chained(By.id("login"), By.cssSelector("input#username")));
        assertNotNull(usernameInput, "Chained CSS locator should find username input");
        usernameInput.sendKeys("tomsmith");
        assertEquals("tomsmith", usernameInput.getAttribute("value"));
    }

    @Test
    void testByChainedMixedCssAndXPath() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        // Mixed chain: CSS parent + XPath child → step-by-step resolution
        WebElement passwordField = webDriver.findElement(
                By.chained(By.id("login"), By.xpath(".//input[@type='password']")));
        assertNotNull(passwordField, "Mixed chained locator should find password field");
        assertTrue(passwordField.isEnabled(), "Password field should be enabled");
    }

    @Test
    void testByChainedThreeLevels() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        // Three-level CSS chain
        WebElement input = webDriver.findElement(
                By.chained(By.id("login"), By.className("row"), By.cssSelector("input[name='username']")));
        assertNotNull(input, "Three-level chained locator should resolve correctly");
    }

    // ── Child element scoping ─────────────────────────────────────────────────

    @Test
    void testChildFindElementScopedToParent() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        WebElement form = webDriver.findElement(By.id("login"));

        // findElement on the parent element should scope the search inside it
        WebElement username = form.findElement(By.id("username"));
        WebElement password = form.findElement(By.id("password"));

        assertNotNull(username, "Username field should be found within form");
        assertNotNull(password, "Password field should be found within form");

        username.sendKeys("tomsmith");
        password.sendKeys("SuperSecretPassword!");

        assertEquals("tomsmith",             username.getAttribute("value"));
        assertEquals("SuperSecretPassword!", password.getAttribute("value"));
    }

    @Test
    void testChildFindElementsByTagNameScopedToParent() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        WebElement form = webDriver.findElement(By.id("login"));

        List<WebElement> inputs = form.findElements(By.tagName("input"));
        // Login form has exactly two <input> elements: username and password
        assertEquals(2, inputs.size(),
                "findElements on form element should return only inputs within the form");
    }

    @Test
    void testChildFindElementsByXPathScopedToParent() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        WebElement form = webDriver.findElement(By.id("login"));

        List<WebElement> inputs = form.findElements(By.xpath(".//input"));
        assertEquals(2, inputs.size(),
                "XPath findElements scoped to form should return 2 inputs");
    }

    @Test
    void testChildFindElementsByRawCssString() {
        webDriver.get("https://the-internet.herokuapp.com/login");
        WebElement form = webDriver.findElement(By.id("login"));
        List<WebElement> inputs = form.findElements("input");
        assertFalse(inputs.isEmpty(), "String findElements on parent should return child inputs");
    }

    // ── By.index ─────────────────────────────────────────────────────────────

    @Test
    void testByIndexResolvesNthElement() {
        webDriver.get("https://asynccodinghub.in/");
        // Get the first and second anchor elements separately
        WebElement firstLink  = webDriver.findElement(By.index(By.tagName("a"), 0));
        WebElement secondLink = webDriver.findElement(By.index(By.tagName("a"), 1));

        assertNotNull(firstLink,  "First link (index 0) should be found");
        assertNotNull(secondLink, "Second link (index 1) should be found");
        // They should be distinct elements (different href or text)
        assertNotEquals(firstLink.getAttribute("href"), secondLink.getAttribute("href"),
                "Indexed elements should resolve to distinct DOM nodes");
    }

    @Test
    void testByIndexWithXPathParent() {
        webDriver.get("https://asynccodinghub.in/");
        // XPath parent + index: (//a)[1] (XPath-positional)
        WebElement firstAnchor = webDriver.findElement(By.index(By.xpath("//a"), 0));
        assertNotNull(firstAnchor, "By.index with XPath parent should resolve to first anchor");
    }

    // ── Element independence (findElements returns re-resolvable elements) ────

    @Test
    void testEachFindElementsResultIsIndependentlyResolvable() {
        webDriver.get("https://asynccodinghub.in/");
        List<WebElement> headings = webDriver.findElements(By.tagName("h2"));
        assertTrue(headings.size() >= 2, "Need at least 2 headings for this test");

        // Navigate away and back to verify elements still resolve (no caching)
        webDriver.navigate().to("https://asynccodinghub.in/roadmap.html");
        webDriver.navigate().back();

        WebElement first  = webDriver.findElements(By.tagName("h2")).get(0);
        WebElement second = webDriver.findElements(By.tagName("h2")).get(1);
        assertNotEquals(first.getText(), second.getText(),
                "Each resolved element should be distinct");
    }
}
