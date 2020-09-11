package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class ServiceWorker {

    private jpuppeteer.cdp.CDPSession session;

    public ServiceWorker(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * experimental
    */
    public void deliverPushMessage(jpuppeteer.cdp.cdp.entity.serviceworker.DeliverPushMessageRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.deliverPushMessage", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeliverPushMessage(jpuppeteer.cdp.cdp.entity.serviceworker.DeliverPushMessageRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.deliverPushMessage", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("ServiceWorker.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void dispatchSyncEvent(jpuppeteer.cdp.cdp.entity.serviceworker.DispatchSyncEventRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.dispatchSyncEvent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDispatchSyncEvent(jpuppeteer.cdp.cdp.entity.serviceworker.DispatchSyncEventRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.dispatchSyncEvent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void dispatchPeriodicSyncEvent(jpuppeteer.cdp.cdp.entity.serviceworker.DispatchPeriodicSyncEventRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.dispatchPeriodicSyncEvent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDispatchPeriodicSyncEvent(jpuppeteer.cdp.cdp.entity.serviceworker.DispatchPeriodicSyncEventRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.dispatchPeriodicSyncEvent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("ServiceWorker.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void inspectWorker(jpuppeteer.cdp.cdp.entity.serviceworker.InspectWorkerRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.inspectWorker", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncInspectWorker(jpuppeteer.cdp.cdp.entity.serviceworker.InspectWorkerRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.inspectWorker", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void setForceUpdateOnPageLoad(jpuppeteer.cdp.cdp.entity.serviceworker.SetForceUpdateOnPageLoadRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.setForceUpdateOnPageLoad", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetForceUpdateOnPageLoad(jpuppeteer.cdp.cdp.entity.serviceworker.SetForceUpdateOnPageLoadRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.setForceUpdateOnPageLoad", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void skipWaiting(jpuppeteer.cdp.cdp.entity.serviceworker.SkipWaitingRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.skipWaiting", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSkipWaiting(jpuppeteer.cdp.cdp.entity.serviceworker.SkipWaitingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.skipWaiting", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void startWorker(jpuppeteer.cdp.cdp.entity.serviceworker.StartWorkerRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.startWorker", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartWorker(jpuppeteer.cdp.cdp.entity.serviceworker.StartWorkerRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.startWorker", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void stopAllWorkers(int timeout) throws Exception {
        session.send("ServiceWorker.stopAllWorkers", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopAllWorkers() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.stopAllWorkers");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void stopWorker(jpuppeteer.cdp.cdp.entity.serviceworker.StopWorkerRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.stopWorker", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopWorker(jpuppeteer.cdp.cdp.entity.serviceworker.StopWorkerRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.stopWorker", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void unregister(jpuppeteer.cdp.cdp.entity.serviceworker.UnregisterRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.unregister", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncUnregister(jpuppeteer.cdp.cdp.entity.serviceworker.UnregisterRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.unregister", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void updateRegistration(jpuppeteer.cdp.cdp.entity.serviceworker.UpdateRegistrationRequest request, int timeout) throws Exception {
        session.send("ServiceWorker.updateRegistration", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncUpdateRegistration(jpuppeteer.cdp.cdp.entity.serviceworker.UpdateRegistrationRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ServiceWorker.updateRegistration", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}