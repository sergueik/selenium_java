package jpuppeteer.chrome.event.page;

import jpuppeteer.api.browser.Header;
import jpuppeteer.api.browser.Response;
import jpuppeteer.api.browser.SecurityDetails;
import jpuppeteer.api.constant.ResourceType;
import jpuppeteer.chrome.ChromePage;
import jpuppeteer.chrome.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class FrameResponse extends FrameEvent implements Response {

    private static final Logger logger = LoggerFactory.getLogger(FrameResponse.class);

    private final String requestId;

    private final String loaderId;

    private final ResourceType resourceType;

    private final FrameRequest request;

    private final String url;

    private final int status;

    private final String statusText;

    private final List<Header> headers;

    private final boolean fromCache;

    private final String remoteAddress;

    private final SecurityDetails securityDetails;

    private volatile Object content;

    public FrameResponse(
            ChromePage page, String requestId, String loaderId,
            ResourceType resourceType, FrameRequest request,
            jpuppeteer.cdp.cdp.entity.network.Response response) {
        super(page, request.frame());
        this.requestId = requestId;
        this.loaderId = loaderId;
        this.resourceType = resourceType;
        this.request = request;
        this.url = response.getUrl();
        this.status = response.getStatus();
        this.statusText = response.getStatusText();
        this.headers = HttpUtils.parseHeader(response.getHeaders());
        this.fromCache = response.getFromDiskCache();
        this.remoteAddress = response.getRemoteIPAddress();
        if (response.getSecurityDetails() != null) {
            this.securityDetails = jpuppeteer.chrome.entity.SecurityDetails.builder()
                    .issuer(response.getSecurityDetails().getIssuer())
                    .protocol(response.getSecurityDetails().getProtocol())
                    .subjectName(response.getSecurityDetails().getSubjectName())
                    .vaildFrom(new Date(response.getSecurityDetails().getValidFrom().longValue() * 1000))
                    .vaildTo(new Date(response.getSecurityDetails().getValidTo().longValue() * 1000))
                    .build();
        } else {
            this.securityDetails = null;
        }
    }

    @Override
    public boolean fromCache() {
        return fromCache;
    }

    @Override
    public List<Header> headers() {
        return headers;
    }

    @Override
    public String remoteAddress() {
        return remoteAddress;
    }

    @Override
    public FrameRequest request() {
        return request;
    }

    @Override
    public SecurityDetails securityDetails() {
        return securityDetails;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public String statusText() {
        return statusText;
    }

    @Override
    public byte[] content() throws Exception {
        if (content == null) {
            synchronized (this) {
                if (content == null) {
                    try {
                        content = page().getResponseContent(requestId, headers);
                    } catch (Exception e) {
                        content = e;
                    }
                }
            }
        }
        if (content instanceof Exception) {
            throw new Exception(((Exception) content).getMessage());
        }
        return (byte[]) content;
    }

    @Override
    public String url() {
        return url;
    }
}
