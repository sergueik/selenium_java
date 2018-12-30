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

import static io.webfolder.cdp.logger.CdpLoggerType.Slf4j;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Locale.ENGLISH;
import static java.util.concurrent.Executors.newCachedThreadPool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import io.webfolder.cdp.command.Target;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.logger.CdpLogger;
import io.webfolder.cdp.logger.CdpLoggerFactory;
import io.webfolder.cdp.logger.CdpLoggerType;

public class SessionFactory implements AutoCloseable {

    public final static String DEFAULT_HOST = "localhost";

    public final static int DEFAULT_PORT = 9222;

    private final String host;

    private final int port;

    private final int connectionTimeout;

    private final WebSocketFactory factory;

    private final Gson gson;

    private final CdpLogger log;

    private final CdpLoggerFactory loggerFactory;

    private static final int DEFAULT_CONNECTION_TIMEOUT = 60 * 1000; // 60 seconds

    private static final Integer DEFAULT_SCREEN_WIDTH = 1366; // WXGA width

    private static final Integer DEFAULT_SCREEN_HEIGHT = 768; // WXGA height

    private final List<Session> sessions = new CopyOnWriteArrayList<>();

    private final Map<Session, String> targets = new ConcurrentHashMap<>();

    private final List<String> browserContextList = new CopyOnWriteArrayList<>();

    private final ExecutorService threadPool;

    private AtomicBoolean headless;

    private volatile Session headlessSession;

    private String browserVersion;

    public SessionFactory() {
        this(DEFAULT_HOST,
                DEFAULT_PORT,
                DEFAULT_CONNECTION_TIMEOUT,
                Slf4j,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(CdpLoggerType loggerType) {
        this(DEFAULT_HOST,
                DEFAULT_PORT,
                DEFAULT_CONNECTION_TIMEOUT,
                loggerType,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(final int port) {
        this(DEFAULT_HOST,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                Slf4j,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(final int port, CdpLoggerType loggerType) {
        this(DEFAULT_HOST,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                loggerType,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(final String host, final int port) {
        this(host,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                Slf4j,
                newCachedThreadPool(new CdpThreadFactory()));
    }

    public SessionFactory(
                final String host,
                final int port,
                final CdpLoggerType loggerType,
                final ExecutorService threadPool) {
        this(host,
                port,
                DEFAULT_CONNECTION_TIMEOUT,
                loggerType,
                threadPool);
    }

    public SessionFactory(
                    final String host,
                    final int port,
                    final int connectionTimeout,
                    final CdpLoggerType loggerType,
                    final ExecutorService threadPool) {
        this.host              = host;
        this.port              = port;
        this.connectionTimeout = connectionTimeout;
        this.factory           = new WebSocketFactory();
        this.loggerFactory     = new CdpLoggerFactory();
        this.threadPool        = threadPool;
        this.log               = loggerFactory.getLogger("cdp4j.factory");
        this.gson              = new GsonBuilder()
                                    .disableHtmlEscaping()
                                    .create();
        this.factory.setConnectionTimeout(this.connectionTimeout);
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public Session create() {
        return create(null);
    }

    @SuppressWarnings("unchecked")
    public Session create(final String browserContextId) {
        boolean headless = isHeadless();
        if (headless) {
            if (headlessSession == null) {
                headlessSession = connectHeadless();
            }
            Target target = headlessSession.getCommand().getTarget();
            String targetId = target.createTarget("about:blank",
                                                  DEFAULT_SCREEN_WIDTH,
                                                  DEFAULT_SCREEN_HEIGHT,
                                                  browserContextId, false);
            Session session = connect(targetId);
            targets.put(session, targetId);
            return session;
        }
        String existingNewSessionId = null;
        for (SessionInfo info : list()) {
            boolean page   = "page".equals(info.getType());
            boolean newTab = "chrome://newtab/".equals(info.getUrl()) ||
                                "chrome://welcome/".equals(info.getUrl()) ||
                                info.getUrl().startsWith("chrome://welcome-win10");
            if (page && newTab) {
                existingNewSessionId = info.getId();
                break;
            }
        }
        boolean used = false;
        for (Session session : sessions) {
            if (session.getId().equals(existingNewSessionId)) {
                used = true;
                break;
            }
        }
        if ( existingNewSessionId != null && ! used ) {
            Session session = connect(existingNewSessionId);
            return session;
        } else {
            String createUrl = format("http://%s:%d/json/new", host, port);
            Reader reader    = null;
            URL    url       = null;
            try {
                url = new URL(createUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(connectionTimeout);
                reader = new InputStreamReader(conn.getInputStream());
                Map<String, Object> map = gson.fromJson(reader, Map.class);
                String newSessionId = valueOf(map.get("id"));
                Session newSession = connect(newSessionId);
                return newSession;
            } catch (IOException  e) {
                throw new CdpException(e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        }
    }

    public Session connect(String sessionId) {
        List<SessionInfo> sessions = list();
        String webSocketDebuggerUrl = null;
        for (SessionInfo session : sessions) {
            if (session.getId().equals(sessionId)) {
                webSocketDebuggerUrl = session.getWebSocketDebuggerUrl();
            }
        }
        if (webSocketDebuggerUrl == null) {
            throw new CdpException("SessionId not found: " + sessionId);
        }
        Reader    reader    = null;
        Session   session   = null;
        WebSocket webSocket = null;
        try {
            Map<Integer, WSContext> contextList = new ConcurrentHashMap<>();
            List<EventListener<?>> eventListeners = new CopyOnWriteArrayList<>();
            webSocket = factory.createSocket(webSocketDebuggerUrl);
            WSAdapter adapter = new WSAdapter(gson, contextList, eventListeners,
                                                    threadPool, loggerFactory.getLogger("cdp4j.ws.response"));
            webSocket.addListener(adapter);
            webSocket.connect();
            webSocket.setAutoFlush(true);
            session = new Session(gson, sessionId, webSocket,
                                    contextList, this, eventListeners, loggerFactory);
            adapter.setSession(session);
            this.sessions.add(session);
            return session;
        } catch (WebSocketException | IOException e) {
            throw new CdpException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public List<SessionInfo> list() {
        return list(connectionTimeout);
    }

    /**
     * @param connectionTimeout timeout an int that specifies the connect timeout value in milliseconds
     */
    public List<SessionInfo> list(int connectionTimeout) {
        String listSessions = format("http://%s:%d/json/list", host, port);
        URL    url          = null;
        Reader reader       = null;
        try {
            url = new URL(listSessions);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            reader = new InputStreamReader(conn.getInputStream());
            TypeToken<?> type = TypeToken.getParameterized(List.class, SessionInfo.class);
            List<SessionInfo> list = gson.fromJson(reader, type.getType());
            return list;
        } catch (ConnectException | SocketTimeoutException e) {
            log.debug(e.getMessage());
            return emptyList();
        } catch (IOException e) {
            throw new CdpException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public void close(Session session) {
        String sessionId = session.getId();
        boolean found = false;
        for (SessionInfo info : list(connectionTimeout)) {
            if (info.getId().equals(sessionId)) {
                found = true;
                break;
            }
        }
        String closeSession = format("http://%s:%d/json/close/%s", host, port, sessionId);
        URL    url          = null;
        Reader reader       = null;
        if (found) {
            try {
                if (isHeadless()) {
                    boolean isHeadlessSession = session != null &&
                                                    session.equals(headlessSession);
                    if (isHeadlessSession) {
                        return;
                    }
                    String targetId = targets.get(session);
                    if ( targetId != null && session.isConnected() ) {
                        headlessSession.getCommand().getTarget().closeTarget(targetId);
                        targets.remove(session);
                    }
                } else {
                    url = new URL(closeSession);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(connectionTimeout);
                    reader = new InputStreamReader(conn.getInputStream());
                }
            } catch (IOException e) {
                throw new CdpException(e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        }
        if (session != null) {
            try {
                session.dispose();
                sessions.remove(session);
            } catch (Throwable t) {
                // ignore
            }
        }
    }

    @Override
    public void close() {
        boolean headless = isHeadless() && headlessSession != null;
        if (headless) {
            for (String browserContextId : browserContextList) {
                try {
                    disposeBrowserContext(browserContextId);
                } catch (Throwable t) {
                    log.error(t.getMessage(), t);
                }
            }
        }
        for (Session session : sessions) {
            if (headless &&
                        session.getId().equals(headlessSession.getId())) {
                continue;
            }
            try {
                session.close();
            } catch (Throwable t) {
                log.error(t.getMessage(), t);
            }
        }
        threadPool.shutdownNow();
        if (headless) {
            browserContextList.clear();
            headlessSession.dispose();
        }
        sessions.clear();
        targets.clear();
        headlessSession = null;
        this.headless.set(false);
    }

    public void activate(String sessionId) {
        boolean found = false;
        for (SessionInfo info : list()) {
            if (info.getId().equals(sessionId)) {
                found = true;
                break;
            }
        }
        if ( ! found ) {
            return;
        }
        if (isHeadless()) {
            Session session = null;
            for (Session next : sessions) {
                if (next.getId().equals(sessionId)) {
                    session = next;
                    break;
                }
            }
            if (session != null) {
                String targetId = targets.get(session);
                if (targetId != null) {
                    headlessSession.getCommand().getTarget().activateTarget(targetId);
                }
            }
        } else {
            String closeSession = format("http://%s:%d/json/activate/%s", host, port, sessionId);
            URL    url          = null;
            Reader reader       = null;
            try {
                url = new URL(closeSession);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(connectionTimeout);
                reader = new InputStreamReader(conn.getInputStream());
            } catch (IOException e) {
                throw new CdpException(e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        }
    }

    protected Session connectHeadless() {
        List<SessionInfo> sessionInfos = list();
        if (sessionInfos.isEmpty()) {
            return null;
        }
        if (headlessSession != null) {
            return headlessSession;
        }
        for (SessionInfo next : sessionInfos) {
            if ( "about:blank".equals(next.getUrl()) &&
                        next.getId() != null &&
                        ! next.getId().trim().isEmpty() &&
                        next.getWebSocketDebuggerUrl() != null &&
                        ! next.getWebSocketDebuggerUrl().trim().isEmpty() ) {
                headlessSession = connect(next.getId());
                headless.compareAndSet(false, true);
                return headlessSession;
            }
        }
        return null;
    }

    public String getTargetId(Session session) {
        return targets.get(session);
    }

    public boolean isHeadless() {
        if (headless == null) {
            headless = new AtomicBoolean();
            Map<String, Object> version = getVersion();
            String ua = (String) version.get("User-Agent");
            browserVersion = (String) version.get("Browser");
            if (ua == null || ua.trim().isEmpty()) {
                headless.set(false);
            } else if (ua.toLowerCase(ENGLISH).contains("headless")) {
                headless.set(true);
            }
        }
        return headless.get();
    }

    protected Map<String, Object> getVersion() {
        String listSessions = format("http://%s:%d/json/version", host, port);
        URL    url          = null;
        Reader reader       = null;
        try {
            url = new URL(listSessions);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            reader = new InputStreamReader(conn.getInputStream());
            @SuppressWarnings("unchecked")
            Map<String, Object> map = gson.fromJson(reader, Map.class);
            return map;
        } catch (ConnectException e) {
            throw new CdpException(format("Unable to connect [%s:%d]", host, port));
        } catch (IOException e) {
            throw new CdpException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public Session getHeadlessSession() {
        if (isHeadless()) {
            return headlessSession;
        }
        return null;
    }

    public String createBrowserContext() {
        if (isHeadless()) {
            if (headlessSession == null) {
                headlessSession = connectHeadless();
            }
            String browserContextId = headlessSession
                                            .getCommand()
                                            .getTarget()
                                            .createBrowserContext();
            browserContextList.add(browserContextId);
            return browserContextId;
        }
        return null;
    }

    public void disposeBrowserContext(final String browserContextId) {
        if ( isHeadless() && headlessSession != null ) {
            headlessSession
                .getCommand()
                .getTarget()
                .disposeBrowserContext(browserContextId);
            browserContextList.remove(browserContextId);
        }
    }

    public List<String> listBrowserContextIds() {
        return unmodifiableList(browserContextList);
    }

    ExecutorService getThreadPool() {
        return threadPool;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    @Override
    public String toString() {
        return "SessionFactory [host=" + host + ", port=" + port + ", sessions=" + sessions + "]";
    }
}
