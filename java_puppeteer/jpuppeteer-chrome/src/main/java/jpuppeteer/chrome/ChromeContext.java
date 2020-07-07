package jpuppeteer.chrome;

import com.google.common.util.concurrent.SettableFuture;
import jpuppeteer.api.browser.BrowserContext;
import jpuppeteer.api.browser.Cookie;
import jpuppeteer.api.constant.PermissionType;
import jpuppeteer.api.event.AbstractEventEmitter;
import jpuppeteer.api.event.AbstractListener;
import jpuppeteer.cdp.cdp.CDPEventType;
import jpuppeteer.cdp.cdp.constant.runtime.ConsoleAPICalledEventType;
import jpuppeteer.cdp.cdp.entity.fetch.AuthRequiredEvent;
import jpuppeteer.cdp.cdp.entity.fetch.RequestPausedEvent;
import jpuppeteer.cdp.cdp.entity.network.LoadingFailedEvent;
import jpuppeteer.cdp.cdp.entity.network.LoadingFinishedEvent;
import jpuppeteer.cdp.cdp.entity.network.RequestWillBeSentEvent;
import jpuppeteer.cdp.cdp.entity.network.ResponseReceivedEvent;
import jpuppeteer.cdp.cdp.entity.page.*;
import jpuppeteer.cdp.cdp.entity.runtime.ConsoleAPICalledEvent;
import jpuppeteer.cdp.cdp.entity.runtime.ExceptionThrownEvent;
import jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextCreatedEvent;
import jpuppeteer.cdp.cdp.entity.runtime.ExecutionContextDestroyedEvent;
import jpuppeteer.cdp.cdp.entity.target.AttachedToTargetEvent;
import jpuppeteer.cdp.cdp.entity.target.TargetCrashedEvent;
import jpuppeteer.cdp.cdp.entity.target.TargetInfo;
import jpuppeteer.cdp.constant.PageLifecyclePhase;
import jpuppeteer.cdp.constant.TargetType;
import jpuppeteer.chrome.event.context.*;
import jpuppeteer.chrome.event.page.*;
import jpuppeteer.chrome.util.URLUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static jpuppeteer.cdp.cdp.CDPEventType.*;

public class ChromeContext extends AbstractEventEmitter<ContextEvent> implements BrowserContext {

    private static final Logger logger = LoggerFactory.getLogger(ChromeContext.class);

    private static final String NEW_PAGE_URL_PREFIX = "about:blank?uuid=";

    private final ExecutorService executors;

    private final String name;

    private final AtomicInteger pageCounter;

    private ChromeBrowser browser;

    private String browserContextId;

    private Map<String/*targetId*/, ChromePage> targetMap;

    private Map<String/*sessionId*/, ChromePage> sessionMap;

    /**
     * @see #newPage()
     */
    private Map<String/*uuid*/, SettableFuture<ChromePage>> futureMap;

    private ChromePage defaultPage;

    public ChromeContext(String name, ChromeBrowser browser, String browserContextId) throws Exception {
        this.executors = Executors.newSingleThreadExecutor(r -> new Thread(r, name));
        this.name = name;
        this.pageCounter = new AtomicInteger(0);
        this.browser = browser;
        this.browserContextId = browserContextId;
        this.targetMap = new ConcurrentHashMap<>();
        this.sessionMap = new ConcurrentHashMap<>();
        this.futureMap = new ConcurrentHashMap<>();

        addListener(new AbstractListener<TargetAttached>() {
            @Override
            public void accept(TargetAttached targetAttached) {
                handleTargetAttached(targetAttached.event());
            }
        });
        addListener(new AbstractListener<TargetDestroyed>() {
            @Override
            public void accept(TargetDestroyed targetDestroyed) {
                handleTargetDestroyed(targetDestroyed.event().getTargetId());
            }
        });
        addListener(new AbstractListener<TargetChanged>() {
            @Override
            public void accept(TargetChanged targetChanged) {
                handleTargetChanged(targetChanged.event().getTargetInfo());
            }
        });
        addListener(new AbstractListener<TargetCrashed>() {
            @Override
            public void accept(TargetCrashed targetCrashed) {
                handleTargetCrashed(targetCrashed.event());
            }
        });

        handlePageEvent(PAGE_FRAMEATTACHED, (PageEventHandler<FrameAttachedEvent>) (pg, event) -> {
            handleFrameAttached(pg, event);
        });
        handlePageEvent(PAGE_FRAMENAVIGATED, (PageEventHandler<FrameNavigatedEvent>) (pg, event) -> {
            handleFrameNavigated(pg, event);
        });
        handlePageEvent(PAGE_FRAMEDETACHED, (PageEventHandler<FrameDetachedEvent>) (pg, event) -> {
            handleFrameDetached(pg, event);
        });

        handlePageEvent(PAGE_LIFECYCLEEVENT, (PageEventHandler<LifecycleEvent>) (pg, event) -> {
            ChromeFrame frame = pg.find(event.getFrameId());
            if (frame == null) {
                return;
            }
            pg.emit(new FrameLifecycle(pg, frame, PageLifecyclePhase.findByValue(event.getName())));
        });
        handlePageEvent(PAGE_DOMCONTENTEVENTFIRED, (PageEventHandler<DomContentEventFiredEvent>) (pg, event) -> {
            pg.emit(new PageReady(pg, event.getTimestamp()));
        });
        handlePageEvent(PAGE_LOADEVENTFIRED, (PageEventHandler<LoadEventFiredEvent>) (pg, event) -> {
            pg.emit(new PageLoaded(pg, event.getTimestamp()));
        });
        handlePageEvent(RUNTIME_CONSOLEAPICALLED, (PageEventHandler<ConsoleAPICalledEvent>) (pg, event) -> {
            ChromeFrame frame = pg.find(event.getExecutionContextId());
            if (frame == null) {
                logger.warn("execution not found, executionId={}", event.getExecutionContextId());
                return;
            }
            ConsoleAPICalledEventType eventType = ConsoleAPICalledEventType.findByValue(event.getType());
            List<ChromeBrowserObject> args = event.getArgs().stream()
                    .map(arg -> new ChromeBrowserObject(frame.runtime, frame, arg))
                    .collect(Collectors.toList());
            pg.emit(new Console(pg, eventType, args, frame, event.getTimestamp(), event.getStackTrace(), event.getContext()));
        });
        handlePageEvent(PAGE_JAVASCRIPTDIALOGOPENING, (PageEventHandler<JavascriptDialogOpeningEvent>) (pg, event) -> {
            pg.emit(new Dialog(pg, event));
        });

        handlePageEvent(RUNTIME_EXCEPTIONTHROWN, (PageEventHandler<ExceptionThrownEvent>) (pg, event) -> {
            pg.emit(new PageError(pg, event));
        });
        handlePageEvent(RUNTIME_EXECUTIONCONTEXTCREATED, (PageEventHandler<ExecutionContextCreatedEvent>) (pg, event) -> {
            pg.handleExecutionCreated(event);
        });
        handlePageEvent(RUNTIME_EXECUTIONCONTEXTDESTROYED, (PageEventHandler<ExecutionContextDestroyedEvent>) (pg, event) -> {
            pg.handleExecutionDestroyed(event);
        });
        handlePageEvent(RUNTIME_EXECUTIONCONTEXTSCLEARED, (pg, event) -> {
            pg.handleExecutionCleared();
        });

        handlePageEvent(NETWORK_REQUESTWILLBESENT, (PageEventHandler<RequestWillBeSentEvent>) (pg, event) -> {
            FrameRequest request = pg.handleRequest(event);
            if (request != null) {
                pg.emit(request);
            }
        });
        handlePageEvent(NETWORK_RESPONSERECEIVED, (PageEventHandler<ResponseReceivedEvent>) (pg, event) -> {
            FrameResponse response = pg.handleResponse(event);
            if (response != null) {
                pg.emit(response);
            }
        });
        handlePageEvent(NETWORK_LOADINGFAILED, (PageEventHandler<LoadingFailedEvent>) (pg, event) -> {
            FrameRequestFailed requestFailed = pg.handleRequestFailed(event);
            if (requestFailed != null) {
                pg.emit(requestFailed);
            }
        });
        handlePageEvent(NETWORK_LOADINGFINISHED, (PageEventHandler<LoadingFinishedEvent>) (pg, event) -> {
            FrameRequestFinished requestFinished = pg.handleRequestFinished(event);
            if (requestFinished != null) {
                pg.emit(requestFinished);
            }
        });
        handlePageEvent(FETCH_REQUESTPAUSED, (PageEventHandler<RequestPausedEvent>) (pg, event) -> {
            pg.handleRequestInterception(event);
        });
        handlePageEvent(FETCH_AUTHREQUIRED, (PageEventHandler<AuthRequiredEvent>) (pg, event) -> {
            pg.handleAuthentication(event);
        });
    }

    private String nextPageName() {
        return name + "-Page-" + pageCounter.getAndIncrement();
    }

    protected void init(String sessionId, TargetType targetType, TargetInfo targetInfo) throws Exception {
        ChromePage page = new ChromePage(nextPageName(), this, browser.createSession(targetType, sessionId), targetInfo, null);
        page.addListener(new AbstractListener<PageCrashed>() {
            @Override
            public void accept(PageCrashed pageCrashed) {
                //当页面崩溃的时候自动刷新页面, 避免因为页面崩溃cdp无法通信的情况
                try {
                    page.reload();
                } catch (Exception e) {
                    logger.error("default page crashed and reload failed, error={}", e.getMessage(), e);
                }
            }
        });
        targetMap.put(targetInfo.getTargetId(), page);
        sessionMap.put(sessionId, page);
        page.init();
        this.defaultPage = page;
    }

    @Override
    protected void emitInternal(AbstractListener<ContextEvent> listener, ContextEvent event) {
        executors.submit(() -> listener.accept(event));
    }

    private <T> void bindEventHandler(CDPEventType eventType, Consumer<ContextCDPEvent<T>> handler) {
        addListener(new AbstractListener<ContextCDPEvent<T>>() {
            @Override
            public void accept(ContextCDPEvent<T> contextCDPEvent) {
                if (Objects.equals(eventType, contextCDPEvent.method())) {
                    handler.accept(contextCDPEvent);
                }
            }
        });
    }

    private <T> void handlePageEvent(CDPEventType eventType, PageEventHandler<T> handler) {
        bindEventHandler(eventType, (ContextCDPEvent<T> contextCDPEvent) -> {
            String sessionId = contextCDPEvent.sessionId();
            ChromePage pg = sessionMap.get(sessionId);
            if (pg == null) {
                logger.error("handle event failed:session not found, event={} sessionId={}", contextCDPEvent.method().getName(), contextCDPEvent.sessionId());
                return;
            }
            handler.handle(pg, contextCDPEvent.event());
        });
    }

    private void handleTargetAttached(AttachedToTargetEvent event) {
        TargetInfo targetInfo = event.getTargetInfo();
        String targetId = targetInfo.getTargetId();
        String sessionId = event.getSessionId();
        ChromePage opener = null;
        String openerId = targetInfo.getOpenerId();
        if (StringUtils.isNotEmpty(openerId)) {
            opener = targetMap.get(openerId);
            if (opener == null) {
                logger.error("target opener not found, openerId={}, targetId={}", openerId, targetId);
                return;
            }
        }

        String url = targetInfo.getUrl();
        SettableFuture<ChromePage> promise = null;
        if (url.startsWith(NEW_PAGE_URL_PREFIX)) {
            //newPage创建的页面
            String uuid = url.substring(NEW_PAGE_URL_PREFIX.length());
            if (StringUtils.isNotEmpty(uuid)) {
                promise = futureMap.get(uuid);
            }
        }

        TargetType targetType = TargetType.findByValue(targetInfo.getType());

        try {
            ChromePage page = new ChromePage(nextPageName(), this, browser.createSession(targetType, sessionId), targetInfo, opener);
            targetMap.put(targetId, page);
            sessionMap.put(sessionId, page);
            page.init();
            if (promise != null) {
                promise.set(page);
            }
            try {
                emit(new PageCreated(this, page));
                if (opener != null) {
                    opener.emit(new PageOpened(opener, page));
                }
            } catch (Exception e) {
                logger.error("emit newpage or openpage event failed, error={}", e.getMessage(), e);
            }
        } catch (Exception e) {
            if (promise != null) {
                promise.setException(e);
            }
            logger.error("create page instance failed, error={}", e.getMessage(), e);
        }
    }

    private void handleTargetDestroyed(String targetId) {
        ChromePage pg = targetMap.remove(targetId);
        if (pg != null) {
            sessionMap.remove(pg.sessionId());
            logger.debug("target destroyed, targetId={}", targetId);
        }
    }

    private void handleTargetChanged(TargetInfo targetInfo) {
        ChromePage pg = targetMap.get(targetInfo.getTargetId());
        if (pg != null) {
            pg.handleTargetChanged(targetInfo);
            pg.emit(new PageChanged(pg, targetInfo));
        }
    }

    private void handleTargetCrashed(TargetCrashedEvent event) {
        ChromePage pg = targetMap.get(event.getTargetId());
        if (pg != null) {
            pg.emit(new PageCrashed(pg, event));
        }
    }

    private void handleFrameAttached(ChromePage pg, FrameAttachedEvent event) {
        ChromeFrame parent = pg.find(event.getParentFrameId());
        if (parent == null) {
            logger.warn("parent frame not found, parentId={}", event.getParentFrameId());
            return;
        }
        pg.emit(new FrameAttached(pg, parent.append(event.getFrameId())));
        logger.debug("frame attached, parent={}, frameId={}", event.getParentFrameId(), event.getFrameId());
    }

    private void handleFrameNavigated(ChromePage pg, FrameNavigatedEvent event) {
        Frame frm = event.getFrame();
        ChromeFrame frame = pg.find(frm.getId());
        if (frame == null) {
            logger.warn("navigated failed:frame not found, frameId={}", frm.getId());
            return;
        }
        frame.setUrl(URLUtils.parse(frm.getUrl()));
        frame.setName(frm.getName());
        frame.setMimeType(frm.getMimeType());
        frame.setUnreachableUrl(URLUtils.parse(frm.getUnreachableUrl()));
        frame.setSecurityOrigin(frm.getSecurityOrigin());
        pg.emit(new FrameNavigated(pg, frame));
    }

    private void handleFrameDetached(ChromePage pg, FrameDetachedEvent event) {
        ChromeFrame frame = pg.find(event.getFrameId());
        if (frame == null) {
            logger.warn("detached failed:frame not found, frameId={}", event.getFrameId());
            return;
        }
        frame.remove();
        pg.emit(new FrameDetached(pg, frame));
        logger.debug("frame detached, parent={}, frameId={}", frame.parent.frameId, event.getFrameId());
    }

    protected ChromePage defaultPage() {
        return defaultPage;
    }


    @Override
    public ChromeBrowser browser() {
        return browser;
    }

    @Override
    public void resetPermissions() throws Exception {
        browser.resetPermissions(browserContextId);
    }

    @Override
    public void grantPermissions(String origin, PermissionType... permissions) throws Exception {
        browser.grantPermissions(browserContextId, origin, permissions);
    }

    @Override
    public ChromePage newPage() throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        SettableFuture<ChromePage> future = SettableFuture.create();
        futureMap.put(uuid, future);
        String targetId = browser.createTarget(browserContextId, uuid);
        try {
            ChromePage page = future.get(5, TimeUnit.SECONDS);
            if (!Objects.equals(targetId, page.targetInfo().getTargetId())) {
                throw new RuntimeException("targetId not match, expect:" + targetId + ", actual:" + page.targetInfo().getTargetId());
            }
            logger.debug("page created, targetId={}", targetId);
            return page;
        } catch (Exception e) {
            browser.closeTarget(targetId);
            logger.warn("create page failed, targetId={}", targetId);
            throw e;
        } finally {
            futureMap.remove(uuid);
        }
    }

    @Override
    public ChromePage[] pages() throws Exception {
        List<ChromePage> pageList = targetMap.values().stream()
                .filter(page -> page != null)
                .collect(Collectors.toList());
        ChromePage[] pages = new ChromePage[pageList.size()];
        return pageList.toArray(pages);
    }

    @Override
    public void setCookies(Cookie... cookies) throws Exception {
        defaultPage.setCookies(cookies);
        //browser.setCookies(browserContextId, CookieUtils.toList(cookies));
    }

    @Override
    public void deleteCookies(String name, String domain, String path, String url) throws Exception {
        defaultPage.deleteCookies(name, domain, path, url);
    }

    @Override
    public void clearCookies() throws Exception {
        if (browserContextId == null) {
            //初始上下文可以使用Network.clearBrowserCookies
            defaultPage.doClearCookies();
        } else {
            //自己创建的上下文需要自己一个一个删除
            List<Cookie> cookies = cookies();
            List<Future> futures = new ArrayList<>(cookies.size());
            for(Cookie cookie : cookies) {
                futures.add(defaultPage.asyncDeleteCookies(cookie.getName(), cookie.getDomain(), cookie.getPath(), cookie.getUrl()));
            }
            for(Future future : futures) {
                future.get(1, TimeUnit.SECONDS);
            }
        }
        //defaultPage.doClearCookies();
        //browser.clearCookies(browserContextId);
    }

    @Override
    public List<Cookie> cookies(String... urls) throws Exception {
        return defaultPage.doGetCookies(urls);
//        List<jpuppeteer.cdp.cdp.entity.network.Cookie> cookieList = browser.getCookies(browserContextId);
//        Cookie[] cookies = new Cookie[cookieList.size()];
//        for(int i = 0; i<cookieList.size(); i++) {
//            cookies[i] = CookieUtils.copyOf(cookieList.get(i));
//        }
//        return cookies;
    }

    @Override
    public void close() throws Exception {
        try {
            //关闭打开的页面
            targetMap.forEach((targetId, page) -> {
                try {
                    page.close();
                } catch (Exception e) {
                    logger.error("close page failed, targetId={}, error={}", targetId, e.getMessage(), e);
                }
            });
            browser.closeContext(browserContextId);
        } finally {
            executors.shutdown();
        }
    }

    @FunctionalInterface
    private interface PageEventHandler<T> {

        void handle(ChromePage pg, T event);

    }
}
