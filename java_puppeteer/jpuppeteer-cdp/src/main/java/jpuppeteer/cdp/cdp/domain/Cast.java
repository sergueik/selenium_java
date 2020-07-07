package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Cast {

    private jpuppeteer.cdp.CDPSession session;

    public Cast(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Starts observing for sinks that can be used for tab mirroring, and if set, sinks compatible with |presentationUrl| as well. When sinks are found, a |sinksUpdated| event is fired. Also starts observing for issue messages. When an issue is added or removed, an |issueUpdated| event is fired.
    * experimental
    */
    public void enable(jpuppeteer.cdp.cdp.entity.cast.EnableRequest request, int timeout) throws Exception {
        session.send("Cast.enable", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable(jpuppeteer.cdp.cdp.entity.cast.EnableRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Cast.enable", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Stops observing for sinks and issues.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Cast.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Cast.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets a sink to be used when the web page requests the browser to choose a sink via Presentation API, Remote Playback API, or Cast SDK.
    * experimental
    */
    public void setSinkToUse(jpuppeteer.cdp.cdp.entity.cast.SetSinkToUseRequest request, int timeout) throws Exception {
        session.send("Cast.setSinkToUse", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetSinkToUse(jpuppeteer.cdp.cdp.entity.cast.SetSinkToUseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Cast.setSinkToUse", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Starts mirroring the tab to the sink.
    * experimental
    */
    public void startTabMirroring(jpuppeteer.cdp.cdp.entity.cast.StartTabMirroringRequest request, int timeout) throws Exception {
        session.send("Cast.startTabMirroring", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartTabMirroring(jpuppeteer.cdp.cdp.entity.cast.StartTabMirroringRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Cast.startTabMirroring", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Stops the active Cast session on the sink.
    * experimental
    */
    public void stopCasting(jpuppeteer.cdp.cdp.entity.cast.StopCastingRequest request, int timeout) throws Exception {
        session.send("Cast.stopCasting", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopCasting(jpuppeteer.cdp.cdp.entity.cast.StopCastingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Cast.stopCasting", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}