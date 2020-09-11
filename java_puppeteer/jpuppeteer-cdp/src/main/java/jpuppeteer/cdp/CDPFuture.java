package jpuppeteer.cdp;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CDPFuture<V> implements Future<V> {

    private final Future<JSONObject> future;

    private final Class<V> clazz;

    public CDPFuture(Future<JSONObject> future, Class<V> clazz) {
        this.future = future;
        this.clazz = clazz;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return future.get().toJavaObject(clazz);
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit).toJavaObject(clazz);
    }
}
