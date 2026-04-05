package io.github.ashwithpoojary98.chrome;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.Dimension;
import io.github.ashwithpoojary98.Point;
import io.github.ashwithpoojary98.Rectangle;
import io.github.ashwithpoojary98.WebElement;
import io.github.ashwithpoojary98.cdp.domain.CSSDomain;
import io.github.ashwithpoojary98.cdp.domain.DOMDomain;
import io.github.ashwithpoojary98.cdp.domain.InputDomain;
import io.github.ashwithpoojary98.cdp.domain.RuntimeDomain;
import io.github.ashwithpoojary98.exception.ElementNotFoundException;
import io.github.ashwithpoojary98.network.NetworkMonitor;
import io.github.ashwithpoojary98.wait.AutoWaitEngine;
import io.github.ashwithpoojary98.wait.ElementWaitConditions;
import io.github.ashwithpoojary98.wait.WaitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * CDP-backed implementation of {@link WebElement}.
 *
 * <p>Every interaction (click, sendKeys, clear …) first waits for the element to
 * reach the required state via {@link AutoWaitEngine}, then resolves the live DOM
 * node through a fresh CDP call — so stale element exceptions cannot occur.
 *
 * <h3>Design notes</h3>
 * <ul>
 *   <li>No magic numbers: all timeouts and index constants are named.</li>
 *   <li>{@link #clear()} uses JavaScript to reset the value and fire framework
 *       events ({@code input}, {@code change}), making it compatible with React,
 *       Vue, and Angular forms.</li>
 *   <li>The scroll-stability loop sleeps between polls — never a CPU spin-wait.</li>
 * </ul>
 */
public class ChromeElement implements WebElement {

    private static final Logger log = LoggerFactory.getLogger(ChromeElement.class);

    // ── Scroll-stability polling ───────────────────────────────────────────────

    /**
     * Maximum time to wait for the element's position to stop changing after scroll.
     */
    private static final long SCROLL_STABILITY_TIMEOUT_MILLIS = 200L;

    /**
     * Interval between scroll-stability position samples.
     */
    private static final long SCROLL_STABILITY_POLL_MILLIS = 20L;

    /**
     * Consecutive identical positions required to declare the element stable.
     */
    private static final int SCROLL_STABLE_CHECKS_REQUIRED = 3;

    // ── CDP box-model content-quad indices ────────────────────────────────────
    // The CDP `content` quad is an 8-element flat array of (x,y) pairs:
    //   [x0,y0, x1,y1, x2,y2, x3,y3]  (top-left, top-right, bottom-right, bottom-left)

    private static final int BOX_X_TOP_LEFT = 0;
    private static final int BOX_Y_TOP_LEFT = 1;
    private static final int BOX_X_BOTTOM_RIGHT = 4;
    private static final int BOX_Y_BOTTOM_RIGHT = 5;

    // ── JS scripts (constants to avoid duplication) ───────────────────────────

    /**
     * Clears an input/textarea and fires the events that JS frameworks listen for.
     * Works with React (uses native setter), Vue, and Angular.
     */
    private static final String SCRIPT_CLEAR_INPUT =
            "function() {" +
                    "  var nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value');" +
                    "  if (nativeSetter && nativeSetter.set) {" +
                    "    nativeSetter.set.call(this, '');" +
                    "  } else {" +
                    "    this.value = '';" +
                    "  }" +
                    "  this.dispatchEvent(new Event('input',  {bubbles: true}));" +
                    "  this.dispatchEvent(new Event('change', {bubbles: true}));" +
                    "}";

    private static final String SCRIPT_GET_TEXT = "function() { return this.textContent; }";
    private static final String SCRIPT_IS_VISIBLE =
            "function() { return !!(this.offsetWidth || this.offsetHeight || this.getClientRects().length); }";

    // ── CDP JSON keys ─────────────────────────────────────────────────────────

    private static final String KEY_ROOT     = "root";
    private static final String KEY_NODE_ID  = "nodeId";
    private static final String KEY_NODE_IDS = "nodeIds";
    private static final String KEY_NODE_NAME = "nodeName";
    private static final String KEY_NODE = "node";
    private static final String KEY_OBJECT = "object";
    private static final String KEY_OBJECT_ID = "objectId";
    private static final String KEY_RESULT = "result";
    private static final String KEY_VALUE = "value";
    private static final String KEY_TYPE = "type";
    private static final String KEY_MODEL = "model";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_NAME = "name";

    private static final String TYPE_OBJECT = "object";
    private static final String ATTR_CHECKED = "checked";
    private static final String ATTR_DISABLED = "disabled";
    private static final String CSS_DISPLAY = "display";
    private static final String CSS_VISIBILITY = "visibility";
    private static final String CSS_OPACITY = "opacity";
    private static final String CSS_DISPLAY_NONE = "none";
    private static final String CSS_VISIBILITY_HIDDEN = "hidden";
    private static final String CSS_OPACITY_ZERO = "0";

    // ─────────────────────────────────────────────────────────────────────────

    private final By locator;
    private final DOMDomain domDomain;
    private final RuntimeDomain runtimeDomain;
    private final InputDomain inputDomain;
    private final CSSDomain cssDomain;
    private final WaitConfig waitConfig;
    private final NetworkMonitor networkMonitor;
    private final AutoWaitEngine autoWaitEngine;

    // ── Constructors ──────────────────────────────────────────────────────────

    public ChromeElement(By locator, DOMDomain domDomain, RuntimeDomain runtimeDomain,
                         InputDomain inputDomain, CSSDomain cssDomain) {
        this(locator, domDomain, runtimeDomain, inputDomain, cssDomain,
                WaitConfig.defaultConfig(), null);
    }

    public ChromeElement(By locator, DOMDomain domDomain, RuntimeDomain runtimeDomain,
                         InputDomain inputDomain, CSSDomain cssDomain, WaitConfig waitConfig) {
        this(locator, domDomain, runtimeDomain, inputDomain, cssDomain, waitConfig, null);
    }

    public ChromeElement(By locator, DOMDomain domDomain, RuntimeDomain runtimeDomain,
                         InputDomain inputDomain, CSSDomain cssDomain,
                         WaitConfig waitConfig, NetworkMonitor networkMonitor) {
        this.locator = locator;
        this.domDomain = domDomain;
        this.runtimeDomain = runtimeDomain;
        this.inputDomain = inputDomain;
        this.cssDomain = cssDomain;
        this.waitConfig = waitConfig;
        this.networkMonitor = networkMonitor;

        ElementWaitConditions conditions =
                new ElementWaitConditions(domDomain, cssDomain, runtimeDomain);
        this.autoWaitEngine = new AutoWaitEngine(conditions, waitConfig, networkMonitor);
    }

    // ── WebElement interactions ───────────────────────────────────────────────

    @Override
    public void click() {
        autoWaitEngine.waitForElementClickable(locator);
        try {
            int nodeId = resolveNodeId();
            domDomain.scrollIntoViewIfNeeded(nodeId).join();
            waitForScrollStability(nodeId);

            JsonObject boxModel = domDomain.getBoxModel(nodeId).join();
            double[] center = extractCenter(boxModel);

            inputDomain.click(center[0], center[1]).join();
            log.debug("Clicked {} at ({}, {})", locator, center[0], center[1]);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        if (keysToSend == null || keysToSend.length == 0) {
            return;
        }
        autoWaitEngine.waitForElementInteractable(locator);
        try {
            StringBuilder text = new StringBuilder();
            for (CharSequence seq : keysToSend) {
                if (seq != null) {
                    text.append(seq);
                }
            }
            if (text.isEmpty()) {
                return;
            }

            int nodeId = resolveNodeId();
            domDomain.focus(nodeId).join();
            inputDomain.insertText(text.toString()).join();
            log.debug("Sent {} char(s) to {}", text.length(), locator);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to element: " + locator, e);
        }
    }

    @Override
    public void clear() {
        autoWaitEngine.waitForElementInteractable(locator);
        try {
            int nodeId = resolveNodeId();
            String objId = resolveObjectId(nodeId);
            runtimeDomain.callFunctionOn(objId, SCRIPT_CLEAR_INPUT, null).join();
            runtimeDomain.releaseObject(objId).join();
            log.debug("Cleared {}", locator);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear element: " + locator, e);
        }
    }

    @Override
    public void submit() {
        try {
            int nodeId = resolveNodeId();
            String objId = resolveObjectId(nodeId);
            runtimeDomain.callFunctionOn(
                    objId,
                    "function() { this.form ? this.form.submit() : this.submit(); }",
                    null).join();
            runtimeDomain.releaseObject(objId).join();
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to submit element: " + locator, e);
        }
    }

    // ── Introspection ─────────────────────────────────────────────────────────

    @Override
    public String getTagName() {
        try {
            int nodeId = resolveNodeId();
            JsonObject result = domDomain.describeNode(nodeId, 0).join();
            return result.getAsJsonObject(KEY_NODE).get(KEY_NODE_NAME).getAsString().toLowerCase();
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get tag name for element: " + locator, e);
        }
    }

    @Override
    public String getAttribute(String name) {
        try {
            int nodeId = resolveNodeId();
            JsonObject result = domDomain.getAttributes(nodeId).join();
            JsonArray attrs = result.getAsJsonArray("attributes");

            for (int i = 0; i < attrs.size() - 1; i += 2) {
                if (attrs.get(i).getAsString().equals(name)) {
                    return attrs.get(i + 1).getAsString();
                }
            }
            return null;
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get attribute '" + name + "' for: " + locator, e);
        }
    }

    @Override
    public boolean isSelected() {
        try {
            return getAttribute(ATTR_CHECKED) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isEnabled() {
        try {
            return getAttribute(ATTR_DISABLED) == null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getText() {
        autoWaitEngine.waitForElementVisible(locator);
        try {
            int nodeId = resolveNodeId();
            String objId = resolveObjectId(nodeId);

            JsonObject result = runtimeDomain.callFunctionOn(objId, SCRIPT_GET_TEXT, null).join();
            runtimeDomain.releaseObject(objId).join();

            JsonObject resultObj = result.getAsJsonObject(KEY_RESULT);
            return resultObj.has(KEY_VALUE) ? resultObj.get(KEY_VALUE).getAsString() : "";
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text for element: " + locator, e);
        }
    }

    @Override
    public boolean isDisplayed() {
        try {
            String display = getCssValue(CSS_DISPLAY);
            String visibility = getCssValue(CSS_VISIBILITY);
            String opacity = getCssValue(CSS_OPACITY);

            return !CSS_DISPLAY_NONE.equals(display)
                    && !CSS_VISIBILITY_HIDDEN.equals(visibility)
                    && !CSS_OPACITY_ZERO.equals(opacity);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Point getLocation() {
        try {
            int nodeId = resolveNodeId();
            JsonObject model = extractBoxModel(nodeId);
            JsonArray content = model.getAsJsonArray(KEY_CONTENT);
            return new Point(
                    content.get(BOX_X_TOP_LEFT).getAsInt(),
                    content.get(BOX_Y_TOP_LEFT).getAsInt());
        } catch (Exception e) {
            return new Point(0, 0);
        }
    }

    @Override
    public Dimension getSize() {
        try {
            int nodeId = resolveNodeId();
            JsonObject model = extractBoxModel(nodeId);
            JsonArray content = model.getAsJsonArray(KEY_CONTENT);

            int width = content.get(BOX_X_BOTTOM_RIGHT).getAsInt()
                    - content.get(BOX_X_TOP_LEFT).getAsInt();
            int height = content.get(BOX_Y_BOTTOM_RIGHT).getAsInt()
                    - content.get(BOX_Y_TOP_LEFT).getAsInt();
            return new Dimension(width, height);
        } catch (Exception e) {
            return new Dimension(0, 0);
        }
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(getLocation(), getSize());
    }

    @Override
    public String getCssValue(String propertyName) {
        try {
            int nodeId = resolveNodeId();
            JsonObject result = cssDomain.getComputedStyleForNode(nodeId).join();
            JsonArray computedStyle = result.getAsJsonArray("computedStyle");

            for (JsonElement el : computedStyle) {
                JsonObject prop = el.getAsJsonObject();
                if (prop.get(KEY_NAME).getAsString().equals(propertyName)) {
                    return prop.get(KEY_VALUE).getAsString();
                }
            }
            return "";
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get CSS value '" + propertyName + "' for: " + locator, e);
        }
    }

    // ── Child element search ──────────────────────────────────────────────────

    /**
     * Finds a single child element scoped within this element.
     *
     * <p>The search is automatically scoped to descendants of this element by
     * wrapping the locator in a {@link By#chained(By...) chained} locator.
     * When both this element's locator and {@code by} are CSS-based the result
     * is a single combined CSS descendant selector evaluated at query time.
     */
    @Override
    public WebElement findElement(By by) {
        return new ChromeElement(By.chained(this.locator, by), domDomain, runtimeDomain,
                inputDomain, cssDomain, waitConfig, networkMonitor);
    }

    /**
     * Finds all child elements matching {@code by} scoped within this element.
     *
     * <p>Returns an empty list (never throws) when no matches are found.
     */
    @Override
    public List<WebElement> findElements(By by) {
        try {
            int parentNodeId = resolveNodeId();
            return findAllChildElements(parentNodeId, by);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private List<WebElement> findAllChildElements(int parentNodeId, By childBy) {
        try {
            String cssSelector = childBy.toCssSelector();
            if (cssSelector != null) {
                JsonObject r = domDomain.querySelectorAll(parentNodeId, cssSelector).join();
                JsonArray nodeIds = r.getAsJsonArray(KEY_NODE_IDS);
                List<WebElement> elements = new ArrayList<>();
                if (nodeIds != null) {
                    By scopedBy = By.chained(this.locator, childBy);
                    for (int i = 0; i < nodeIds.size(); i++) {
                        elements.add(new ChromeElement(By.index(scopedBy, i), domDomain,
                                runtimeDomain, inputDomain, cssDomain, waitConfig, networkMonitor));
                    }
                }
                return elements;
            }

            if (childBy.isXPath()) {
                String objId = resolveObjectId(parentNodeId);
                try {
                    String escaped = childBy.getSelector()
                            .replace("\\", "\\\\").replace("'", "\\'");
                    String countScript = "function() { return document.evaluate('"
                            + escaped + "', this, null, "
                            + "XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null).snapshotLength; }";
                    JsonObject countResult = runtimeDomain
                            .callFunctionOn(objId, countScript, null).join();
                    int count = countResult.getAsJsonObject(KEY_RESULT)
                            .get(KEY_VALUE).getAsInt();
                    By scopedBy = By.chained(this.locator, childBy);
                    List<WebElement> elements = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        elements.add(new ChromeElement(By.index(scopedBy, i), domDomain,
                                runtimeDomain, inputDomain, cssDomain, waitConfig, networkMonitor));
                    }
                    return elements;
                } finally {
                    try { runtimeDomain.releaseObject(objId).join(); } catch (Exception ignored) {}
                }
            }

            return new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Resolves the live DOM node ID for this element's locator.
     *
     * <p>Dispatches to specialised helpers for {@link By.ByIndex} and
     * non-CSS {@link By.ByChained} locators; all other locators are resolved
     * directly via CSS ({@code DOM.querySelector}) or XPath.
     *
     * @return node ID (always &gt; 0)
     * @throws ElementNotFoundException if the element cannot be found
     */
    private int resolveNodeId() {
        // ByIndex: find the nth element among all matches
        if (locator instanceof By.ByIndex) {
            return resolveIndexedNode((By.ByIndex) locator);
        }

        try {
            // CSS path (also handles CSS-combinable ByChained transparently)
            String cssSelector = locator.toCssSelector();
            if (cssSelector != null) {
                JsonObject docResult = domDomain.getDocument().join();
                int documentNode = docResult.getAsJsonObject(KEY_ROOT)
                        .get(KEY_NODE_ID).getAsInt();
                JsonObject r = domDomain.querySelector(documentNode, cssSelector).join();
                int nodeId = r.get(KEY_NODE_ID).getAsInt();
                if (nodeId == 0) {
                    throw new ElementNotFoundException("Element not found: " + locator);
                }
                return nodeId;
            }

            // XPath path
            if (locator.isXPath()) {
                return resolveNodeIdByXPath(locator.getSelector());
            }

            // Mixed ByChained (non-CSS-combinable): step-by-step resolution
            if (locator instanceof By.ByChained) {
                return resolveChainedNode((By.ByChained) locator);
            }

            throw new UnsupportedOperationException(
                    "Unsupported locator type: " + locator.getClass().getSimpleName());
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ElementNotFoundException("Failed to locate element: " + locator, e);
        }
    }

    /**
     * Resolves a {@link By.ByIndex} locator by querying all matches for the
     * parent locator and returning the node ID at the requested position.
     */
    private int resolveIndexedNode(By.ByIndex byIndex) {
        By parent = byIndex.getParent();
        int index  = byIndex.getIndex();

        try {
            // CSS-based parent (including CSS-combinable ByChained)
            String cssSelector = parent.toCssSelector();
            if (cssSelector != null) {
                JsonObject docResult = domDomain.getDocument().join();
                int documentNode = docResult.getAsJsonObject(KEY_ROOT)
                        .get(KEY_NODE_ID).getAsInt();
                JsonObject r = domDomain.querySelectorAll(documentNode, cssSelector).join();
                JsonArray nodeIds = r.getAsJsonArray(KEY_NODE_IDS);
                if (nodeIds == null || index >= nodeIds.size()) {
                    throw new ElementNotFoundException("Index " + index + " out of bounds ("
                            + (nodeIds == null ? 0 : nodeIds.size())
                            + " matches) for: " + parent);
                }
                return nodeIds.get(index).getAsInt();
            }

            // XPath-based parent: use positional predicate (XPath is 1-based)
            if (parent.isXPath()) {
                String xpath = "(" + parent.getSelector() + ")[" + (index + 1) + "]";
                return resolveNodeIdByXPath(xpath);
            }

            // Mixed ByChained parent: resolve context up to penultimate By, then
            // find the nth child using the last By within that context
            if (parent instanceof By.ByChained) {
                By[] bys = ((By.ByChained) parent).getBys();
                int contextNodeId = resolveChainedToContext(bys);
                return resolveNthInContext(contextNodeId, bys[bys.length - 1], index);
            }

            throw new UnsupportedOperationException(
                    "Unsupported parent locator type for By.index: "
                            + parent.getClass().getSimpleName());
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ElementNotFoundException(
                    "Failed to resolve indexed element: " + byIndex, e);
        }
    }

    /**
     * Resolves a mixed (non-CSS-combinable) {@link By.ByChained} locator
     * step-by-step: first By against the document, subsequent Bys scoped to
     * the node found by the prior step.
     */
    private int resolveChainedNode(By.ByChained chained) {
        By[] bys = chained.getBys();
        int nodeId = new ChromeElement(bys[0], domDomain, runtimeDomain,
                inputDomain, cssDomain, waitConfig, networkMonitor).resolveNodeId();
        for (int i = 1; i < bys.length; i++) {
            nodeId = resolveWithContext(nodeId, bys[i]);
        }
        return nodeId;
    }

    /**
     * Resolves the first {@code bys.length - 1} entries of a chain and returns
     * the resulting node ID, which serves as context for finding the last By's
     * matches.
     */
    private int resolveChainedToContext(By[] bys) {
        int nodeId = new ChromeElement(bys[0], domDomain, runtimeDomain,
                inputDomain, cssDomain, waitConfig, networkMonitor).resolveNodeId();
        for (int i = 1; i < bys.length - 1; i++) {
            nodeId = resolveWithContext(nodeId, bys[i]);
        }
        return nodeId;
    }

    /**
     * Resolves a single {@code By} scoped to {@code contextNodeId} (i.e. the
     * search is limited to descendants of that node).
     */
    private int resolveWithContext(int contextNodeId, By by) {
        String cssSelector = by.toCssSelector();
        if (cssSelector != null) {
            try {
                JsonObject r = domDomain.querySelector(contextNodeId, cssSelector).join();
                int nodeId = r.get(KEY_NODE_ID).getAsInt();
                if (nodeId == 0) {
                    throw new ElementNotFoundException(
                            "Element not found within context: " + by);
                }
                return nodeId;
            } catch (ElementNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new ElementNotFoundException(
                        "Failed to find element in context: " + by, e);
            }
        }

        if (by.isXPath()) {
            String objectId = resolveObjectId(contextNodeId);
            try {
                return resolveNodeByXPathInContext(objectId, by.getSelector());
            } finally {
                try { runtimeDomain.releaseObject(objectId).join(); } catch (Exception ignored) {}
            }
        }

        throw new UnsupportedOperationException(
                "Unsupported locator in chained resolution: "
                        + by.getClass().getSimpleName());
    }

    /**
     * Evaluates an XPath expression with {@code contextObjectId} as the context
     * node (i.e. {@code this} in the script) and returns the first matching
     * node's ID.
     */
    private int resolveNodeByXPathInContext(String contextObjectId, String xpath) {
        String escaped = xpath.replace("\\", "\\\\").replace("'", "\\'");
        String script = "function() { "
                + "var r = document.evaluate('" + escaped + "', this, null, "
                + "XPathResult.FIRST_ORDERED_NODE_TYPE, null); "
                + "return r.singleNodeValue; }";
        try {
            JsonObject result = runtimeDomain
                    .callFunctionOn(contextObjectId, script, null).join();
            JsonObject resultObj = result.getAsJsonObject(KEY_RESULT);
            if (TYPE_OBJECT.equals(resultObj.get(KEY_TYPE).getAsString())
                    && resultObj.has(KEY_OBJECT_ID)) {
                String objId = resultObj.get(KEY_OBJECT_ID).getAsString();
                JsonObject nodeRes = domDomain.requestNode(objId).join();
                int nodeId = nodeRes.get(KEY_NODE_ID).getAsInt();
                runtimeDomain.releaseObject(objId).join();
                if (nodeId == 0) {
                    throw new ElementNotFoundException(
                            "XPath returned no node in context: " + xpath);
                }
                return nodeId;
            }
            throw new ElementNotFoundException(
                    "XPath matched no element in context: " + xpath);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ElementNotFoundException(
                    "Failed to resolve XPath in context: " + xpath, e);
        }
    }

    /**
     * Returns the node ID of the {@code index}-th (0-based) element matching
     * {@code by} within the subtree rooted at {@code contextNodeId}.
     */
    private int resolveNthInContext(int contextNodeId, By by, int index) {
        String css = by.toCssSelector();
        if (css != null) {
            try {
                JsonObject r = domDomain.querySelectorAll(contextNodeId, css).join();
                JsonArray nodeIds = r.getAsJsonArray(KEY_NODE_IDS);
                if (nodeIds == null || index >= nodeIds.size()) {
                    throw new ElementNotFoundException("Index " + index + " out of bounds ("
                            + (nodeIds == null ? 0 : nodeIds.size())
                            + " matches) for: " + by);
                }
                return nodeIds.get(index).getAsInt();
            } catch (ElementNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new ElementNotFoundException(
                        "Failed to find nth element in context: " + by, e);
            }
        }

        if (by.isXPath()) {
            String objId = resolveObjectId(contextNodeId);
            try {
                return resolveNthByXPathInContext(objId, by.getSelector(), index);
            } finally {
                try { runtimeDomain.releaseObject(objId).join(); } catch (Exception ignored) {}
            }
        }

        throw new UnsupportedOperationException(
                "Unsupported locator in context resolution: "
                        + by.getClass().getSimpleName());
    }

    /**
     * Evaluates an XPath snapshot relative to {@code contextObjectId} and
     * returns the node ID of the element at position {@code index} (0-based).
     */
    private int resolveNthByXPathInContext(String contextObjectId, String xpath, int index) {
        String escaped = xpath.replace("\\", "\\\\").replace("'", "\\'");
        String script = "function() { "
                + "var snap = document.evaluate('" + escaped + "', this, null, "
                + "XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); "
                + "return snap.snapshotLength > " + index
                + " ? snap.snapshotItem(" + index + ") : null; }";
        try {
            JsonObject result = runtimeDomain
                    .callFunctionOn(contextObjectId, script, null).join();
            JsonObject resultObj = result.getAsJsonObject(KEY_RESULT);
            if (TYPE_OBJECT.equals(resultObj.get(KEY_TYPE).getAsString())
                    && resultObj.has(KEY_OBJECT_ID)) {
                String objId = resultObj.get(KEY_OBJECT_ID).getAsString();
                JsonObject nodeRes = domDomain.requestNode(objId).join();
                int nodeId = nodeRes.get(KEY_NODE_ID).getAsInt();
                runtimeDomain.releaseObject(objId).join();
                if (nodeId == 0) {
                    throw new ElementNotFoundException(
                            "XPath snapshot item was null: " + xpath);
                }
                return nodeId;
            }
            throw new ElementNotFoundException(
                    "XPath returned null at index " + index + ": " + xpath);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ElementNotFoundException(
                    "Failed to resolve XPath[" + index + "] in context: " + xpath, e);
        }
    }

    /**
     * Resolves a DOM node ID by evaluating an XPath expression via the Runtime domain.
     *
     * @param xpath XPath expression
     * @return node ID
     * @throws ElementNotFoundException if no node matches
     */
    private int resolveNodeIdByXPath(String xpath) {
        try {
            // Use a parameterised approach to avoid XPath-in-JS injection issues
            String escaped = xpath.replace("\\", "\\\\").replace("'", "\\'");
            String script = "document.evaluate('" + escaped + "', document, null, "
                    + "XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue";

            JsonObject result = runtimeDomain.evaluate(script, false).join();
            JsonObject resultObj = result.getAsJsonObject(KEY_RESULT);

            if (TYPE_OBJECT.equals(resultObj.get(KEY_TYPE).getAsString())
                    && resultObj.has(KEY_OBJECT_ID)) {
                String objectId = resultObj.get(KEY_OBJECT_ID).getAsString();
                JsonObject nodeRes = domDomain.requestNode(objectId).join();
                int nodeId = nodeRes.get(KEY_NODE_ID).getAsInt();
                runtimeDomain.releaseObject(objectId).join();

                if (nodeId == 0) {
                    throw new ElementNotFoundException("XPath returned no node: " + xpath);
                }
                return nodeId;
            }
            throw new ElementNotFoundException("XPath matched no element: " + xpath);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ElementNotFoundException(
                    "Failed to resolve node by XPath: " + xpath, e);
        }
    }

    /**
     * Resolves a runtime object ID for a given node ID (needed for JS calls).
     *
     * @param nodeId DOM node ID
     * @return runtime object ID
     */
    private String resolveObjectId(int nodeId) {
        JsonObject resolved = domDomain.resolveNode(nodeId).join();
        return resolved.getAsJsonObject(KEY_OBJECT).get(KEY_OBJECT_ID).getAsString();
    }

    /**
     * Extracts the {@code model} sub-object from a {@code DOM.getBoxModel} result.
     *
     * @param nodeId DOM node ID
     * @return the {@code model} JSON object
     */
    private JsonObject extractBoxModel(int nodeId) {
        return domDomain.getBoxModel(nodeId).join().getAsJsonObject(KEY_MODEL);
    }

    /**
     * Computes the viewport center of an element from its box model.
     *
     * @param boxModel result of {@code DOM.getBoxModel}
     * @return {@code [centerX, centerY]}
     */
    private double[] extractCenter(JsonObject boxModel) {
        JsonArray content = boxModel.getAsJsonObject(KEY_MODEL).getAsJsonArray(KEY_CONTENT);
        double x1 = content.get(BOX_X_TOP_LEFT).getAsDouble();
        double y1 = content.get(BOX_Y_TOP_LEFT).getAsDouble();
        double x2 = content.get(BOX_X_BOTTOM_RIGHT).getAsDouble();
        double y2 = content.get(BOX_Y_BOTTOM_RIGHT).getAsDouble();
        return new double[]{(x1 + x2) / 2.0, (y1 + y2) / 2.0};
    }

    /**
     * Waits for the element's viewport position to stabilise after a scroll,
     * sleeping between samples to avoid CPU waste.
     *
     * @param nodeId DOM node ID to monitor
     */
    private void waitForScrollStability(int nodeId) {
        long deadline = System.currentTimeMillis() + SCROLL_STABILITY_TIMEOUT_MILLIS;
        Point previous = null;
        int stableCount = 0;

        while (System.currentTimeMillis() < deadline) {
            Point current = samplePosition(nodeId);

            if (previous != null
                    && previous.getX() == current.getX()
                    && previous.getY() == current.getY()) {
                if (++stableCount >= SCROLL_STABLE_CHECKS_REQUIRED) {
                    return;
                }
            } else {
                stableCount = 0;
            }
            previous = current;

            try {
                Thread.sleep(SCROLL_STABILITY_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Give up on stability check — proceed with click
            }
        }
    }

    /**
     * Samples the top-left position of the element's box model.
     *
     * @param nodeId DOM node ID
     * @return current element position, or {@code Point(0,0)} on failure
     */
    private Point samplePosition(int nodeId) {
        try {
            JsonObject model = extractBoxModel(nodeId);
            JsonArray content = model.getAsJsonArray(KEY_CONTENT);
            return new Point(
                    content.get(BOX_X_TOP_LEFT).getAsInt(),
                    content.get(BOX_Y_TOP_LEFT).getAsInt());
        } catch (Exception e) {
            return new Point(0, 0);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "ChromeElement[" + locator + "]";
    }
}
