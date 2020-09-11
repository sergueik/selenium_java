package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Accessibility {

    private jpuppeteer.cdp.CDPSession session;

    public Accessibility(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables the accessibility domain.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Accessibility.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Accessibility.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables the accessibility domain which causes `AXNodeId`s to remain consistent between method calls. This turns on accessibility for the page, which can impact performance until accessibility is disabled.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("Accessibility.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Accessibility.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Fetches the accessibility node and partial accessibility tree for this DOM node, if it exists.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeResponse getPartialAXTree(jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeRequest request, int timeout) throws Exception {
        return session.send("Accessibility.getPartialAXTree", request, jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeResponse> asyncGetPartialAXTree(jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Accessibility.getPartialAXTree", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeResponse>(future, jpuppeteer.cdp.cdp.entity.accessibility.GetPartialAXTreeResponse.class);
    }

    /**
    * Fetches the entire accessibility tree
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.accessibility.GetFullAXTreeResponse getFullAXTree(int timeout) throws Exception {
        return session.send("Accessibility.getFullAXTree", null, jpuppeteer.cdp.cdp.entity.accessibility.GetFullAXTreeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.accessibility.GetFullAXTreeResponse> asyncGetFullAXTree() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Accessibility.getFullAXTree");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.accessibility.GetFullAXTreeResponse>(future, jpuppeteer.cdp.cdp.entity.accessibility.GetFullAXTreeResponse.class);
    }
}