package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Inspector {

    private jpuppeteer.cdp.CDPSession session;

    public Inspector(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables inspector domain notifications.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Inspector.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Inspector.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables inspector domain notifications.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("Inspector.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Inspector.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}