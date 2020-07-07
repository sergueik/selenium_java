package jpuppeteer.cdp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import jpuppeteer.cdp.constant.TargetType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class CDPSession {

    protected static final String SESSION_ID = "sessionId";

    private CDPConnection connection;

    private TargetType type;

    private String sessionId;

    private Map<String, Object> extra;

    public CDPSession(CDPConnection connection, TargetType type, String sessionId) {
        this.connection = connection;
        this.type = type;
        this.sessionId = sessionId;
        this.extra = new HashMap<>();
        this.extra.put(SESSION_ID, sessionId);
    }

    public String sessionId() {
        return sessionId;
    }

    public final <T> T send(String method, Object params, Class<T> clazz, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        return connection.send(method, params, extra, clazz, timeout);
    }

    public final <T> T send(String method, Object params, TypeReference<T> type, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        return connection.send(method, params, extra, type, timeout);
    }

    public final void send(String method, Object params, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        connection.send(method, params, extra, timeout);
    }

    public final Future<JSONObject> asyncSend(String method) {
        return connection.asyncSend(method, null, extra);
    }

    public final Future<JSONObject> asyncSend(String method, Object params) {
        return connection.asyncSend(method, params, extra);
    }
}