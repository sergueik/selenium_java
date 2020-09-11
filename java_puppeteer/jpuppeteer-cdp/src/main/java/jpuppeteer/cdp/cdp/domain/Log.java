package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Log {

    private jpuppeteer.cdp.CDPSession session;

    public Log(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Clears the log.
    */
    public void clear(int timeout) throws Exception {
        session.send("Log.clear", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClear() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Log.clear");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables log domain, prevents further log entries from being reported to the client.
    */
    public void disable(int timeout) throws Exception {
        session.send("Log.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Log.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables log domain, sends the entries collected so far to the client by means of the `entryAdded` notification.
    */
    public void enable(int timeout) throws Exception {
        session.send("Log.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Log.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * start violation reporting.
    */
    public void startViolationsReport(jpuppeteer.cdp.cdp.entity.log.StartViolationsReportRequest request, int timeout) throws Exception {
        session.send("Log.startViolationsReport", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartViolationsReport(jpuppeteer.cdp.cdp.entity.log.StartViolationsReportRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Log.startViolationsReport", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Stop violation reporting.
    */
    public void stopViolationsReport(int timeout) throws Exception {
        session.send("Log.stopViolationsReport", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopViolationsReport() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Log.stopViolationsReport");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}