package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Performance {

    private jpuppeteer.cdp.CDPSession session;

    public Performance(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disable collecting and reporting metrics.
    */
    public void disable(int timeout) throws Exception {
        session.send("Performance.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Performance.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable collecting and reporting metrics.
    */
    public void enable(int timeout) throws Exception {
        session.send("Performance.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Performance.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets time domain to use for collecting and reporting duration metrics. Note that this must be called before enabling metrics collection. Calling this method while metrics collection is enabled returns an error.
    */
    public void setTimeDomain(jpuppeteer.cdp.cdp.entity.performance.SetTimeDomainRequest request, int timeout) throws Exception {
        session.send("Performance.setTimeDomain", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetTimeDomain(jpuppeteer.cdp.cdp.entity.performance.SetTimeDomainRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Performance.setTimeDomain", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Retrieve current values of run-time metrics.
    */
    public jpuppeteer.cdp.cdp.entity.performance.GetMetricsResponse getMetrics(int timeout) throws Exception {
        return session.send("Performance.getMetrics", null, jpuppeteer.cdp.cdp.entity.performance.GetMetricsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.performance.GetMetricsResponse> asyncGetMetrics() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Performance.getMetrics");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.performance.GetMetricsResponse>(future, jpuppeteer.cdp.cdp.entity.performance.GetMetricsResponse.class);
    }
}