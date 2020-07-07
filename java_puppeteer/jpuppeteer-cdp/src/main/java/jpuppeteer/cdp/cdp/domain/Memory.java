package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Memory {

    private jpuppeteer.cdp.CDPSession session;

    public Memory(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.memory.GetDOMCountersResponse getDOMCounters(int timeout) throws Exception {
        return session.send("Memory.getDOMCounters", null, jpuppeteer.cdp.cdp.entity.memory.GetDOMCountersResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.memory.GetDOMCountersResponse> asyncGetDOMCounters() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.getDOMCounters");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.memory.GetDOMCountersResponse>(future, jpuppeteer.cdp.cdp.entity.memory.GetDOMCountersResponse.class);
    }

    /**
    * experimental
    */
    public void prepareForLeakDetection(int timeout) throws Exception {
        session.send("Memory.prepareForLeakDetection", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncPrepareForLeakDetection() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.prepareForLeakDetection");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Simulate OomIntervention by purging V8 memory.
    * experimental
    */
    public void forciblyPurgeJavaScriptMemory(int timeout) throws Exception {
        session.send("Memory.forciblyPurgeJavaScriptMemory", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncForciblyPurgeJavaScriptMemory() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.forciblyPurgeJavaScriptMemory");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable/disable suppressing memory pressure notifications in all processes.
    * experimental
    */
    public void setPressureNotificationsSuppressed(jpuppeteer.cdp.cdp.entity.memory.SetPressureNotificationsSuppressedRequest request, int timeout) throws Exception {
        session.send("Memory.setPressureNotificationsSuppressed", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPressureNotificationsSuppressed(jpuppeteer.cdp.cdp.entity.memory.SetPressureNotificationsSuppressedRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.setPressureNotificationsSuppressed", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Simulate a memory pressure notification in all processes.
    * experimental
    */
    public void simulatePressureNotification(jpuppeteer.cdp.cdp.entity.memory.SimulatePressureNotificationRequest request, int timeout) throws Exception {
        session.send("Memory.simulatePressureNotification", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSimulatePressureNotification(jpuppeteer.cdp.cdp.entity.memory.SimulatePressureNotificationRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.simulatePressureNotification", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Start collecting native memory profile.
    * experimental
    */
    public void startSampling(jpuppeteer.cdp.cdp.entity.memory.StartSamplingRequest request, int timeout) throws Exception {
        session.send("Memory.startSampling", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartSampling(jpuppeteer.cdp.cdp.entity.memory.StartSamplingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.startSampling", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Stop collecting native memory profile.
    * experimental
    */
    public void stopSampling(int timeout) throws Exception {
        session.send("Memory.stopSampling", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopSampling() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.stopSampling");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Retrieve native memory allocations profile collected since renderer process startup.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.memory.GetAllTimeSamplingProfileResponse getAllTimeSamplingProfile(int timeout) throws Exception {
        return session.send("Memory.getAllTimeSamplingProfile", null, jpuppeteer.cdp.cdp.entity.memory.GetAllTimeSamplingProfileResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.memory.GetAllTimeSamplingProfileResponse> asyncGetAllTimeSamplingProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.getAllTimeSamplingProfile");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.memory.GetAllTimeSamplingProfileResponse>(future, jpuppeteer.cdp.cdp.entity.memory.GetAllTimeSamplingProfileResponse.class);
    }

    /**
    * Retrieve native memory allocations profile collected since browser process startup.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.memory.GetBrowserSamplingProfileResponse getBrowserSamplingProfile(int timeout) throws Exception {
        return session.send("Memory.getBrowserSamplingProfile", null, jpuppeteer.cdp.cdp.entity.memory.GetBrowserSamplingProfileResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.memory.GetBrowserSamplingProfileResponse> asyncGetBrowserSamplingProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.getBrowserSamplingProfile");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.memory.GetBrowserSamplingProfileResponse>(future, jpuppeteer.cdp.cdp.entity.memory.GetBrowserSamplingProfileResponse.class);
    }

    /**
    * Retrieve native memory allocations profile collected since last `startSampling` call.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.memory.GetSamplingProfileResponse getSamplingProfile(int timeout) throws Exception {
        return session.send("Memory.getSamplingProfile", null, jpuppeteer.cdp.cdp.entity.memory.GetSamplingProfileResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.memory.GetSamplingProfileResponse> asyncGetSamplingProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Memory.getSamplingProfile");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.memory.GetSamplingProfileResponse>(future, jpuppeteer.cdp.cdp.entity.memory.GetSamplingProfileResponse.class);
    }
}