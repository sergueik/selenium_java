package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class IndexedDB {

    private jpuppeteer.cdp.CDPSession session;

    public IndexedDB(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Clears all entries from an object store.
    * experimental
    */
    public void clearObjectStore(jpuppeteer.cdp.cdp.entity.indexeddb.ClearObjectStoreRequest request, int timeout) throws Exception {
        session.send("IndexedDB.clearObjectStore", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearObjectStore(jpuppeteer.cdp.cdp.entity.indexeddb.ClearObjectStoreRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.clearObjectStore", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Deletes a database.
    * experimental
    */
    public void deleteDatabase(jpuppeteer.cdp.cdp.entity.indexeddb.DeleteDatabaseRequest request, int timeout) throws Exception {
        session.send("IndexedDB.deleteDatabase", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeleteDatabase(jpuppeteer.cdp.cdp.entity.indexeddb.DeleteDatabaseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.deleteDatabase", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Delete a range of entries from an object store
    * experimental
    */
    public void deleteObjectStoreEntries(jpuppeteer.cdp.cdp.entity.indexeddb.DeleteObjectStoreEntriesRequest request, int timeout) throws Exception {
        session.send("IndexedDB.deleteObjectStoreEntries", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeleteObjectStoreEntries(jpuppeteer.cdp.cdp.entity.indexeddb.DeleteObjectStoreEntriesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.deleteObjectStoreEntries", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables events from backend.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("IndexedDB.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables events from backend.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("IndexedDB.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests data from object store or index.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataResponse requestData(jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataRequest request, int timeout) throws Exception {
        return session.send("IndexedDB.requestData", request, jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataResponse> asyncRequestData(jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.requestData", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataResponse>(future, jpuppeteer.cdp.cdp.entity.indexeddb.RequestDataResponse.class);
    }

    /**
    * Gets metadata of an object store
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataResponse getMetadata(jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataRequest request, int timeout) throws Exception {
        return session.send("IndexedDB.getMetadata", request, jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataResponse> asyncGetMetadata(jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.getMetadata", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataResponse>(future, jpuppeteer.cdp.cdp.entity.indexeddb.GetMetadataResponse.class);
    }

    /**
    * Requests database with given name in given frame.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseResponse requestDatabase(jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseRequest request, int timeout) throws Exception {
        return session.send("IndexedDB.requestDatabase", request, jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseResponse> asyncRequestDatabase(jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.requestDatabase", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseResponse>(future, jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseResponse.class);
    }

    /**
    * Requests database names for given security origin.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesResponse requestDatabaseNames(jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesRequest request, int timeout) throws Exception {
        return session.send("IndexedDB.requestDatabaseNames", request, jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesResponse> asyncRequestDatabaseNames(jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("IndexedDB.requestDatabaseNames", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesResponse>(future, jpuppeteer.cdp.cdp.entity.indexeddb.RequestDatabaseNamesResponse.class);
    }
}