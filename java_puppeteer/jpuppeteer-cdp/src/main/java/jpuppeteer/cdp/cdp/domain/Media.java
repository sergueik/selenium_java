package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Media {

    private jpuppeteer.cdp.CDPSession session;

    public Media(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Enables the Media domain
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("Media.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Media.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables the Media domain.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Media.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Media.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}