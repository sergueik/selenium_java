package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class WebAudio {

    private jpuppeteer.cdp.CDPSession session;

    public WebAudio(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Enables the WebAudio domain and starts sending context lifetime events.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("WebAudio.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAudio.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables the WebAudio domain.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("WebAudio.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAudio.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Fetch the realtime data from the registered contexts.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataResponse getRealtimeData(jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataRequest request, int timeout) throws Exception {
        return session.send("WebAudio.getRealtimeData", request, jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataResponse> asyncGetRealtimeData(jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAudio.getRealtimeData", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataResponse>(future, jpuppeteer.cdp.cdp.entity.webaudio.GetRealtimeDataResponse.class);
    }
}