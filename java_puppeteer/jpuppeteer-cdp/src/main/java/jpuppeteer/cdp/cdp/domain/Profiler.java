package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Profiler {

    private jpuppeteer.cdp.CDPSession session;

    public Profiler(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    */
    public void disable(int timeout) throws Exception {
        session.send("Profiler.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void enable(int timeout) throws Exception {
        session.send("Profiler.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Collect coverage data for the current isolate. The coverage data may be incomplete due to garbage collection.
    */
    public jpuppeteer.cdp.cdp.entity.profiler.GetBestEffortCoverageResponse getBestEffortCoverage(int timeout) throws Exception {
        return session.send("Profiler.getBestEffortCoverage", null, jpuppeteer.cdp.cdp.entity.profiler.GetBestEffortCoverageResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.profiler.GetBestEffortCoverageResponse> asyncGetBestEffortCoverage() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.getBestEffortCoverage");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.profiler.GetBestEffortCoverageResponse>(future, jpuppeteer.cdp.cdp.entity.profiler.GetBestEffortCoverageResponse.class);
    }

    /**
    * Changes CPU profiler sampling interval. Must be called before CPU profiles recording started.
    */
    public void setSamplingInterval(jpuppeteer.cdp.cdp.entity.profiler.SetSamplingIntervalRequest request, int timeout) throws Exception {
        session.send("Profiler.setSamplingInterval", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetSamplingInterval(jpuppeteer.cdp.cdp.entity.profiler.SetSamplingIntervalRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.setSamplingInterval", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void start(int timeout) throws Exception {
        session.send("Profiler.start", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStart() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.start");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable precise code coverage. Coverage data for JavaScript executed before enabling precise code coverage may be incomplete. Enabling prevents running optimized code and resets execution counters.
    */
    public void startPreciseCoverage(jpuppeteer.cdp.cdp.entity.profiler.StartPreciseCoverageRequest request, int timeout) throws Exception {
        session.send("Profiler.startPreciseCoverage", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartPreciseCoverage(jpuppeteer.cdp.cdp.entity.profiler.StartPreciseCoverageRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.startPreciseCoverage", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable type profile.
    */
    public void startTypeProfile(int timeout) throws Exception {
        session.send("Profiler.startTypeProfile", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartTypeProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.startTypeProfile");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public jpuppeteer.cdp.cdp.entity.profiler.StopResponse stop(int timeout) throws Exception {
        return session.send("Profiler.stop", null, jpuppeteer.cdp.cdp.entity.profiler.StopResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.profiler.StopResponse> asyncStop() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.stop");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.profiler.StopResponse>(future, jpuppeteer.cdp.cdp.entity.profiler.StopResponse.class);
    }

    /**
    * Disable precise code coverage. Disabling releases unnecessary execution count records and allows executing optimized code.
    */
    public void stopPreciseCoverage(int timeout) throws Exception {
        session.send("Profiler.stopPreciseCoverage", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopPreciseCoverage() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.stopPreciseCoverage");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disable type profile. Disabling releases type profile data collected so far.
    */
    public void stopTypeProfile(int timeout) throws Exception {
        session.send("Profiler.stopTypeProfile", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopTypeProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.stopTypeProfile");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Collect coverage data for the current isolate, and resets execution counters. Precise code coverage needs to have started.
    */
    public jpuppeteer.cdp.cdp.entity.profiler.TakePreciseCoverageResponse takePreciseCoverage(int timeout) throws Exception {
        return session.send("Profiler.takePreciseCoverage", null, jpuppeteer.cdp.cdp.entity.profiler.TakePreciseCoverageResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.profiler.TakePreciseCoverageResponse> asyncTakePreciseCoverage() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.takePreciseCoverage");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.profiler.TakePreciseCoverageResponse>(future, jpuppeteer.cdp.cdp.entity.profiler.TakePreciseCoverageResponse.class);
    }

    /**
    * Collect type profile.
    */
    public jpuppeteer.cdp.cdp.entity.profiler.TakeTypeProfileResponse takeTypeProfile(int timeout) throws Exception {
        return session.send("Profiler.takeTypeProfile", null, jpuppeteer.cdp.cdp.entity.profiler.TakeTypeProfileResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.profiler.TakeTypeProfileResponse> asyncTakeTypeProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.takeTypeProfile");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.profiler.TakeTypeProfileResponse>(future, jpuppeteer.cdp.cdp.entity.profiler.TakeTypeProfileResponse.class);
    }

    /**
    * Enable run time call stats collection.
    */
    public void enableRuntimeCallStats(int timeout) throws Exception {
        session.send("Profiler.enableRuntimeCallStats", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnableRuntimeCallStats() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.enableRuntimeCallStats");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disable run time call stats collection.
    */
    public void disableRuntimeCallStats(int timeout) throws Exception {
        session.send("Profiler.disableRuntimeCallStats", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisableRuntimeCallStats() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.disableRuntimeCallStats");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Retrieve run time call stats.
    */
    public jpuppeteer.cdp.cdp.entity.profiler.GetRuntimeCallStatsResponse getRuntimeCallStats(int timeout) throws Exception {
        return session.send("Profiler.getRuntimeCallStats", null, jpuppeteer.cdp.cdp.entity.profiler.GetRuntimeCallStatsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.profiler.GetRuntimeCallStatsResponse> asyncGetRuntimeCallStats() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Profiler.getRuntimeCallStats");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.profiler.GetRuntimeCallStatsResponse>(future, jpuppeteer.cdp.cdp.entity.profiler.GetRuntimeCallStatsResponse.class);
    }
}