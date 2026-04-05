package io.github.ashwithpoojary98.wait;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.cdp.domain.CSSDomain;
import io.github.ashwithpoojary98.cdp.domain.DOMDomain;
import io.github.ashwithpoojary98.cdp.domain.RuntimeDomain;

public class ElementWaitConditions {

    private final DOMDomain domDomain;
    private final CSSDomain cssDomain;
    private final RuntimeDomain runtimeDomain;

    public ElementWaitConditions(DOMDomain domDomain, CSSDomain cssDomain, RuntimeDomain runtimeDomain) {
        this.domDomain = domDomain;
        this.cssDomain = cssDomain;
        this.runtimeDomain = runtimeDomain;
    }

    public boolean isPresent(By locator) {
        try {
            int nodeId = findNodeId(locator);
            return nodeId != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVisible(By locator) {
        try {
            int nodeId = findNodeId(locator);
            if (nodeId == 0) return false;

            JsonObject resolved = domDomain.resolveNode(nodeId).join();
            String objectId = resolved.getAsJsonObject("object").get("objectId").getAsString();

            String script = "!!(this.offsetWidth || this.offsetHeight || this.getClientRects().length)";
            JsonObject result = runtimeDomain.callFunctionOn(objectId, "function() { return " + script + "; }", null).join();
            runtimeDomain.releaseObject(objectId).join();

            JsonObject resultObj = result.getAsJsonObject("result");
            if (resultObj.has("value")) {
                return resultObj.get("value").getAsBoolean();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStable(By locator) {
        try {
            int nodeId = findNodeId(locator);
            if (nodeId == 0) return false;

            JsonObject resolved = domDomain.resolveNode(nodeId).join();
            String objectId = resolved.getAsJsonObject("object").get("objectId").getAsString();

            String script = """
                const animations = this.getAnimations ? this.getAnimations({subtree: true}) : [];
                const runningAnimations = animations.filter(a => a.playState === 'running');
                return runningAnimations.length === 0;
            """;

            JsonObject result = runtimeDomain.callFunctionOn(objectId, "function() { " + script + " }", null).join();
            runtimeDomain.releaseObject(objectId).join();

            JsonObject resultObj = result.getAsJsonObject("result");
            if (resultObj.has("value")) {
                return resultObj.get("value").getAsBoolean();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNotObscured(By locator) {
        try {
            int nodeId = findNodeId(locator);
            if (nodeId == 0) return false;

            JsonObject resolved = domDomain.resolveNode(nodeId).join();
            String objectId = resolved.getAsJsonObject("object").get("objectId").getAsString();

            String script = """
                const rect = this.getBoundingClientRect();
                if (rect.width === 0 || rect.height === 0) return false;

                const x = rect.left + rect.width / 2;
                const y = rect.top + rect.height / 2;

                if (x < 0 || y < 0) return false;

                const el = document.elementFromPoint(x, y);
                return el === this || this.contains(el);
            """;

            JsonObject result = runtimeDomain.callFunctionOn(objectId, "function() { " + script + " }", null).join();
            runtimeDomain.releaseObject(objectId).join();

            JsonObject resultObj = result.getAsJsonObject("result");
            if (resultObj.has("value")) {
                return resultObj.get("value").getAsBoolean();
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isClickable(By locator) {
        try {
            if (!isVisible(locator)) return false;
            if (!isStable(locator)) return false;
            if (!isNotObscured(locator)) return false;

            int nodeId = findNodeId(locator);
            JsonObject resolved = domDomain.resolveNode(nodeId).join();
            String objectId = resolved.getAsJsonObject("object").get("objectId").getAsString();

            String script = "!this.disabled && this.offsetParent !== null";
            JsonObject result = runtimeDomain.callFunctionOn(objectId, "function() { return " + script + "; }", null).join();
            runtimeDomain.releaseObject(objectId).join();

            JsonObject resultObj = result.getAsJsonObject("result");
            if (resultObj.has("value")) {
                return resultObj.get("value").getAsBoolean();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEditable(By locator) {
        try {
            if (!isVisible(locator)) return false;

            int nodeId = findNodeId(locator);
            JsonObject resolved = domDomain.resolveNode(nodeId).join();
            String objectId = resolved.getAsJsonObject("object").get("objectId").getAsString();

            String script = """
                const tagName = this.tagName.toLowerCase();
                const isInput = tagName === 'input' || tagName === 'textarea';
                const isContentEditable = this.isContentEditable;
                const isNotReadonly = !this.readOnly;
                const isNotDisabled = !this.disabled;

                return (isInput || isContentEditable) && isNotReadonly && isNotDisabled;
            """;

            JsonObject result = runtimeDomain.callFunctionOn(objectId, "function() { " + script + " }", null).join();
            runtimeDomain.releaseObject(objectId).join();

            JsonObject resultObj = result.getAsJsonObject("result");
            if (resultObj.has("value")) {
                return resultObj.get("value").getAsBoolean();
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private int findNodeId(By locator) {
        try {
            JsonObject docResult = domDomain.getDocument().join();
            JsonObject root = docResult.getAsJsonObject("root");
            int documentNodeId = root.get("nodeId").getAsInt();

            String cssSelector = locator.toCssSelector();

            if (cssSelector != null) {
                JsonObject result = domDomain.querySelector(documentNodeId, cssSelector).join();
                return result.get("nodeId").getAsInt();
            } else if (locator.isXPath()) {
                return findNodeIdByXPath(locator.getSelector());
            } else {
                throw new UnsupportedOperationException("Unsupported locator type");
            }
        } catch (Exception e) {
            return 0;
        }
    }

    private int findNodeIdByXPath(String xpath) {
        try {
            String escapedXPath = xpath.replace("'", "\\'");
            String script = String.format(
                    "document.evaluate('%s', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue",
                    escapedXPath
            );

            JsonObject result = runtimeDomain.evaluate(script, false).join();
            JsonObject resultObj = result.getAsJsonObject("result");

            if (resultObj.get("type").getAsString().equals("object") && resultObj.has("objectId")) {
                String objectId = resultObj.get("objectId").getAsString();
                JsonObject nodeResult = domDomain.requestNode(objectId).join();
                int nodeId = nodeResult.get("nodeId").getAsInt();
                runtimeDomain.releaseObject(objectId).join();
                return nodeId;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private String getCssProperty(JsonArray computedStyle, String propertyName) {
        for (JsonElement element : computedStyle) {
            JsonObject prop = element.getAsJsonObject();
            if (prop.get("name").getAsString().equals(propertyName)) {
                return prop.get("value").getAsString();
            }
        }
        return "";
    }
}
