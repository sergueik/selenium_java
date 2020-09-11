package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Storage {

    private jpuppeteer.cdp.CDPConnection connection;

    public Storage(jpuppeteer.cdp.CDPConnection connection) {
        this.connection = connection;
    }

    /**
    * Clears storage for origin.
    * experimental
    */
    public void clearDataForOrigin(jpuppeteer.cdp.cdp.entity.storage.ClearDataForOriginRequest request, int timeout) throws Exception {
        connection.send("Storage.clearDataForOrigin", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearDataForOrigin(jpuppeteer.cdp.cdp.entity.storage.ClearDataForOriginRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.clearDataForOrigin", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns all browser cookies.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.storage.GetCookiesResponse getCookies(jpuppeteer.cdp.cdp.entity.storage.GetCookiesRequest request, int timeout) throws Exception {
        return connection.send("Storage.getCookies", request, jpuppeteer.cdp.cdp.entity.storage.GetCookiesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.storage.GetCookiesResponse> asyncGetCookies(jpuppeteer.cdp.cdp.entity.storage.GetCookiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.getCookies", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.storage.GetCookiesResponse>(future, jpuppeteer.cdp.cdp.entity.storage.GetCookiesResponse.class);
    }

    /**
    * Sets given cookies.
    * experimental
    */
    public void setCookies(jpuppeteer.cdp.cdp.entity.storage.SetCookiesRequest request, int timeout) throws Exception {
        connection.send("Storage.setCookies", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetCookies(jpuppeteer.cdp.cdp.entity.storage.SetCookiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.setCookies", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears cookies.
    * experimental
    */
    public void clearCookies(jpuppeteer.cdp.cdp.entity.storage.ClearCookiesRequest request, int timeout) throws Exception {
        connection.send("Storage.clearCookies", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearCookies(jpuppeteer.cdp.cdp.entity.storage.ClearCookiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.clearCookies", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns usage and quota in bytes.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaResponse getUsageAndQuota(jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaRequest request, int timeout) throws Exception {
        return connection.send("Storage.getUsageAndQuota", request, jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaResponse> asyncGetUsageAndQuota(jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.getUsageAndQuota", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaResponse>(future, jpuppeteer.cdp.cdp.entity.storage.GetUsageAndQuotaResponse.class);
    }

    /**
    * Registers origin to be notified when an update occurs to its cache storage list.
    * experimental
    */
    public void trackCacheStorageForOrigin(jpuppeteer.cdp.cdp.entity.storage.TrackCacheStorageForOriginRequest request, int timeout) throws Exception {
        connection.send("Storage.trackCacheStorageForOrigin", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncTrackCacheStorageForOrigin(jpuppeteer.cdp.cdp.entity.storage.TrackCacheStorageForOriginRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.trackCacheStorageForOrigin", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Registers origin to be notified when an update occurs to its IndexedDB.
    * experimental
    */
    public void trackIndexedDBForOrigin(jpuppeteer.cdp.cdp.entity.storage.TrackIndexedDBForOriginRequest request, int timeout) throws Exception {
        connection.send("Storage.trackIndexedDBForOrigin", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncTrackIndexedDBForOrigin(jpuppeteer.cdp.cdp.entity.storage.TrackIndexedDBForOriginRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.trackIndexedDBForOrigin", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Unregisters origin from receiving notifications for cache storage.
    * experimental
    */
    public void untrackCacheStorageForOrigin(jpuppeteer.cdp.cdp.entity.storage.UntrackCacheStorageForOriginRequest request, int timeout) throws Exception {
        connection.send("Storage.untrackCacheStorageForOrigin", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncUntrackCacheStorageForOrigin(jpuppeteer.cdp.cdp.entity.storage.UntrackCacheStorageForOriginRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.untrackCacheStorageForOrigin", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Unregisters origin from receiving notifications for IndexedDB.
    * experimental
    */
    public void untrackIndexedDBForOrigin(jpuppeteer.cdp.cdp.entity.storage.UntrackIndexedDBForOriginRequest request, int timeout) throws Exception {
        connection.send("Storage.untrackIndexedDBForOrigin", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncUntrackIndexedDBForOrigin(jpuppeteer.cdp.cdp.entity.storage.UntrackIndexedDBForOriginRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Storage.untrackIndexedDBForOrigin", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}