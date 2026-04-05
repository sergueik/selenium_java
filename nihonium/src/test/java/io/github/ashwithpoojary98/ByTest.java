package io.github.ashwithpoojary98;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link By} and its inner locator types.
 * No browser required — these are pure in-process tests.
 */
class ByTest {

    // ── Factory methods ───────────────────────────────────────────────────────

    @Test
    void cssSelector_returnsCssSelector() {
        By by = By.cssSelector(".foo > span");
        assertEquals(".foo > span", by.toCssSelector());
        assertFalse(by.isXPath());
    }

    @Test
    void id_buildsPoundSelector() {
        By by = By.id("submit-btn");
        assertEquals("#submit-btn", by.toCssSelector());
        assertFalse(by.isXPath());
    }

    @Test
    void className_buildsDotSelector() {
        By by = By.className("nav-item");
        assertEquals(".nav-item", by.toCssSelector());
        assertFalse(by.isXPath());
    }

    @Test
    void tagName_returnsTagAsSelector() {
        By by = By.tagName("input");
        assertEquals("input", by.toCssSelector());
        assertFalse(by.isXPath());
    }

    @Test
    void name_buildsAttributeSelector() {
        By by = By.name("username");
        assertEquals("[name=\"username\"]", by.toCssSelector());
        assertFalse(by.isXPath());
    }

    @Test
    void xpath_isXPathAndNullCss() {
        By by = By.xpath("//input[@type='text']");
        assertNull(by.toCssSelector());
        assertTrue(by.isXPath());
        assertEquals("//input[@type='text']", by.getSelector());
    }

    @Test
    void linkText_isXPath() {
        By by = By.linkText("Sign in");
        assertNull(by.toCssSelector());
        assertTrue(by.isXPath());
        assertTrue(by.getSelector().contains("normalize-space"));
    }

    @Test
    void partialLinkText_isXPath() {
        By by = By.partialLinkText("Sign");
        assertNull(by.toCssSelector());
        assertTrue(by.isXPath());
        assertTrue(by.getSelector().contains("contains"));
    }

    // ── By.chained — CSS combination ─────────────────────────────────────────

    @Test
    void chained_allCss_combinesWithSpace() {
        By chain = By.chained(By.id("form"), By.cssSelector("input.email"));
        assertEquals("#form input.email", chain.toCssSelector());
        assertFalse(chain.isXPath());
    }

    @Test
    void chained_threeAllCss_combinesAll() {
        By chain = By.chained(By.id("page"), By.className("section"), By.tagName("p"));
        assertEquals("#page .section p", chain.toCssSelector());
    }

    @Test
    void chained_singleLocator_returnsItsCss() {
        By chain = By.chained(By.id("root"));
        assertEquals("#root", chain.toCssSelector());
    }

    @Test
    void chained_withXPath_combinedCssIsNull() {
        By chain = By.chained(By.id("form"), By.xpath(".//input"));
        assertNull(chain.toCssSelector());
        assertFalse(chain.isXPath());
    }

    @Test
    void chained_getBys_returnsOriginalLocators() {
        By first  = By.id("parent");
        By second = By.cssSelector("span.label");
        By.ByChained chain = (By.ByChained) By.chained(first, second);

        By[] bys = chain.getBys();
        assertEquals(2, bys.length);
        assertEquals(first.toCssSelector(),  bys[0].toCssSelector());
        assertEquals(second.toCssSelector(), bys[1].toCssSelector());
    }

    @Test
    void chained_emptyArgs_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> By.chained());
    }

    @Test
    void chained_nullArgs_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> By.chained((By[]) null));
    }

    @Test
    void chained_toString_containsChainRepresentation() {
        By chain = By.chained(By.id("a"), By.cssSelector("b"));
        assertTrue(chain.toString().toLowerCase().contains("chained"));
    }

    // ── By.index ─────────────────────────────────────────────────────────────

    @Test
    void index_storesParentAndIndex() {
        By parent = By.cssSelector(".item");
        By.ByIndex idx = (By.ByIndex) By.index(parent, 2);

        assertEquals(parent.toCssSelector(), idx.getParent().toCssSelector());
        assertEquals(2, idx.getIndex());
    }

    @Test
    void index_toCssSelectorIsNull() {
        // ByIndex never exposes a CSS selector (resolution requires querySelectorAll)
        By idx = By.index(By.cssSelector(".row"), 0);
        assertNull(idx.toCssSelector());
    }

    @Test
    void index_isXPathIsFalse() {
        By idx = By.index(By.xpath("//li"), 3);
        assertFalse(idx.isXPath());
    }

    @Test
    void index_toString_containsIndexValue() {
        By idx = By.index(By.id("list"), 5);
        assertTrue(idx.toString().contains("5"));
    }

    @Test
    void index_xpathParent_preservesXPathSelector() {
        By parent = By.xpath("//li[@class='item']");
        By.ByIndex idx = (By.ByIndex) By.index(parent, 0);
        assertTrue(idx.getParent().isXPath());
        assertEquals("//li[@class='item']", idx.getParent().getSelector());
    }

    // ── xpathStringLiteral ────────────────────────────────────────────────────

    @Test
    void xpathStringLiteral_simpleString_wrapsInSingleQuotes() {
        assertEquals("'hello'", By.xpathStringLiteral("hello"));
    }

    @Test
    void xpathStringLiteral_withSingleQuote_usesConcatFunction() {
        String result = By.xpathStringLiteral("it's");
        assertTrue(result.startsWith("concat("), "Should use concat() for strings with '");
        assertTrue(result.contains("\"'\""), "Should escape single quote as double-quoted string");
    }

    @Test
    void xpathStringLiteral_empty_returnsEmptyLiteral() {
        assertEquals("''", By.xpathStringLiteral(""));
    }

    // ── toString ─────────────────────────────────────────────────────────────

    @Test
    void toString_includesClassName() {
        assertTrue(By.id("x").toString().toLowerCase().contains("id"));
        assertTrue(By.cssSelector(".x").toString().toLowerCase().contains("css"));
        assertTrue(By.xpath("//x").toString().toLowerCase().contains("xpath"));
    }
}
