package jpuppeteer.chrome;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Sets;
import jpuppeteer.api.browser.Coordinate;
import jpuppeteer.api.browser.Frame;
import jpuppeteer.cdp.CDPSession;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectSubtype;
import jpuppeteer.cdp.cdp.constant.runtime.RemoteObjectType;
import jpuppeteer.cdp.cdp.domain.DOM;
import jpuppeteer.cdp.cdp.domain.Input;
import jpuppeteer.cdp.cdp.domain.Page;
import jpuppeteer.cdp.cdp.domain.Runtime;
import jpuppeteer.cdp.cdp.entity.page.NavigateRequest;
import jpuppeteer.cdp.cdp.entity.page.SetDocumentContentRequest;
import jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnRequest;
import jpuppeteer.cdp.cdp.entity.runtime.EvaluateRequest;
import jpuppeteer.chrome.constant.ScriptConstants;
import jpuppeteer.chrome.util.ChromeObjectUtils;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static jpuppeteer.chrome.ChromeBrowser.DEFAULT_TIMEOUT;

public class ChromeFrame extends AbstractExecution implements Frame {

    private static final Logger logger = LoggerFactory.getLogger(ChromeFrame.class);

    protected String frameId;

    protected ChromeFrame parent;

    protected Set<ChromeFrame> children;

    protected volatile Integer executionContextId;

    protected CDPSession session;

    protected Page page;

    protected DOM dom;

    protected Input input;

    @Setter
    protected String name;

    @Setter
    protected URL url;

    @Setter
    protected String securityOrigin;

    @Setter
    protected String mimeType;

    @Setter
    protected URL unreachableUrl;

    public ChromeFrame(ChromeFrame parent, String frameId, CDPSession session, Page page, Runtime runtime, DOM dom, Input input) {
        super(runtime);
        this.parent = parent;
        this.frameId = frameId;
        this.session = session;
        this.children = Sets.newConcurrentHashSet();
        this.page = page;
        this.dom = dom;
        this.input = input;
    }

    private static ChromeFrame find(ChromeFrame frame, String frameId) {
        ChromeFrame target = null;
        if (Objects.equals(frame.frameId, frameId)) {
            target = frame;
        } else if (CollectionUtils.isNotEmpty(frame.children)) {
            for (ChromeFrame frm : frame.children) {
                target = find(frm, frameId);
                if (target != null) {
                    break;
                }
            }
        }
        return target;
    }

    protected ChromeFrame find(String frameId) {
        return find(this, frameId);
    }

    private static ChromeFrame find(ChromeFrame frame, Integer executionContextId) {
        ChromeFrame target = null;
        if (Objects.equals(frame.executionContextId, executionContextId)) {
            target = frame;
        } else if (CollectionUtils.isNotEmpty(frame.children)) {
            for (ChromeFrame frm : frame.children) {
                target = find(frm, executionContextId);
                if (target != null) {
                    break;
                }
            }
        }
        return target;
    }

    protected ChromeFrame find(Integer executionContextId) {
        return find(this, executionContextId);
    }

    /**
     * 返回刚刚append的子frame
     * @param frameId
     * @return
     */
    protected ChromeFrame append(String frameId) {
        ChromeFrame frame = new ChromeFrame(this, frameId, session, page, runtime, dom, input);
        this.children.add(frame);
        return frame;
    }

    protected void remove() {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
    }

    public void createExecutionContext(Integer executionContextId) {
        this.executionContextId = executionContextId;
    }

    public void destroyExecutionContext() {
        this.executionContextId = null;
    }

    @Override
    protected void intercept(CallFunctionOnRequest request) {
        if (this.executionContextId == null) {
            throw new RuntimeException("execution context not created");
        }
        request.setExecutionContextId(executionContextId);
    }

    @Override
    protected void intercept(EvaluateRequest request) {
        if (this.executionContextId == null) {
            throw new RuntimeException("execution context not created");
        }
        request.setContextId(executionContextId);
    }

    @Override
    public String frameId() {
        return this.frameId;
    }

    @Override
    public ChromeFrame parent() {
        return parent;
    }

    @Override
    public ChromeFrame[] children() {
        ChromeFrame[] frames = new ChromeFrame[this.children.size()];
        return this.children.toArray(frames);
    }

    @Override
    public ChromeElement querySelector(String selector) throws Exception {
        ChromeBrowserObject object = call("function(selector){return document.querySelector(selector);}", selector);
        if (RemoteObjectType.UNDEFINED.equals(object.type) || RemoteObjectSubtype.NULL.equals(object.subType)) {
            return null;
        }
        return new ChromeElement(this, object);
    }

    @Override
    public List<ChromeElement> querySelectorAll(String selector) throws Exception {
        ChromeBrowserObject browserObject = call("function(selector){return document.querySelectorAll(selector);}", selector);
        List<ChromeBrowserObject> properties = browserObject.getProperties();
        ChromeObjectUtils.releaseObjectQuietly(runtime, browserObject.objectId);
        return properties.stream()
                .map(object -> new ChromeElement(this, object))
                .collect(Collectors.toList());
    }

    @Override
    public String content() throws Exception {
        ChromeBrowserObject object = call("function(){return document.documentElement.outerHTML;}");
        return object.toStringValue();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void setContent(String content) throws Exception {
        SetDocumentContentRequest request = new SetDocumentContentRequest();
        request.setFrameId(frameId);
        request.setHtml(content);
        page.setDocumentContent(request, DEFAULT_TIMEOUT);
    }

    @Override
    public String title() throws Exception {
        ChromeBrowserObject object = call("function(){return document.title;}");
        return object.toStringValue();
    }

    @Override
    public URL url() throws Exception {
        return url;
    }

    @Override
    public void navigate(String url, String referer) throws Exception {
        NavigateRequest request = new NavigateRequest();
        request.setUrl(url);
        request.setReferrer(referer);
        request.setFrameId(frameId);
        page.navigate(request, DEFAULT_TIMEOUT);
    }

    private static Object[] buildWaitArgs(String expression, int timeout, TimeUnit unit, Object... args) {
        Object[] callArgs = new Object[2 + args.length];
        callArgs[0] = expression;
        callArgs[1] = unit.toMillis(timeout);
        System.arraycopy(args, 0, callArgs, 2, args.length);
        return callArgs;
    }

    @Override
    public ChromeBrowserObject eval(String expression) throws Exception {
        ChromeBrowserObject object = super.eval(expression);
        if (RemoteObjectSubtype.NODE.equals(object.subType)) {
            return new ChromeElement(this, object);
        } else {
            return object;
        }
    }

    @Override
    public ChromeBrowserObject call(String declaration, Object... args) throws Exception {
        ChromeBrowserObject object = super.call(declaration, args);
        if (RemoteObjectSubtype.NODE.equals(object.subType)) {
            return new ChromeElement(this, object);
        } else {
            return object;
        }
    }

    @Override
    public ChromeBrowserObject wait(String expression, int timeout, TimeUnit unit, Object... args) throws Exception {
        Object[] callArgs = buildWaitArgs(expression, timeout, unit, args);
        return call(ScriptConstants.WAIT, callArgs);
    }

    @Override
    public <R> R wait(String expression, int timeout, TimeUnit unit, Class<R> clazz, Object... args) throws Exception {
        Object[] callArgs = buildWaitArgs(expression, timeout, unit, args);
        return call(ScriptConstants.WAIT, clazz, callArgs);
    }

    @Override
    public <R> R wait(String expression, int timeout, TimeUnit unit, TypeReference<R> type, Object... args) throws Exception {
        Object[] callArgs = buildWaitArgs(expression, timeout, unit, args);
        return call(ScriptConstants.WAIT, type, callArgs);
    }

    @Override
    public ChromeElement waitSelector(String selector, int timeout, TimeUnit unit) throws Exception {
        ChromeBrowserObject object = wait(ScriptConstants.WAIT_SELECTOR, timeout, unit, selector);
        if (RemoteObjectType.UNDEFINED.equals(object.type) || RemoteObjectSubtype.NULL.equals(object.subType)) {
            return null;
        }
        return new ChromeElement(this, object);
    }

    @Override
    public Coordinate scroll(int x, int y) throws Exception {
        JSONObject offset = call(ScriptConstants.SCROLL, JSONObject.class, null, x, y);
        return new Coordinate(offset.getDouble("scrollX"), offset.getDouble("scrollY"));
    }

}
