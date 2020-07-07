package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Tracing {

    private jpuppeteer.cdp.CDPSession session;

    public Tracing(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Stop trace events collection.
    * experimental
    */
    public void end(int timeout) throws Exception {
        session.send("Tracing.end", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnd() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tracing.end");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Gets supported tracing categories.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.tracing.GetCategoriesResponse getCategories(int timeout) throws Exception {
        return session.send("Tracing.getCategories", null, jpuppeteer.cdp.cdp.entity.tracing.GetCategoriesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.tracing.GetCategoriesResponse> asyncGetCategories() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tracing.getCategories");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.tracing.GetCategoriesResponse>(future, jpuppeteer.cdp.cdp.entity.tracing.GetCategoriesResponse.class);
    }

    /**
    * Record a clock sync marker in the trace.
    * experimental
    */
    public void recordClockSyncMarker(jpuppeteer.cdp.cdp.entity.tracing.RecordClockSyncMarkerRequest request, int timeout) throws Exception {
        session.send("Tracing.recordClockSyncMarker", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRecordClockSyncMarker(jpuppeteer.cdp.cdp.entity.tracing.RecordClockSyncMarkerRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tracing.recordClockSyncMarker", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Request a global memory dump.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpResponse requestMemoryDump(jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpRequest request, int timeout) throws Exception {
        return session.send("Tracing.requestMemoryDump", request, jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpResponse> asyncRequestMemoryDump(jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tracing.requestMemoryDump", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpResponse>(future, jpuppeteer.cdp.cdp.entity.tracing.RequestMemoryDumpResponse.class);
    }

    /**
    * Start trace events collection.
    * experimental
    */
    public void start(jpuppeteer.cdp.cdp.entity.tracing.StartRequest request, int timeout) throws Exception {
        session.send("Tracing.start", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStart(jpuppeteer.cdp.cdp.entity.tracing.StartRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tracing.start", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}