/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.session;

import static com.neovisionaries.ws.client.WebSocketCloseCode.NORMAL;
import static io.webfolder.cdp.event.Events.LogEntryAdded;
import static io.webfolder.cdp.event.Events.NetworkResponseReceived;
import static io.webfolder.cdp.event.Events.PageLifecycleEvent;
import static io.webfolder.cdp.event.Events.RuntimeConsoleAPICalled;
import static io.webfolder.cdp.type.constant.ImageFormat.Png;
import static io.webfolder.cdp.type.page.ResourceType.Document;
import static io.webfolder.cdp.type.page.ResourceType.XHR;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.floor;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.ThreadLocal.withInitial;
import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Locale.ENGLISH;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;

import io.webfolder.cdp.command.Emulation;
import io.webfolder.cdp.command.Page;
import io.webfolder.cdp.event.log.EntryAdded;
import io.webfolder.cdp.event.network.ResponseReceived;
import io.webfolder.cdp.event.page.LifecycleEvent;
import io.webfolder.cdp.event.runtime.ConsoleAPICalled;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.exception.LoadTimeoutException;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.listener.TerminateEvent;
import io.webfolder.cdp.listener.TerminateListener;
import io.webfolder.cdp.logger.CdpLogger;
import io.webfolder.cdp.logger.CdpLoggerFactory;
import io.webfolder.cdp.type.css.SourceRange;
import io.webfolder.cdp.type.dom.Rect;
import io.webfolder.cdp.type.log.LogEntry;
import io.webfolder.cdp.type.network.Response;
import io.webfolder.cdp.type.page.GetLayoutMetricsResult;
import io.webfolder.cdp.type.runtime.RemoteObject;

public class Session implements AutoCloseable,
                                Selector     ,
                                Keyboard     ,
                                Mouse        ,
                                Navigator    ,
                                JavaScript   ,
                                Sizzle       ,
                                Dom          {

    private final Map<Class<?>, Object> proxies = new ConcurrentHashMap<>();

    private AtomicBoolean connected = new AtomicBoolean(true);

    private final AtomicBoolean enableSizzle = new AtomicBoolean();

    private final List<EventListener<?>> eventListeners;

    private final SessionInvocationHandler invocationHandler;

    private final SessionFactory sesessionFactory;

    private final String sessionId;

    private final WebSocket webSocket;

    private final CdpLogger log;

    private final CdpLogger logFlow;

    private final Gson gson;

    private TerminateListener terminateListener;

    private String frameId;

    private Command command;

    private final ReentrantLock lock = new ReentrantLock(true);

    private static final ThreadLocal<Boolean> ENABLE_ENTRY_EXIT_LOG = 
                                                    withInitial(() -> { return TRUE; });

    Session(
            final Gson gson,
            final String sessionId,
            final WebSocket webSocket,
            final Map<Integer, WSContext> contextList,
            final SessionFactory sessionFactory,
            final List<EventListener<?>> eventListeners,
            final CdpLoggerFactory loggerFactory) {
        this.sessionId = sessionId;
        this.invocationHandler = new SessionInvocationHandler(
                                                        gson,
                                                        webSocket,
                                                        contextList,
                                                        this,
                                                        loggerFactory.getLogger("cdp4j.ws.request"));
        this.sesessionFactory = sessionFactory;
        this.eventListeners   = eventListeners;
        this.webSocket        = webSocket;
        this.log              = loggerFactory.getLogger("cdp4j.session");
        this.logFlow          = loggerFactory.getLogger("cdp4j.flow");
        this.gson             = gson;
        this.command          = new Command(this);
    }

    public String getId() {
        return sessionId;
    }

    /**
     * Close the this browser window
     */
    @Override
    public void close() {
        logEntry("close");
        if (connected.get()) {
            try {
                sesessionFactory.close(this);
            } finally {
                connected.set(false);
            }
        }
    }

    public boolean isConnected() {
        return connected.get() && webSocket.isOpen();
    }

    /**
     * Activate this browser window
     */
    public void activate() {
        logEntry("activate");
        sesessionFactory.activate(sessionId);
    }

    /**
     * Use {@link Session#getListenerManager()}
     */
    public void addEventListener(EventListener<?> eventListener) {
        eventListeners.add(eventListener);
    }

    /**
     * Use {@link Session#getListenerManager()}
     */
    public void removeEventEventListener(EventListener<?> eventListener) {
        if (eventListener != null) {
            eventListeners.remove(eventListener);
        }
    }

    /**
     * waits until document is ready
     * 
     * @return this
     */
    public Session waitDocumentReady() {
        return waitDocumentReady(WAIT_TIMEOUT);
    }

    /**
     * waits until document is ready
     * 
     * @param timeout the maximum time to wait in milliseconds
     * 
     * @return this
     */
    public Session waitDocumentReady(final int timeout) {
        if ( ! isConnected() ) {
            return this;
        }
        long start = System.currentTimeMillis();
        logEntry("waitDocumentReady", format("[timeout=%d]", timeout));
        if (isDomReady()) {
            return getThis();
        }
        CountDownLatch latch  = new CountDownLatch(2);
        AtomicBoolean  loaded = new AtomicBoolean(false);
        AtomicBoolean  ready  = new AtomicBoolean(false);
        if (isConnected()) {
            command.getPage().enable();
            EventListener<?> loadListener = (e, d) -> {
                if (PageLifecycleEvent.equals(e) &&
                        "load".equalsIgnoreCase(((LifecycleEvent) d).getName())) {
                    latch.countDown();
                    loaded.set(true);
                    if (isDomReady()) {
                        ready.set(true);
                    }
                }
            };
            addEventListener(loadListener);
            sesessionFactory.getThreadPool().execute(() -> {
                try {
                    waitUntil(s -> ! isConnected() || s.isDomReady() || ready.get(), timeout, false);
                } finally {
                    latch.countDown();
                    if ( ! loaded.get() ) {
                        latch.countDown();
                    }
                    if ( ! ready.get() && isConnected() && isDomReady() ) {
                        ready.set(true);
                    }
                }
            });
            try {
                latch.await(timeout, MILLISECONDS);
            } catch (InterruptedException e) {
                throw new LoadTimeoutException(e);
            } finally {
                removeEventEventListener(loadListener);
            }
            long elapsed = System.currentTimeMillis() - start;
            if ( elapsed > timeout && isConnected() && ! isDomReady() ) {
                throw new LoadTimeoutException("Page not loaded within " + timeout + " ms");
            }
        }
        return this;
    }

    private boolean waitUntil(Predicate<Session> predicate, int timeout, boolean log) {
        return waitUntil(predicate, timeout, WAIT_PERIOD, log);
    }

    public boolean waitUntil(final Predicate<Session> predicate) {
        return waitUntil(predicate, WAIT_TIMEOUT, WAIT_PERIOD);
    }

    public boolean waitUntil(final Predicate<Session> predicate, final int timeout) {
        return waitUntil(predicate, timeout, WAIT_PERIOD, true);
    }

    public boolean waitUntil(
            final Predicate<Session> predicate,
            final int timeout,
            final int period) {
        return waitUntil(predicate, timeout, period, true);
    }

    public boolean waitUntil(
                    final Predicate<Session> predicate,
                    final int timeout,
                    final int period,
                    final boolean log) {
        final int count = (int) floor(timeout / period);
        for (int i = 0; i < count; i++) {
            final boolean wakeup = predicate.test(getThis());
            if (wakeup) {
                return true;
            } else {
                if ( ! isConnected() ) {
                    return false;
                } else {
                    wait(period, log);
                }
            }
        }
        return false;
    }

    public Session navigate(final String url) {
        logEntry("navigate", url);
        command.getInspector().enable();
        this.frameId = command.getPage().navigate(url);
        return this;
    }

    /**
     * Redirects javascript console logs to slf4j
     * 
     * @return this
     */
    public Session enableConsoleLog() {
        getCommand().getRuntime().enable();
        addEventListener((e, d) -> {
            if (RuntimeConsoleAPICalled.equals(e)) {
                ConsoleAPICalled ca = (ConsoleAPICalled) d;
                for (RemoteObject next : ca.getArgs()) {
                    Object value = next.getValue();
                    String type = ca.getType().toString().toUpperCase(ENGLISH);
                    switch (ca.getType()) {
                        case Log    :
                        case Info   : log.info("[console] [{}] {}", new Object[] { type, valueOf(value) }); break;
                        case Error  : log.info("[console] [{}] {}", new Object[] { type, valueOf(value) }); break;
                        case Warning: log.info("[console] [{}] {}", new Object[] { type, valueOf(value) }); break;
                        default: break;
                    }
                }
            }
        });
        return getThis();
    }

    /**
     * Redirects runtime logs (network, security, storage etc..) to slf4j
     * 
     * @return this
     */
    public Session enableDetailLog() {
        getCommand().getLog().enable();
        addEventListener((e, d) -> {
            if (LogEntryAdded.equals(e)) {
                EntryAdded entryAdded = (EntryAdded) d;
                LogEntry entry = entryAdded.getEntry();
                String level = entry.getLevel().toString().toUpperCase(ENGLISH);
                switch (entry.getLevel()) {
                    case Verbose: log.info("[{}] [{}] {}", entry.getSource(), level, entry.getText()); break;
                    case Info   : log.info("[{}] [{}] {}", entry.getSource(), level, entry.getText()); break;
                    case Warning: log.info("[{}] [{}] {}", entry.getSource(), level, entry.getText()); break;
                    case Error  : log.info("[{}] [{}] {}", entry.getSource(), level, entry.getText()); break;
                }
            }
        });
        return getThis();
    }

    /**
     * Redirects network logs to slf4j
     * 
     * @return this
     */
    public Session enableNetworkLog() {
        getCommand().getNetwork().enable();
        addEventListener((e, d) -> {
            if (NetworkResponseReceived.equals(e)) {
                ResponseReceived rr = (ResponseReceived) d;
                Response         response = rr.getResponse();
                final String     url      = response.getUrl();
                final int        status   = response.getStatus().intValue();
                final String     mimeType = response.getMimeType();
                if (Document.equals(rr.getType()) || XHR.equals(rr.getType())) {
                    log.info("[{}] [{}] [{}] [{}] [{}]", new Object[] {
                        rr.getType().toString().toUpperCase(ENGLISH),
                        rr.getResponse().getProtocol().toUpperCase(ENGLISH),
                        status,
                        mimeType,
                        url
                    });
                }
            }
        });
        return getThis();
    }

    public Session getThis() {
        return this;
    }

    public String getFrameId() {
        return frameId;
    }

    public byte[] captureScreenshot() {
        SourceRange location = new SourceRange();
        location.setEndColumn(0);
        location.setEndLine(0);
        location.setStartColumn(0);
        location.setStartLine(0);
        Page page = getThis().getCommand().getPage();
        GetLayoutMetricsResult metrics = page.getLayoutMetrics();
        Rect cs = metrics.getContentSize();
        Emulation emulation = getThis().getCommand().getEmulation();
        emulation.setDeviceMetricsOverride(cs.getWidth().intValue(), cs.getHeight().intValue(), 1D, false);
        byte[] data = page.captureScreenshot(Png, null, null, true);        
        emulation.clearDeviceMetricsOverride();
        emulation.resetPageScaleFactor();
        return data;
    }

    /**
     * Causes the current thread to wait until waiting time elapses.
     * 
     * @param timeout the maximum time to wait in milliseconds
     * 
     * @throws CdpException if the session held by another thread at the time of invocation.
     * 
     * @return this
     */
    public Session wait(int timeout) {
        return wait(timeout, true);
    }

    /**
     * Causes the current thread to wait until waiting time elapses.
     * 
     * @param timeout the maximum time to wait in milliseconds
     * 
     * @throws CdpException if the session held by another thread at the time of invocation.
     * 
     * @return this
     */
    public Session wait(int timeout, boolean log) {
        if (lock.tryLock()) {
            Condition condition = lock.newCondition();
            try {
                if (log) {
                    logEntry("wait", timeout + "ms");
                }
                condition.await(timeout, MILLISECONDS);
            } catch (InterruptedException e) {
                throw new CdpException(e);
            } finally {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        } else {
            throw new CdpException("Unable to acquire lock");
        }
        return getThis();
    }

    public void onTerminate(TerminateListener terminateListener) {
        this.terminateListener = terminateListener;
    }


    public Command getCommand() {
        return command;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Session other = (Session) obj;
        if (sessionId == null) {
            if (other.sessionId != null)
                return false;
        } else if (!sessionId.equals(other.sessionId))
            return false;
        return true;
    }

    void dispose() {
        proxies.clear();
        eventListeners.clear();
        invocationHandler.dispose();
        try {
            webSocket.disconnect(NORMAL, null, 1000); // max wait time to close: 1 seconds
        } catch (Throwable t) {
        }
    }

    Gson getGson() {
        return gson;
    }

    void terminate(String message) {
        if (terminateListener != null) {
            terminateListener.onTerminate(new TerminateEvent(message));
        }
    }

    void info(
            final String message,
            final Object ...args) {
        log.info(message, args);
    }

    void error(final String message, final Object ...args) {
        log.error(message, args);
    }

    void logEntry(final String method) {
        logEntry(method, null);
    }

    void logEntry(
            final String method,
            final String args) {
        if ( ! ENABLE_ENTRY_EXIT_LOG.get() ) {
            return;
        }
        boolean hasArgs = args != null ? true : false;
        logFlow.info("{}({}{}{})", new Object[] {
            method,
            hasArgs ? "\"" : "",
            hasArgs ? args : "",
            hasArgs ? "\"" : ""
        });
    }

    void logExit(
            final String method,
            final Object retValue) {
        logExit(method, null, retValue);
    }

    void logExit(
            final String method,
            final String args,
            final Object retValue) {
        if ( ! ENABLE_ENTRY_EXIT_LOG.get() ) {
            return;
        }
        boolean hasArgs = args != null ? true : false;
        logFlow.info("{}({}{}{}): {}", new Object[] {
            method,
            hasArgs ? "\"" : "",
            hasArgs ? args : "",
            hasArgs ? "\"" : "",
            retValue
        });
    }

    void setSizzle(final boolean enable) {
        this.enableSizzle.set(enable);
    }

    boolean getSizzle() {
        return this.enableSizzle.get();
    }

    @SuppressWarnings("unchecked")
    <T> T getProxy(Class<T> klass) {
        T proxy = (T) proxies.get(klass);
        if (proxy != null) {
            return (T) proxy;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        Class<T>[] interfaces = new Class[] { klass };
        proxy = (T) newProxyInstance(classLoader, interfaces, invocationHandler);
        Object existing = proxies.putIfAbsent(klass, proxy);
        if (existing != null) {
            return (T) existing;
        }
        return proxy;
    }

    void disableFlowLog() {
        ENABLE_ENTRY_EXIT_LOG.set(FALSE);
    }

    void enableFlowLog() {
        ENABLE_ENTRY_EXIT_LOG.set(TRUE);
    }

    boolean isPrimitive(Class<?> klass) {
        if (String.class.equals(klass)) {
            return true;
        } else if (boolean.class.equals(klass) || Boolean.class.equals(klass)) {
            return true;
        } else if (void.class.equals(klass) || Void.class.equals(klass)) {
            return true;
        } else if (int.class.equals(klass) || Integer.class.equals(klass)) {
            return true;
        } else if (double.class.equals(klass) || Double.class.equals(klass)) {
            return true;
        } else if (long.class.equals(klass) || Long.class.equals(klass)) {
            return true;
        } else if (float.class.equals(klass) || Float.class.equals(klass)) {
            return true;
        } else if (char.class.equals(klass) || Character.class.equals(klass)) {
            return true;
        } else if (byte.class.equals(klass) || Byte.class.equals(klass)) {
            return true;
        } else if (short.class.equals(klass) || Short.class.equals(klass)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Session [sessionId=" + sessionId + "]";
    }
}
