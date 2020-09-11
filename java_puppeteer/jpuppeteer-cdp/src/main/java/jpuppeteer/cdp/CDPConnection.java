package jpuppeteer.cdp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.MapMaker;
import com.google.common.util.concurrent.SettableFuture;
import jpuppeteer.api.event.AbstractEventEmitter;
import jpuppeteer.api.event.AbstractListener;
import jpuppeteer.cdp.cdp.CDPEventType;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class CDPConnection extends AbstractEventEmitter<CDPEvent> {

    private static final Logger logger = LoggerFactory.getLogger(CDPConnection.class);

    protected static final String ID = "id";

    protected static final String METHOD = "method";

    protected static final String PARAMS = "params";

    protected static final String ERROR = "error";

    protected static final String RESULT = "result";

    private final ExecutorService executors;

    private final AtomicInteger messageId;

    protected final Map<Integer, SettableFuture<JSONObject>> requestMap;

    protected CDPConnection(String name) {
        this.executors = Executors.newSingleThreadExecutor(r -> new Thread(r, "Connection["+name+"]"));
        this.messageId = new AtomicInteger(0);
        this.requestMap = new MapMaker().weakValues().concurrencyLevel(16).makeMap();
    }

    @Override
    protected void emitInternal(AbstractListener<CDPEvent> listener, CDPEvent event) {
        executors.submit(() -> listener.accept(event));
    }

    private JSONObject sendBase(String method, Object params, Map<String, Object> extra, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        return send0(method, params, extra).get(timeout, TimeUnit.SECONDS);
    }

    private Future<JSONObject> send0(String method, Object params, Map<String, Object> extra) {
        JSONObject json = new JSONObject();
        int id = messageId.getAndIncrement();
        //避免extra中的内容会覆盖ID, METHOD, PARAMS, 所以在前面先put进去
        if (MapUtils.isNotEmpty(extra)) {
            json.putAll(extra);
        }
        json.put(ID, id);
        json.put(METHOD, method);
        json.put(PARAMS, params);
        SettableFuture<JSONObject> future = SettableFuture.create();
        requestMap.put(id, future);
        if (logger.isDebugEnabled()) {
            logger.debug("==> send method={}, id={}, extra={}, params={}", method, id, JSON.toJSONString(extra), JSON.toJSONString(params));
        }
        try {
            sendInternal(json);
        } catch (IOException ioe) {
            //发送请求的过程中发生异常, 需要remove
            requestMap.remove(id);
            future.setException(ioe);
            logger.error("internal send error, error={}", ioe.getMessage(), ioe);
        }
        return future;
    }

    public final <T> T send(String method, Object params, Map<String, Object> extra, Class<T> clazz, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        JSONObject result = sendBase(method, params, extra, timeout);
        return JSONObject.class.equals(clazz) ? (T) result : result.toJavaObject(clazz);
    }

    public final <T> T send(String method, Object params, Map<String, Object> extra, TypeReference<T> type, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        JSONObject result = sendBase(method, params, extra, timeout);
        return type.getType().equals(JSONObject.class) ? (T) result : result.toJavaObject(type);
    }

    public final void send(String method, Object params, Map<String, Object> extra, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        sendBase(method, params, extra, timeout);
    }

    public final <T> T send(String method, Object params, Class<T> clazz, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        JSONObject result = sendBase(method, params, null, timeout);
        return JSONObject.class.equals(clazz) ? (T) result : result.toJavaObject(clazz);
    }

    public final <T> T send(String method, Object params, TypeReference<T> type, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        JSONObject result = sendBase(method, params, null, timeout);
        return type.getType().equals(JSONObject.class) ? (T) result : result.toJavaObject(type);
    }

    public final void send(String method, Object params, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        sendBase(method, params, null, timeout);
    }

    public final Future<JSONObject> asyncSend(String method) {
        return send0(method, null, null);
    }

    public final Future<JSONObject> asyncSend(String method, Object params) {
        return send0(method, params, null);
    }

    public final Future<JSONObject> asyncSend(String method, Object params, Map<String, Object> extra) {
        return send0(method, params, extra);
    }

    private void handleEvent(JSONObject json) {
        String method = json.getString(METHOD);
        CDPEventType eventType = CDPEventType.findByName(method);
        if (eventType == null) {
            logger.error("discard unknown event [{}]", method);
            return;
        }
        emit(new CDPEvent(
                json.getString(CDPSession.SESSION_ID),
                eventType,
                json.getJSONObject(PARAMS)));
    }

    protected void recv(String message) {
        logger.debug("<== recv message={}", message);
        JSONObject json = JSON.parseObject(message);
        if (json.containsKey(METHOD)) {
            //这种格式的就是event notification
            handleEvent(json);
            return;
        }
        if (!json.containsKey(ID)) {
            //没有ID就是异常的返回
            throw new IllegalArgumentException("attribute \"id\" can not be null");
        }
        Integer id = json.getInteger(ID);
        SettableFuture<JSONObject> future = requestMap.get(id);
        if (future == null) {
            logger.warn("request timeout or send failed, id={}", id);
            return;
        }
        //接收成功之后删除requestMap中对应的节点
        requestMap.remove(id);
        if (json.containsKey(ERROR)) {
            JSONObject error = json.getJSONObject(ERROR);
            future.setException(new CDPException(error.getIntValue("code"), error.getString("message")));
        } else {
            future.set(json.getJSONObject(RESULT));
        }
    }

    public void close() {
        this.executors.shutdown();
    }

    public abstract void open() throws IOException, InterruptedException;

    protected abstract void sendInternal(JSONObject request) throws IOException;

}
