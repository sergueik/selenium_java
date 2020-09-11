package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class BackgroundService {

    private jpuppeteer.cdp.CDPSession session;

    public BackgroundService(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Enables event updates for the service.
    * experimental
    */
    public void startObserving(jpuppeteer.cdp.cdp.entity.backgroundservice.StartObservingRequest request, int timeout) throws Exception {
        session.send("BackgroundService.startObserving", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartObserving(jpuppeteer.cdp.cdp.entity.backgroundservice.StartObservingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("BackgroundService.startObserving", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables event updates for the service.
    * experimental
    */
    public void stopObserving(jpuppeteer.cdp.cdp.entity.backgroundservice.StopObservingRequest request, int timeout) throws Exception {
        session.send("BackgroundService.stopObserving", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopObserving(jpuppeteer.cdp.cdp.entity.backgroundservice.StopObservingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("BackgroundService.stopObserving", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Set the recording state for the service.
    * experimental
    */
    public void setRecording(jpuppeteer.cdp.cdp.entity.backgroundservice.SetRecordingRequest request, int timeout) throws Exception {
        session.send("BackgroundService.setRecording", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetRecording(jpuppeteer.cdp.cdp.entity.backgroundservice.SetRecordingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("BackgroundService.setRecording", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears all stored data for the service.
    * experimental
    */
    public void clearEvents(jpuppeteer.cdp.cdp.entity.backgroundservice.ClearEventsRequest request, int timeout) throws Exception {
        session.send("BackgroundService.clearEvents", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearEvents(jpuppeteer.cdp.cdp.entity.backgroundservice.ClearEventsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("BackgroundService.clearEvents", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}