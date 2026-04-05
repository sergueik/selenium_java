package io.github.ashwithpoojary98.chrome;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.WebDriver;
import io.github.ashwithpoojary98.WebElement;

/**
 * CDP-backed implementation of {@link WebDriver.TargetLocator}.
 *
 * <h3>Supported operations</h3>
 * <ul>
 *   <li>{@link #window(String)} — activates a tab/window by its CDP target ID</li>
 *   <li>{@link #defaultContent()} — re-focuses the top-level document</li>
 *   <li>{@link #activeElement()} — returns the element that currently has focus</li>
 * </ul>
 *
 * <h3>Unsupported operations</h3>
 * <p>Frame switching ({@link #frame(int)}, {@link #frame(String)},
 * {@link #frame(WebElement)}, {@link #parentFrame()}) requires connecting to a
 * child browsing context over a separate CDP session.  This multi-session
 * architecture is not yet implemented.  These methods throw
 * {@link UnsupportedOperationException} so that callers receive an explicit
 * failure rather than a silent no-op.
 */
class ChromeTargetLocator implements WebDriver.TargetLocator {

    // ── CDP field names ───────────────────────────────────────────────────────

    private static final String SCRIPT_ACTIVE_ELEMENT = "document.activeElement";
    private static final String FIELD_RESULT          = "result";
    private static final String FIELD_TYPE            = "type";
    private static final String FIELD_OBJECT_ID       = "objectId";
    private static final String TYPE_OBJECT           = "object";

    // ─────────────────────────────────────────────────────────────────────────

    private final ChromeDriver driver;

    ChromeTargetLocator(ChromeDriver driver) {
        this.driver = driver;
    }

    // ── Frame switching (not yet supported) ───────────────────────────────────

    /**
     * @throws UnsupportedOperationException always — frame switching requires
     *         multi-session CDP support which is not yet implemented
     */
    @Override
    public WebDriver frame(int index) {
        throw new UnsupportedOperationException(
                "Frame switching by index is not yet supported. "
                + "CDP frame isolation requires a separate child session.");
    }

    /**
     * @throws UnsupportedOperationException always — frame switching requires
     *         multi-session CDP support which is not yet implemented
     */
    @Override
    public WebDriver frame(String nameOrId) {
        throw new UnsupportedOperationException(
                "Frame switching by name/id is not yet supported. "
                + "CDP frame isolation requires a separate child session.");
    }

    /**
     * @throws UnsupportedOperationException always — frame switching requires
     *         multi-session CDP support which is not yet implemented
     */
    @Override
    public WebDriver frame(WebElement frameElement) {
        throw new UnsupportedOperationException(
                "Frame switching by element is not yet supported. "
                + "CDP frame isolation requires a separate child session.");
    }

    /**
     * @throws UnsupportedOperationException always — frame switching requires
     *         multi-session CDP support which is not yet implemented
     */
    @Override
    public WebDriver parentFrame() {
        throw new UnsupportedOperationException(
                "parentFrame() is not yet supported. "
                + "CDP frame isolation requires a separate child session.");
    }

    // ── Window switching ──────────────────────────────────────────────────────

    /**
     * Activates the tab or window identified by {@code nameOrHandle}.
     *
     * <p>{@code nameOrHandle} must be a CDP target ID as returned by
     * {@link WebDriver#getWindowHandles()}.  Named windows (via
     * {@code window.open(..., 'name')}) are not yet supported.
     *
     * @param nameOrHandle CDP target ID of the window to switch to
     * @return this driver
     * @throws RuntimeException if the target cannot be activated
     */
    @Override
    public WebDriver window(String nameOrHandle) {
        try {
            driver.getBrowserDomain().activateTarget(nameOrHandle).join();
            return driver;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to switch to window with handle: " + nameOrHandle, e);
        }
    }

    // ── Document focus ────────────────────────────────────────────────────────

    /**
     * Switches focus back to the top-level document (main frame).
     *
     * <p>Since Nihonium does not yet implement frame switching, this is
     * effectively a no-op — the driver is always focused on the top frame.
     *
     * @return this driver
     */
    @Override
    public WebDriver defaultContent() {
        return driver;
    }

    // ── Active element ────────────────────────────────────────────────────────

    /**
     * Returns the element that currently has keyboard focus.
     *
     * <p>If no element has focus (e.g. {@code document.activeElement} is
     * {@code document.body}), the body element is returned.
     *
     * @return a {@link WebElement} representing the focused element
     * @throws RuntimeException if the active element cannot be determined
     */
    @Override
    public WebElement activeElement() {
        try {
            // Evaluate document.activeElement → get objectId
            JsonObject evalResult = driver.getRuntimeDomain()
                    .evaluate(SCRIPT_ACTIVE_ELEMENT, false).join();
            JsonObject resultObj  = evalResult.getAsJsonObject(FIELD_RESULT);

            if (!TYPE_OBJECT.equals(resultObj.get(FIELD_TYPE).getAsString())
                    || !resultObj.has(FIELD_OBJECT_ID)) {
                // Fallback: return body element
                return driver.findElement(By.tagName("body"));
            }

            // Request the DOM node ID so we can build a stable CSS selector
            String objectId = resultObj.get(FIELD_OBJECT_ID).getAsString();
            JsonObject nodeResult = driver.getDomDomain().requestNode(objectId).join();
            int nodeId = nodeResult.get("nodeId").getAsInt();
            driver.getRuntimeDomain().releaseObject(objectId).join();

            // Build a unique CSS selector from the element's attributes
            String cssSelector = buildUniqueSelectorForNode(nodeId);
            return driver.findElement(By.cssSelector(cssSelector));

        } catch (Exception e) {
            throw new RuntimeException("Failed to get active element", e);
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Attempts to build a unique CSS selector for a DOM node.
     *
     * <p>Priority: {@code #id} → {@code [name="..."]} → JS-generated path.
     *
     * @param nodeId CDP node ID
     * @return a CSS selector string
     */
    private String buildUniqueSelectorForNode(int nodeId) {
        try {
            JsonObject resolved = driver.getDomDomain().resolveNode(nodeId).join();
            String objectId     = resolved.getAsJsonObject("object").get("objectId").getAsString();

            // Ask JS to give us the best available selector
            String script =
                    "function() {" +
                    "  if (this.id)   return '#' + CSS.escape(this.id);" +
                    "  if (this.name) return '[name=\"' + this.name + '\"]';" +
                    "  var path = [], el = this;" +
                    "  while (el && el.nodeType === 1) {" +
                    "    var idx = 1, sib = el.previousElementSibling;" +
                    "    while (sib) { idx++; sib = sib.previousElementSibling; }" +
                    "    path.unshift(el.tagName.toLowerCase() + ':nth-child(' + idx + ')');" +
                    "    el = el.parentElement;" +
                    "  }" +
                    "  return path.join(' > ');" +
                    "}";

            JsonObject result = driver.getRuntimeDomain()
                    .callFunctionOn(objectId, script, null).join();
            driver.getRuntimeDomain().releaseObject(objectId).join();

            JsonObject resultObj = result.getAsJsonObject("result");
            if (resultObj.has("value")) {
                return resultObj.get("value").getAsString();
            }
        } catch (Exception ignored) { }

        // Last resort fallback
        return "body";
    }
}
