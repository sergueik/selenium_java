package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Database {

    private jpuppeteer.cdp.CDPSession session;

    public Database(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables database tracking, prevents database events from being sent to the client.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Database.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Database.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables database tracking, database events will now be delivered to the client.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("Database.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Database.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.database.ExecuteSQLResponse executeSQL(jpuppeteer.cdp.cdp.entity.database.ExecuteSQLRequest request, int timeout) throws Exception {
        return session.send("Database.executeSQL", request, jpuppeteer.cdp.cdp.entity.database.ExecuteSQLResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.database.ExecuteSQLResponse> asyncExecuteSQL(jpuppeteer.cdp.cdp.entity.database.ExecuteSQLRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Database.executeSQL", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.database.ExecuteSQLResponse>(future, jpuppeteer.cdp.cdp.entity.database.ExecuteSQLResponse.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesResponse getDatabaseTableNames(jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesRequest request, int timeout) throws Exception {
        return session.send("Database.getDatabaseTableNames", request, jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesResponse> asyncGetDatabaseTableNames(jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Database.getDatabaseTableNames", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesResponse>(future, jpuppeteer.cdp.cdp.entity.database.GetDatabaseTableNamesResponse.class);
    }
}