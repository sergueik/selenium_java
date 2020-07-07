package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class DOMStorage {

    private jpuppeteer.cdp.CDPSession session;

    public DOMStorage(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * experimental
    */
    public void clear(jpuppeteer.cdp.cdp.entity.domstorage.ClearRequest request, int timeout) throws Exception {
        session.send("DOMStorage.clear", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClear(jpuppeteer.cdp.cdp.entity.domstorage.ClearRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMStorage.clear", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables storage tracking, prevents storage events from being sent to the client.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("DOMStorage.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMStorage.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables storage tracking, storage events will now be delivered to the client.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("DOMStorage.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMStorage.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsResponse getDOMStorageItems(jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsRequest request, int timeout) throws Exception {
        return session.send("DOMStorage.getDOMStorageItems", request, jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsResponse> asyncGetDOMStorageItems(jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMStorage.getDOMStorageItems", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsResponse>(future, jpuppeteer.cdp.cdp.entity.domstorage.GetDOMStorageItemsResponse.class);
    }

    /**
    * experimental
    */
    public void removeDOMStorageItem(jpuppeteer.cdp.cdp.entity.domstorage.RemoveDOMStorageItemRequest request, int timeout) throws Exception {
        session.send("DOMStorage.removeDOMStorageItem", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveDOMStorageItem(jpuppeteer.cdp.cdp.entity.domstorage.RemoveDOMStorageItemRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMStorage.removeDOMStorageItem", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void setDOMStorageItem(jpuppeteer.cdp.cdp.entity.domstorage.SetDOMStorageItemRequest request, int timeout) throws Exception {
        session.send("DOMStorage.setDOMStorageItem", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDOMStorageItem(jpuppeteer.cdp.cdp.entity.domstorage.SetDOMStorageItemRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMStorage.setDOMStorageItem", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}