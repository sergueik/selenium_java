package jpuppeteer.chrome;

import com.google.common.collect.Sets;
import jpuppeteer.api.browser.Browser;
import jpuppeteer.api.constant.PermissionType;
import jpuppeteer.api.event.AbstractListener;
import jpuppeteer.api.event.EventEmitter;
import jpuppeteer.cdp.CDPConnection;
import jpuppeteer.cdp.CDPEvent;
import jpuppeteer.cdp.CDPSession;
import jpuppeteer.cdp.WebSocketCDPConnection;
import jpuppeteer.cdp.cdp.CDPEventType;
import jpuppeteer.cdp.cdp.domain.Storage;
import jpuppeteer.cdp.cdp.domain.Target;
import jpuppeteer.cdp.cdp.entity.browser.GetVersionResponse;
import jpuppeteer.cdp.cdp.entity.browser.GrantPermissionsRequest;
import jpuppeteer.cdp.cdp.entity.browser.ResetPermissionsRequest;
import jpuppeteer.cdp.cdp.entity.network.Cookie;
import jpuppeteer.cdp.cdp.entity.network.CookieParam;
import jpuppeteer.cdp.cdp.entity.storage.ClearCookiesRequest;
import jpuppeteer.cdp.cdp.entity.storage.GetCookiesRequest;
import jpuppeteer.cdp.cdp.entity.storage.GetCookiesResponse;
import jpuppeteer.cdp.cdp.entity.storage.SetCookiesRequest;
import jpuppeteer.cdp.cdp.entity.target.*;
import jpuppeteer.cdp.constant.TargetType;
import jpuppeteer.chrome.event.context.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static jpuppeteer.cdp.cdp.CDPEventType.*;

public class ChromeBrowser implements EventEmitter<CDPEvent>, Browser {

    private static final Logger logger = LoggerFactory.getLogger(ChromeBrowser.class);

    private static final String DEFAULT_URL = "about:blank";

    public static final int DEFAULT_TIMEOUT = 30;

    private final String name;

    private final AtomicInteger contextCounter;

    private CDPConnection connection;

    private ChromeArguments arguments;

    private Map<String/*browserContextId*/, ChromeContext> contextMap;

    /**
     * 用来维护target跟context的关联关系, 以便发送事件的时候能快速找到对应的context
     */
    private Map<String/*targetId*/, ChromeContext> targetMap;

    /**
     * 用来维护session跟context的关联关系, 以便发送事件的时候能快速找到对应的context
     */
    private Map<String/*sessionId*/, ChromeContext> sessionMap;

    private Process process;

    private jpuppeteer.cdp.cdp.domain.Browser browser;

    private Target target;

    private ChromeContext defaultContext;

    private Storage storage;

    public ChromeBrowser(String name, Process process, CDPConnection connection, ChromeArguments arguments) throws Exception {
        this.name = name;
        this.contextCounter = new AtomicInteger(0);
        this.process = process;
        this.connection = connection;
        this.arguments = arguments;
        this.browser = new jpuppeteer.cdp.cdp.domain.Browser(connection);
        this.target = new Target(connection);
        this.storage = new Storage(connection);
        this.contextMap = new ConcurrentHashMap<>();
        this.targetMap = new ConcurrentHashMap<>();
        this.sessionMap = new ConcurrentHashMap<>();
        this.defaultContext = initContext(null);
        //此方法必须等默认上下文创建好了之后才能调用true
        this.setDiscoverTargets(true);

        //事件
        bindEventHandler(event -> handleTargetCreated(event), TARGET_TARGETCREATED);
        bindEventHandler(event -> handleTargetAttached(event), TARGET_ATTACHEDTOTARGET);
        bindEventHandler(event -> handleTargetDestroyed(event), TARGET_TARGETDESTROYED);
        bindEventHandler(event -> handleTargetChanged(event), TARGET_TARGETINFOCHANGED);
        bindEventHandler(event -> handleTargetCrashed(event), TARGET_TARGETCRASHED);
        //透传事件
        handleSessionEvent(
                (context, event) -> context.emit(new ContextCDPEvent(context, event)),
                PAGE_LIFECYCLEEVENT, PAGE_DOMCONTENTEVENTFIRED, PAGE_LOADEVENTFIRED,
                PAGE_FRAMEATTACHED, PAGE_FRAMEDETACHED, PAGE_FRAMENAVIGATED,
                PAGE_JAVASCRIPTDIALOGOPENING, NETWORK_REQUESTWILLBESENT, NETWORK_RESPONSERECEIVED,
                NETWORK_LOADINGFAILED, NETWORK_LOADINGFINISHED, RUNTIME_EXCEPTIONTHROWN,
                RUNTIME_EXECUTIONCONTEXTCREATED, RUNTIME_EXECUTIONCONTEXTDESTROYED, RUNTIME_EXECUTIONCONTEXTSCLEARED,
                FETCH_REQUESTPAUSED, FETCH_AUTHREQUIRED, RUNTIME_CONSOLEAPICALLED
        );
    }

    @Override
    public void emit(CDPEvent event) {
        connection.emit(event);
    }

    @Override
    public void addListener(AbstractListener<? extends CDPEvent> listener) {
        connection.addListener(listener);
    }

    @Override
    public void removeListener(AbstractListener<? extends CDPEvent> listener) {
        connection.removeListener(listener);
    }

    private void bindEventHandler(Consumer<CDPEvent> handler, CDPEventType... eventTypes) {
        Set<CDPEventType> eventTypeSet = Sets.newHashSet(eventTypes);
        addListener(new AbstractListener<CDPEvent>() {
            @Override
            public void accept(CDPEvent event) {
                if (eventTypeSet.contains(event.getMethod())) {
                    handler.accept(event);
                }
            }
        });
    }

    private void handleSessionEvent(ContextEventHandler handler, CDPEventType... eventTypes) {
        bindEventHandler(event -> {
            String sessionId = event.getSessionId();
            ChromeContext context = sessionMap.get(sessionId);
            if (context == null) {
                logger.error("handle event failed, context not found, event={} sessionId={}", event.getMethod().getName(), sessionId);
                return;
            }
            handler.handle(context, event);
        }, eventTypes);
    }

    private void handleTargetCreated(CDPEvent event) {
        TargetCreatedEvent evt = event.getObject(TargetCreatedEvent.class);
        TargetInfo targetInfo = evt.getTargetInfo();
        TargetType targetType = TargetType.findByValue(targetInfo.getType());
        if (!TargetType.PAGE.equals(targetType)) {
            logger.debug("target is not page, ignore! type={}, targetId={}, url={}", targetInfo.getType(), targetInfo.getTargetId(), targetInfo.getUrl());
            return;
        }
        String targetId = targetInfo.getTargetId();
        String contextId = targetInfo.getBrowserContextId();
        ChromeContext context = contextMap.get(contextId);
        if (context != null) {
            //自动执行attach操作
            asyncAttachToTarget(targetId);
            targetMap.put(targetId, context);
            context.emit(new TargetCreated(context, event));
            logger.debug("target created, auto do attach, targetId={}", targetId);
        }
    }

    private void handleTargetAttached(CDPEvent event) {
        AttachedToTargetEvent evt = event.getObject(AttachedToTargetEvent.class);
        TargetInfo target = evt.getTargetInfo();
        String targetId = target.getTargetId();
        String contextId = target.getBrowserContextId();
        ChromeContext context = contextMap.get(contextId);
        if (context != null) {
            String sessionId = evt.getSessionId();
            sessionMap.put(sessionId, context);
            context.emit(new TargetAttached(context, event));
            logger.debug("target attached, targetId={}", targetId);
        }
    }

    private void handleTargetDestroyed(CDPEvent event) {
        TargetDestroyedEvent evt = event.getObject(TargetDestroyedEvent.class);
        String targetId = evt.getTargetId();
        ChromeContext context = targetMap.remove(targetId);
        if (context != null) {
            try {
                for (ChromePage page : context.pages()) {
                    if (targetId.equals(page.targetInfo().getTargetId())) {
                        sessionMap.remove(page.sessionId());
                    }
                }
            } catch (Exception e) {
                //do nth...
            }
            context.emit(new TargetDestroyed(context, event));
            logger.debug("target destoryed, targetId={}", targetId);
        }
    }

    private void handleTargetChanged(CDPEvent event) {
        TargetInfoChangedEvent evt = event.getObject(TargetInfoChangedEvent.class);
        TargetInfo targetInfo = evt.getTargetInfo();
        String targetId = targetInfo.getTargetId();
        TargetType targetType = TargetType.findByValue(targetInfo.getType());
        if (!(TargetType.PAGE.equals(targetType))) {
            logger.debug("target is not page, ignore! type={}, targetId={}, url={}", targetInfo.getType(), targetInfo.getTargetId(), targetInfo.getUrl());
            return;
        }
        ChromeContext context = targetMap.get(targetId);
        if (context != null) {
            context.emit(new TargetChanged(context, event));
        }
    }

    private void handleTargetCrashed(CDPEvent event) {
        TargetCrashedEvent evt = event.getObject(TargetCrashedEvent.class);
        String targetId = evt.getTargetId();
        ChromeContext context = targetMap.get(targetId);
        if (context != null) {
            context.emit(new TargetCrashed(context, event));
            logger.error("target crashed, targetId={}", targetId);
        }
    }

    private String nextContextName() {
        return "Context[" + name + "]-" + contextCounter.getAndIncrement();
    }

    /**
     * 只对已经存在的页面生效
     * @param autoAttach 是否自动attach
     * @throws Exception
     */
    protected void setAutoAttach(boolean autoAttach) throws Exception {
        SetAutoAttachRequest request = new SetAutoAttachRequest();
        request.setAutoAttach(autoAttach);
        request.setFlatten(true);
        request.setWaitForDebuggerOnStart(false);
        this.target.setAutoAttach(request, DEFAULT_TIMEOUT);
    }

    protected void setDiscoverTargets(boolean discover) throws Exception {
        SetDiscoverTargetsRequest request = new SetDiscoverTargetsRequest();
        request.setDiscover(discover);
        target.setDiscoverTargets(request, DEFAULT_TIMEOUT);
    }

    private AttachToTargetRequest buildAttachToTargetRequest(String targetId) {
        AttachToTargetRequest request = new AttachToTargetRequest();
        request.setTargetId(targetId);
        request.setFlatten(true);
        return request;
    }

    protected String attachToTarget(String targetId) throws Exception {
        AttachToTargetResponse response = target.attachToTarget(buildAttachToTargetRequest(targetId), DEFAULT_TIMEOUT);
        return response.getSessionId();
    }

    protected Future<AttachToTargetResponse> asyncAttachToTarget(String targetId) {
        return target.asyncAttachToTarget(buildAttachToTargetRequest(targetId));
    }

    protected boolean closeTarget(String targetId) throws Exception {
        CloseTargetRequest request = new CloseTargetRequest();
        request.setTargetId(targetId);
        CloseTargetResponse response = target.closeTarget(request, DEFAULT_TIMEOUT);
        return response.getSuccess();
    }

    /**
     *
     * @param browserContextId
     * @param uuid 需要在创建的时候给定一个唯一的uuid
     * @return
     * @throws Exception
     */
    protected String createTarget(String browserContextId, String uuid) throws Exception {
        CreateTargetRequest request = new CreateTargetRequest();
        if (StringUtils.isEmpty(uuid)) {
            request.setUrl(DEFAULT_URL);
        } else {
            request.setUrl(DEFAULT_URL + "?uuid=" + uuid);
        }
        request.setBrowserContextId(browserContextId);
        CreateTargetResponse response = target.createTarget(request, DEFAULT_TIMEOUT);
        return response.getTargetId();
    }

    protected String createTarget(String browserContextId) throws Exception {
        return createTarget(browserContextId, null);
    }

    protected List<TargetInfo> getTargets(String browserContextId) throws Exception {
        GetTargetsResponse response = target.getTargets(DEFAULT_TIMEOUT);
        if (browserContextId == null) {
            return response.getTargetInfos();
        } else {
            return response.getTargetInfos().stream()
                    .filter(targetInfo -> Objects.equals(browserContextId, targetInfo.getBrowserContextId()))
                    .collect(Collectors.toList());
        }
    }

    protected void resetPermissions(String browserContextId) throws Exception {
        ResetPermissionsRequest request = new ResetPermissionsRequest();
        request.setBrowserContextId(browserContextId);
        browser.resetPermissions(request, DEFAULT_TIMEOUT);
    }

    protected void grantPermissions(String browserContextId, String origin, PermissionType... permissions) throws Exception {
        GrantPermissionsRequest request = new GrantPermissionsRequest();
        request.setBrowserContextId(browserContextId);
        request.setOrigin(origin);
        request.setPermissions(Arrays.stream(permissions)
                .map(permission -> permission.getValue())
                .collect(Collectors.toList()));
        browser.grantPermissions(request, DEFAULT_TIMEOUT);
    }

    protected void setCookies(String browserContextId, List<CookieParam> cookies) throws Exception {
        SetCookiesRequest request = new SetCookiesRequest();
        request.setBrowserContextId(browserContextId);
        request.setCookies(cookies);
        storage.setCookies(request, DEFAULT_TIMEOUT);
    }

    protected void clearCookies(String browserContextId) throws Exception {
        ClearCookiesRequest request = new ClearCookiesRequest();
        request.setBrowserContextId(browserContextId);
        storage.clearCookies(request, DEFAULT_TIMEOUT);
    }

    protected List<Cookie> getCookies(String browserContextId) throws Exception {
        GetCookiesRequest request = new GetCookiesRequest();
        request.setBrowserContextId(browserContextId);
        GetCookiesResponse response = storage.getCookies(request, DEFAULT_TIMEOUT);
        return response.getCookies();
    }

    protected CDPSession createSession(TargetType targetType, String sessionId) {
        return new CDPSession(connection, targetType, sessionId);
    }

    private ChromeContext initContext(String browserContextId) throws Exception {
        List<TargetInfo> targets = getTargets(browserContextId).stream()
                .filter(targetInfo -> TargetType.PAGE.getValue().equals(targetInfo.getType()))
                .collect(Collectors.toList());
        TargetInfo targetInfo;
        if (targets.size() == 0) {
            //没有默认页
            //创建一个
            String targetId = createTarget(browserContextId);
            try {
                targetInfo = getTargets(browserContextId).stream()
                        .filter(tinfo -> targetId.equals(tinfo.getTargetId()))
                        .findAny()
                        .get();
            } catch (Exception e) {
                closeTarget(targetId);
                throw e;
            }
        } else if (targets.size() == 1) {
            //有一个默认页
            targetInfo = targets.get(0);
        } else {
            //有多个默认页
            //留下第一个, 其余的关闭掉
            targetInfo = targets.get(0);
            for(int i=1; i<targets.size(); i++) {
                String tid = targets.get(i).getTargetId();
                try {
                    closeTarget(tid);
                } catch (Exception e) {
                    logger.error("close startup page failed, targetId={}", tid);
                }
            }
        }
        ChromeContext context = new ChromeContext(nextContextName(), this, browserContextId);
        TargetType targetType = TargetType.findByValue(targetInfo.getType());
        String sessionId = attachToTarget(targetInfo.getTargetId());
        this.contextMap.put(targetInfo.getBrowserContextId(), context);
        this.targetMap.put(targetInfo.getTargetId(), context);
        this.sessionMap.put(sessionId, context);
        context.init(sessionId, targetType, targetInfo);
        return context;
    }

    @Override
    public synchronized void close() {
        if (!process.isAlive()) {
            return;
        }
        try {
            //关闭打开的上下文
            contextMap.forEach((browserContextId, context) -> {
                try {
                    context.close();
                } catch (Exception e) {
                    logger.error("close browser context failed, browserContextId={}, error={}", browserContextId, e.getMessage(), e);
                }
            });
            //关闭浏览器
            browser.close(DEFAULT_TIMEOUT);
            connection.close();
            logger.info("browser closed");
            while (process.isAlive()) {
                logger.info("process is alive, waiting...");
                TimeUnit.SECONDS.sleep(1);
            }
            //清空临时文件夹
            if (arguments.isUseTempUserData()) {
                //删除临时文件夹
                File tmp = new File(arguments.getUserDataDir());
                if (tmp.exists()) {
                    logger.debug("clean user data dir {}", arguments.getUserDataDir());
                    delete(tmp);
                    logger.debug("clean success {}", arguments.getUserDataDir());
                }
            }
            logger.info("browser process normally exited");
        } catch (Exception e) {
            process.destroy();
            logger.error("close browser failed force shutdown, error={}", e.getMessage(), e);
        }
    }

    private void delete(File file) {
        if (file.isDirectory()) {
            for(File f : file.listFiles()) {
                delete(f);
            }
        }
        file.delete();
    }

    @Override
    public ChromeContext[] browserContexts() {
        List<ChromeContext> contextList = contextMap.values().stream()
                .filter(context -> context != null)
                .collect(Collectors.toList());
        ChromeContext[] contexts = new ChromeContext[contextList.size()];
        return contextList.toArray(contexts);
    }

    @Override
    public ChromeContext createContext() throws Exception {
        CreateBrowserContextResponse response = target.createBrowserContext(DEFAULT_TIMEOUT);
        String contextId = response.getBrowserContextId();
        try {
            ChromeContext context = initContext(contextId);
            logger.debug("browser context created, contextId={}", contextId);
            return context;
        } catch (Exception e) {
            DisposeBrowserContextRequest request = new DisposeBrowserContextRequest();
            request.setBrowserContextId(contextId);
            target.disposeBrowserContext(request, DEFAULT_TIMEOUT);
            logger.warn("create browser context failed, contextId={}", contextId);
            throw e;
        }
    }

    @Override
    public ChromeContext defaultContext() {
        return defaultContext;
    }

    protected void closeContext(String browserContextId) throws Exception {
        if (browserContextId == null) {
            //默认的上下文不能用此方法关闭
            return;
        }
        DisposeBrowserContextRequest request = new DisposeBrowserContextRequest();
        request.setBrowserContextId(browserContextId);
        target.disposeBrowserContext(request, DEFAULT_TIMEOUT);
        contextMap.remove(browserContextId);
        logger.debug("browser context closed, contextId={}", browserContextId);
    }

    @Override
    public String userAgent() throws Exception {
        GetVersionResponse response = browser.getVersion(DEFAULT_TIMEOUT);
        return response.getUserAgent();
    }

    @Override
    public String version() throws Exception {
        GetVersionResponse response = browser.getVersion(DEFAULT_TIMEOUT);
        return response.getProduct();
    }

    @Override
    public URI wsEndpoint() {
        return connection instanceof WebSocketCDPConnection ? ((WebSocketCDPConnection) connection).uri() : null;
    }

    @Override
    public Process process() {
        return this.process;
    }

    @FunctionalInterface
    private interface ContextEventHandler {

        void handle(ChromeContext context, CDPEvent event);

    }
}
