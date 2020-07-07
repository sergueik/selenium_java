package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class HeapProfiler {

    private jpuppeteer.cdp.CDPSession session;

    public HeapProfiler(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Enables console to refer to the node with given id via $x (see Command Line API for more details $x functions).
    * experimental
    */
    public void addInspectedHeapObject(jpuppeteer.cdp.cdp.entity.heapprofiler.AddInspectedHeapObjectRequest request, int timeout) throws Exception {
        session.send("HeapProfiler.addInspectedHeapObject", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncAddInspectedHeapObject(jpuppeteer.cdp.cdp.entity.heapprofiler.AddInspectedHeapObjectRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.addInspectedHeapObject", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void collectGarbage(int timeout) throws Exception {
        session.send("HeapProfiler.collectGarbage", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncCollectGarbage() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.collectGarbage");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("HeapProfiler.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("HeapProfiler.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdResponse getHeapObjectId(jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdRequest request, int timeout) throws Exception {
        return session.send("HeapProfiler.getHeapObjectId", request, jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdResponse> asyncGetHeapObjectId(jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.getHeapObjectId", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdResponse>(future, jpuppeteer.cdp.cdp.entity.heapprofiler.GetHeapObjectIdResponse.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdResponse getObjectByHeapObjectId(jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdRequest request, int timeout) throws Exception {
        return session.send("HeapProfiler.getObjectByHeapObjectId", request, jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdResponse> asyncGetObjectByHeapObjectId(jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.getObjectByHeapObjectId", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdResponse>(future, jpuppeteer.cdp.cdp.entity.heapprofiler.GetObjectByHeapObjectIdResponse.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.heapprofiler.GetSamplingProfileResponse getSamplingProfile(int timeout) throws Exception {
        return session.send("HeapProfiler.getSamplingProfile", null, jpuppeteer.cdp.cdp.entity.heapprofiler.GetSamplingProfileResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.heapprofiler.GetSamplingProfileResponse> asyncGetSamplingProfile() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.getSamplingProfile");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.heapprofiler.GetSamplingProfileResponse>(future, jpuppeteer.cdp.cdp.entity.heapprofiler.GetSamplingProfileResponse.class);
    }

    /**
    * experimental
    */
    public void startSampling(jpuppeteer.cdp.cdp.entity.heapprofiler.StartSamplingRequest request, int timeout) throws Exception {
        session.send("HeapProfiler.startSampling", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartSampling(jpuppeteer.cdp.cdp.entity.heapprofiler.StartSamplingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.startSampling", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void startTrackingHeapObjects(jpuppeteer.cdp.cdp.entity.heapprofiler.StartTrackingHeapObjectsRequest request, int timeout) throws Exception {
        session.send("HeapProfiler.startTrackingHeapObjects", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartTrackingHeapObjects(jpuppeteer.cdp.cdp.entity.heapprofiler.StartTrackingHeapObjectsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.startTrackingHeapObjects", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.heapprofiler.StopSamplingResponse stopSampling(int timeout) throws Exception {
        return session.send("HeapProfiler.stopSampling", null, jpuppeteer.cdp.cdp.entity.heapprofiler.StopSamplingResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.heapprofiler.StopSamplingResponse> asyncStopSampling() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.stopSampling");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.heapprofiler.StopSamplingResponse>(future, jpuppeteer.cdp.cdp.entity.heapprofiler.StopSamplingResponse.class);
    }

    /**
    * experimental
    */
    public void stopTrackingHeapObjects(jpuppeteer.cdp.cdp.entity.heapprofiler.StopTrackingHeapObjectsRequest request, int timeout) throws Exception {
        session.send("HeapProfiler.stopTrackingHeapObjects", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopTrackingHeapObjects(jpuppeteer.cdp.cdp.entity.heapprofiler.StopTrackingHeapObjectsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.stopTrackingHeapObjects", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void takeHeapSnapshot(jpuppeteer.cdp.cdp.entity.heapprofiler.TakeHeapSnapshotRequest request, int timeout) throws Exception {
        session.send("HeapProfiler.takeHeapSnapshot", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncTakeHeapSnapshot(jpuppeteer.cdp.cdp.entity.heapprofiler.TakeHeapSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("HeapProfiler.takeHeapSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}