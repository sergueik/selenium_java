package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Console {

    private jpuppeteer.cdp.CDPSession session;

    public Console(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Does nothing.
    */
    public void clearMessages(int timeout) throws Exception {
        session.send("Console.clearMessages", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearMessages() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Console.clearMessages");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables console domain, prevents further console messages from being reported to the client.
    */
    public void disable(int timeout) throws Exception {
        session.send("Console.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Console.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables console domain, sends the messages collected so far to the client by means of the `messageAdded` notification.
    */
    public void enable(int timeout) throws Exception {
        session.send("Console.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Console.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}