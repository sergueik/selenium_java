package io.github.ashwithpoojary98;

/**
 * Mechanism used to locate elements within a document.
 *
 * <p>Use the static factory methods to create locators:
 * <pre>{@code
 * driver.findElement(By.id("submit-button"));
 * driver.findElement(By.cssSelector(".nav > a"));
 * driver.findElement(By.xpath("//input[@type='email']"));
 * driver.findElement(By.linkText("Sign in"));
 * }</pre>
 *
 * <p>Internally every locator converts to either a CSS selector string
 * (via {@link #toCssSelector()}) or an XPath expression (via {@link #getSelector()}
 * when {@link #isXPath()} is {@code true}).
 *
 * <p>Note: {@code ByLinkText} and {@code ByPartialLinkText} are represented as
 * XPath expressions internally so they can be dispatched through the same
 * code path as {@code ByXPath}.
 */
public abstract class By {

    /** The underlying selector string (CSS selector or XPath expression). */
    protected final String selector;

    protected By(String selector) {
        this.selector = selector;
    }

    /**
     * Returns the selector string used for CDP queries.
     * <p>For CSS-based locators this is the CSS selector.
     * For XPath-based locators (including link text) this is the XPath expression.
     *
     * @return selector string
     */
    public String getSelector() {
        return selector;
    }

    /**
     * Returns the CSS selector equivalent of this locator, or {@code null} if
     * this locator must be evaluated as XPath.
     *
     * @return CSS selector string, or {@code null}
     */
    public abstract String toCssSelector();

    /**
     * Returns {@code true} if this locator must be dispatched via XPath evaluation.
     *
     * @return {@code true} for XPath and link-text locators
     */
    public abstract boolean isXPath();

    // ── Factory methods ───────────────────────────────────────────────────────

    /** Locates elements by CSS selector. */
    public static By cssSelector(String selector) {
        return new ByCssSelector(selector);
    }

    /** Locates elements by XPath expression. */
    public static By xpath(String xpath) {
        return new ByXPath(xpath);
    }

    /** Locates elements by their {@code id} attribute (equivalent to {@code #id}). */
    public static By id(String id) {
        return new ById(id);
    }

    /** Locates elements by one of their CSS class names. */
    public static By className(String className) {
        return new ByClassName(className);
    }

    /** Locates elements by tag name. */
    public static By tagName(String tagName) {
        return new ByTagName(tagName);
    }

    /** Locates elements by their {@code name} attribute. */
    public static By name(String name) {
        return new ByName(name);
    }

    /**
     * Locates anchor ({@code <a>}) elements whose visible text exactly matches
     * {@code linkText}.
     *
     * <p>Internally converted to an XPath expression:
     * {@code //a[normalize-space(.)='linkText']}.
     */
    public static By linkText(String linkText) {
        return new ByLinkText(linkText);
    }

    /**
     * Locates anchor ({@code <a>}) elements whose visible text contains
     * {@code linkText} as a substring.
     *
     * <p>Internally converted to an XPath expression:
     * {@code //a[contains(normalize-space(.),'linkText')]}.
     */
    public static By partialLinkText(String linkText) {
        return new ByPartialLinkText(linkText);
    }

    // ── Chaining / indexing factory methods ──────────────────────────────────

    /**
     * Chains multiple locators so that each one is evaluated within the element
     * matched by the previous one.
     *
     * <p>When all constituent locators are CSS-based the chain is collapsed to a
     * single CSS descendant selector at construction time:
     * <pre>{@code
     * By.chained(By.id("form"), By.cssSelector("input[type='email']"))
     *   // → CSS: #form input[type='email']
     * }</pre>
     *
     * <p>When the chain contains XPath or other non-CSS locators resolution
     * proceeds step-by-step via CDP at query time.
     *
     * @param bys one or more locators, in parent-to-child order
     * @return a {@code By} that resolves through the chain
     */
    public static By chained(By... bys) {
        if (bys == null || bys.length == 0) {
            throw new IllegalArgumentException("chained() requires at least one locator");
        }
        return new ByChained(bys);
    }

    /**
     * Returns a locator that resolves to the element at the given 0-based
     * position among all matches produced by {@code parent}.
     *
     * <p>Used internally so that every element returned by {@code findElements}
     * can be individually re-resolved without risk of stale references.
     *
     * @param parent source locator
     * @param index  0-based index into the match list
     * @return a {@code By} that resolves to the nth match
     */
    public static By index(By parent, int index) {
        return new ByIndex(parent, index);
    }

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + selector;
    }

    // ── Implementations ───────────────────────────────────────────────────────

    private static final class ByCssSelector extends By {
        ByCssSelector(String selector) { super(selector); }

        @Override public String  toCssSelector() { return selector; }
        @Override public boolean isXPath()        { return false; }
    }

    private static final class ByXPath extends By {
        ByXPath(String xpath) { super(xpath); }

        @Override public String  toCssSelector() { return null; }
        @Override public boolean isXPath()        { return true; }
    }

    private static final class ById extends By {
        ById(String id) { super(id); }

        @Override public String  toCssSelector() { return "#" + selector; }
        @Override public boolean isXPath()        { return false; }
    }

    private static final class ByClassName extends By {
        ByClassName(String className) { super(className); }

        @Override public String  toCssSelector() { return "." + selector; }
        @Override public boolean isXPath()        { return false; }
    }

    private static final class ByTagName extends By {
        ByTagName(String tagName) { super(tagName); }

        @Override public String  toCssSelector() { return selector; }
        @Override public boolean isXPath()        { return false; }
    }

    private static final class ByName extends By {
        ByName(String name) { super(name); }

        @Override public String  toCssSelector() { return "[name=\"" + selector + "\"]"; }
        @Override public boolean isXPath()        { return false; }
    }

    /**
     * Matches {@code <a>} elements whose normalised text equals the given link text.
     *
     * <p>The raw link text is stored in {@link #selector} for human-readable
     * {@link #toString()} output. {@link #getSelector()} returns the generated XPath.
     */
    private static final class ByLinkText extends By {

        private final String linkText;

        ByLinkText(String linkText) {
            // selector field holds the XPath so it flows through the XPath dispatch path
            super("//a[normalize-space(.)=" + xpathStringLiteral(linkText) + "]");
            this.linkText = linkText;
        }

        @Override public String  toCssSelector() { return null; }
        @Override public boolean isXPath()        { return true; }

        @Override
        public String toString() {
            return "ByLinkText: " + linkText;
        }
    }

    /**
     * Matches {@code <a>} elements whose normalised text contains the given substring.
     *
     * <p>The raw substring is stored for {@link #toString()} output.
     * {@link #getSelector()} returns the generated XPath.
     */
    private static final class ByPartialLinkText extends By {

        private final String partialText;

        ByPartialLinkText(String partialText) {
            super("//a[contains(normalize-space(.)," + xpathStringLiteral(partialText) + ")]");
            this.partialText = partialText;
        }

        @Override public String  toCssSelector() { return null; }
        @Override public boolean isXPath()        { return true; }

        @Override
        public String toString() {
            return "ByPartialLinkText: " + partialText;
        }
    }

    /**
     * Chains multiple locators: each subsequent locator is evaluated within the
     * element found by the previous one.
     *
     * <p>When all constituent locators produce a CSS selector the chain is
     * represented as a single combined descendant CSS selector (e.g.
     * {@code #form input[type='email']}). Otherwise {@link #toCssSelector()}
     * returns {@code null} and {@code ChromeElement} resolves the chain
     * step-by-step via CDP.
     */
    public static final class ByChained extends By {

        private final By[] bys;
        /**
         * Combined CSS selector when every constituent locator is CSS-based;
         * {@code null} when at least one requires XPath or step-by-step resolution.
         */
        private final String combinedCss;

        ByChained(By... bys) {
            super(representativeString(bys));
            this.bys = bys.clone();
            this.combinedCss = tryBuildCss(bys);
        }

        private static String representativeString(By[] bys) {
            StringBuilder sb = new StringBuilder();
            for (By by : bys) {
                if (sb.length() > 0) sb.append(" -> ");
                sb.append(by.toString());
            }
            return sb.toString();
        }

        private static String tryBuildCss(By[] bys) {
            StringBuilder sb = new StringBuilder();
            for (By by : bys) {
                String css = by.toCssSelector();
                if (css == null) return null;
                if (sb.length() > 0) sb.append(' ');
                sb.append(css);
            }
            return sb.toString();
        }

        @Override public String  toCssSelector() { return combinedCss; }
        @Override public boolean isXPath()        { return false; }

        /** Returns the constituent locators in chain order. */
        public By[] getBys() { return bys.clone(); }

        @Override public String toString() { return "ByChained(" + selector + ")"; }
    }

    /**
     * Locates the element at a specific 0-based position among all matches
     * produced by a parent locator.
     *
     * <p>Used internally by {@code findElements} so that every returned element
     * can be individually re-resolved (no stale-element risk) without caching
     * a DOM node ID.
     */
    public static final class ByIndex extends By {

        private final By  parent;
        private final int index;

        ByIndex(By parent, int index) {
            super(parent.getSelector() + "[" + index + "]");
            this.parent = parent;
            this.index  = index;
        }

        @Override public String  toCssSelector() { return null; }
        @Override public boolean isXPath()        { return false; }

        public By  getParent() { return parent; }
        public int getIndex()  { return index; }

        @Override public String toString() {
            return "ByIndex(" + parent + ", " + index + ")";
        }
    }

    // ── XPath helpers ─────────────────────────────────────────────────────────

    /**
     * Produces a safe XPath string literal for the given value, handling
     * single quotes by using the XPath {@code concat()} function.
     *
     * <p>Examples:
     * <ul>
     *   <li>{@code "hello"}   → {@code 'hello'}</li>
     *   <li>{@code "it's"}    → {@code concat('it',"'",'s')}</li>
     * </ul>
     *
     * @param value raw string value
     * @return XPath string literal safe for embedding in an XPath expression
     */
    static String xpathStringLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        // Split on single quotes and concat the parts
        StringBuilder sb = new StringBuilder("concat(");
        String[] parts = value.split("'", -1);
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                sb.append(",\"'\",");
            }
            sb.append("'").append(parts[i]).append("'");
        }
        sb.append(")");
        return sb.toString();
    }
}
