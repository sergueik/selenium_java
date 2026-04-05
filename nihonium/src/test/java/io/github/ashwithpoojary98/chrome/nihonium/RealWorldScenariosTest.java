package io.github.ashwithpoojary98.chrome.nihonium;

import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.WebDriver;
import io.github.ashwithpoojary98.WebElement;
import io.github.ashwithpoojary98.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Real-World End-to-End Scenarios")
class RealWorldScenariosTest {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("Homepage loads → title and heading are visible")
    void testHomepageTitleAndHeading() {
        driver.get("https://asynccodinghub.in/");

        assertFalse(driver.getTitle().isEmpty(),
                "Page title should not be empty");

        WebElement heading = driver.findElement(By.tagName("h1"));
        assertFalse(heading.getText().isEmpty(),
                "H1 heading should be visible on the homepage");
    }

    @Test
    @DisplayName("findElements returns multiple navigation links")
    void testFindElementsReturnsMultipleLinks() {
        driver.get("https://asynccodinghub.in/");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        assertTrue(links.size() > 5,
                "Homepage should contain multiple anchor links, found: " + links.size());
    }

    @Test
    @DisplayName("String locator — find element using raw CSS selector")
    void testFindElementByStringLocator() {
        driver.get("https://asynccodinghub.in/");

        WebElement heading = driver.findElement("h1");
        assertFalse(heading.getText().isEmpty(),
                "H1 found via raw CSS string should have text");
    }

    @Test
    @DisplayName("By.chained — find child element scoped within parent")
    void testByChainedLocator() {
        driver.get("https://asynccodinghub.in/");

        // Find an anchor nested inside a nav or header section
        WebElement link = driver.findElement(
                By.chained(By.tagName("nav"), By.tagName("a")));
        assertNotNull(link, "Should find an anchor inside nav via By.chained");
        assertFalse(link.getText().isEmpty(),
                "Chained locator should resolve to a link with text");
    }

    @Test
    @DisplayName("By.index — access nth element from findElements result")
    void testByIndexLocator() {
        driver.get("https://asynccodinghub.in/");

        WebElement firstLink  = driver.findElement(By.index(By.tagName("a"), 0));
        WebElement secondLink = driver.findElement(By.index(By.tagName("a"), 1));

        assertNotNull(firstLink,  "First link via By.index(0) should be found");
        assertNotNull(secondLink, "Second link via By.index(1) should be found");
        assertNotEquals(firstLink.getAttribute("href"), secondLink.getAttribute("href"),
                "Indexed elements should resolve to distinct links");
    }

    @Test
    @DisplayName("Child element scoping — findElements on parent returns only its children")
    void testChildElementScoping() {
        driver.get("https://asynccodinghub.in/");

        WebElement nav = driver.findElement(By.tagName("nav"));
        List<WebElement> navLinks = nav.findElements(By.tagName("a"));

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        assertTrue(navLinks.size() < allLinks.size(),
                "Nav-scoped links should be fewer than all links on the page");
    }

    @Test
    @DisplayName("Click Start Learning → navigates to roadmap page")
    void testNavigationToRoadmap() {
        driver.get("https://asynccodinghub.in/");

        driver.findElement(By.xpath("//a[contains(normalize-space(),'Start Learning')]"))
              .click();

        assertTrue(driver.getCurrentUrl().contains("roadmap"),
                "URL should contain 'roadmap' after clicking Start Learning");
    }

    @Test
    @DisplayName("Back navigation — returns to homepage after visiting roadmap")
    void testBackNavigation() {
        driver.get("https://asynccodinghub.in/");
        String homeUrl = driver.getCurrentUrl();

        driver.findElement(By.xpath("//a[contains(normalize-space(),'Start Learning')]"))
              .click();

        driver.navigate().back();
        assertEquals(homeUrl, driver.getCurrentUrl(),
                "Back navigation should return to the homepage");
    }

    @Test
    @DisplayName("XPath findElements — find all h2 headings on homepage")
    void testFindElementsByXPath() {
        driver.get("https://asynccodinghub.in/");

        List<WebElement> headings = driver.findElements(By.xpath("//h2"));

        assertFalse(headings.isEmpty(),
                "Should find at least one h2 heading via XPath");
        headings.forEach(h ->
                assertNotNull(h.getText(), "Each h2 should return text"));
    }

    @Test
    @DisplayName("Element attributes — anchor hrefs are non-empty")
    void testElementAttributes() {
        driver.get("https://asynccodinghub.in/");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        assertTrue(links.size() > 0, "Page should have links");

        // Each indexed element should independently resolve and return its href
        WebElement firstLink = driver.findElement(By.index(By.tagName("a"), 0));
        assertNotNull(firstLink.getAttribute("href"),
                "First anchor should have a non-null href attribute");
    }

    @Test
    @DisplayName("Page source — contains expected site content")
    void testGetPageSource() {
        driver.get("https://asynccodinghub.in/");

        String source = driver.getPageSource();
        assertFalse(source.isBlank(), "Page source should not be blank");
        assertTrue(source.contains("<html") || source.contains("<HTML"),
                "Page source should contain HTML markup");
    }
}
