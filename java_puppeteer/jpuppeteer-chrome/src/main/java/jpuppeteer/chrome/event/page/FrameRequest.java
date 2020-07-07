package jpuppeteer.chrome.event.page;

import jpuppeteer.api.browser.Header;
import jpuppeteer.api.browser.Request;
import jpuppeteer.api.constant.ResourceType;
import jpuppeteer.chrome.ChromeFrame;
import jpuppeteer.chrome.ChromePage;
import jpuppeteer.chrome.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FrameRequest extends FrameEvent implements Request {

    private static final Logger logger = LoggerFactory.getLogger(FrameRequest.class);

    private final String requestId;

    private final String loaderId;

    private final String method;

    private final String url;

    private final ResourceType resourceType;

    private final List<Header> headers;

    private final boolean hasPostData;

    private volatile Object postData;

    private volatile FrameResponse response;

    public FrameRequest(
            ChromePage page, ChromeFrame frame,
            String requestId, String loaderId, ResourceType resourceType,
            jpuppeteer.cdp.cdp.entity.network.Request request) {
        super(page, frame);
        this.requestId = requestId;
        this.loaderId = loaderId;
        this.resourceType = resourceType;
        this.method = request.getMethod();
        if (StringUtils.isEmpty(request.getUrlFragment())) {
            this.url = request.getUrl();
        } else {
            this.url = request.getUrl() + request.getUrlFragment();
        }
        this.headers = HttpUtils.parseHeader(request.getHeaders());
        this.hasPostData = request.getPostData() != null || (request.getHasPostData() != null && request.getHasPostData()) ? true : false;
        this.postData = request.getPostData();
    }

    public void response(FrameResponse response) {
        this.response = response;
    }

    @Override
    public List<Header> headers() {
        return headers;
    }

    @Override
    public boolean isNavigationRequest() {
        return StringUtils.equals(requestId, loaderId) && ResourceType.DOCUMENT.equals(resourceType);
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String content() throws Exception {
        if (!hasPostData) {
            return null;
        }
        if (postData == null) {
            synchronized (this) {
                if (postData == null) {
                    try {
                        postData = page().getRequestContent(requestId);
                    } catch (Exception e) {
                        postData = e;
                    }
                }
            }
        }
        if (postData instanceof Exception) {
            throw new Exception(((Exception) postData).getMessage());
        }
        return (String) postData;
    }

    @Override
    public ResourceType resourceType() {
        return resourceType;
    }

    @Override
    public FrameResponse response() {
        return response;
    }

    @Override
    public String url() {
        return url;
    }
}
