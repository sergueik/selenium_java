package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class HeadlessExperimental {

    private jpuppeteer.cdp.CDPSession session;

    public HeadlessExperimental(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Sends a BeginFrame to the target and returns when the frame was completed. Optionally captures a screenshot from the resulting frame. Requires that the target was created with enabled BeginFrameControl. Designed for use with --run-all-compositor-stages-before-draw, see also https://goo.gl/3zHXhB for more background.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameResponse beginFrame(jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameRequest request, int timeout) throws Exception {
        return session.send("HeadlessExperimental.beginFrame", request, jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameResponse> asyncBeginFrame(jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeadlessExperimental.beginFrame", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameResponse>(future, jpuppeteer.cdp.cdp.entity.headlessexperimental.BeginFrameResponse.class);
    }

    /**
    * Disables headless events for the target.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("HeadlessExperimental.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeadlessExperimental.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables headless events for the target.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("HeadlessExperimental.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeadlessExperimental.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}