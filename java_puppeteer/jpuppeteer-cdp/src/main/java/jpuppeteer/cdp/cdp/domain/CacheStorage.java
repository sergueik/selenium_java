package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class CacheStorage {

    private jpuppeteer.cdp.CDPSession session;

    public CacheStorage(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Deletes a cache.
    * experimental
    */
    public void deleteCache(jpuppeteer.cdp.cdp.entity.cachestorage.DeleteCacheRequest request, int timeout) throws Exception {
        session.send("CacheStorage.deleteCache", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeleteCache(jpuppeteer.cdp.cdp.entity.cachestorage.DeleteCacheRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CacheStorage.deleteCache", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Deletes a cache entry.
    * experimental
    */
    public void deleteEntry(jpuppeteer.cdp.cdp.entity.cachestorage.DeleteEntryRequest request, int timeout) throws Exception {
        session.send("CacheStorage.deleteEntry", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeleteEntry(jpuppeteer.cdp.cdp.entity.cachestorage.DeleteEntryRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CacheStorage.deleteEntry", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests cache names.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesResponse requestCacheNames(jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesRequest request, int timeout) throws Exception {
        return session.send("CacheStorage.requestCacheNames", request, jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesResponse> asyncRequestCacheNames(jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CacheStorage.requestCacheNames", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesResponse>(future, jpuppeteer.cdp.cdp.entity.cachestorage.RequestCacheNamesResponse.class);
    }

    /**
    * Fetches cache entry.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseResponse requestCachedResponse(jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseRequest request, int timeout) throws Exception {
        return session.send("CacheStorage.requestCachedResponse", request, jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseResponse> asyncRequestCachedResponse(jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CacheStorage.requestCachedResponse", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseResponse>(future, jpuppeteer.cdp.cdp.entity.cachestorage.RequestCachedResponseResponse.class);
    }

    /**
    * Requests data from cache.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesResponse requestEntries(jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesRequest request, int timeout) throws Exception {
        return session.send("CacheStorage.requestEntries", request, jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesResponse> asyncRequestEntries(jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CacheStorage.requestEntries", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesResponse>(future, jpuppeteer.cdp.cdp.entity.cachestorage.RequestEntriesResponse.class);
    }
}