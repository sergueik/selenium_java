package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class ApplicationCache {

    private jpuppeteer.cdp.CDPSession session;

    public ApplicationCache(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Enables application cache domain notifications.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("ApplicationCache.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ApplicationCache.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns relevant application cache data for the document in given frame.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameResponse getApplicationCacheForFrame(jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameRequest request, int timeout) throws Exception {
        return session.send("ApplicationCache.getApplicationCacheForFrame", request, jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameResponse> asyncGetApplicationCacheForFrame(jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ApplicationCache.getApplicationCacheForFrame", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameResponse>(future, jpuppeteer.cdp.cdp.entity.applicationcache.GetApplicationCacheForFrameResponse.class);
    }

    /**
    * Returns array of frame identifiers with manifest urls for each frame containing a document associated with some application cache.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.applicationcache.GetFramesWithManifestsResponse getFramesWithManifests(int timeout) throws Exception {
        return session.send("ApplicationCache.getFramesWithManifests", null, jpuppeteer.cdp.cdp.entity.applicationcache.GetFramesWithManifestsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.applicationcache.GetFramesWithManifestsResponse> asyncGetFramesWithManifests() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ApplicationCache.getFramesWithManifests");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.applicationcache.GetFramesWithManifestsResponse>(future, jpuppeteer.cdp.cdp.entity.applicationcache.GetFramesWithManifestsResponse.class);
    }

    /**
    * Returns manifest URL for document in the given frame.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameResponse getManifestForFrame(jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameRequest request, int timeout) throws Exception {
        return session.send("ApplicationCache.getManifestForFrame", request, jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameResponse> asyncGetManifestForFrame(jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("ApplicationCache.getManifestForFrame", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameResponse>(future, jpuppeteer.cdp.cdp.entity.applicationcache.GetManifestForFrameResponse.class);
    }
}