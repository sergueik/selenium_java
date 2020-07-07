package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.cdp.constant.network.BlockedReason;
import jpuppeteer.cdp.cdp.entity.network.LoadingFailedEvent;
import jpuppeteer.chrome.ChromePage;

public class FrameRequestFailed extends FrameEvent {

    private final String requestId;

    private final FrameRequest request;

    private final String errorText;

    private final Boolean canceled;

    private final BlockedReason blockedReason;

    public FrameRequestFailed(ChromePage page, FrameRequest request, LoadingFailedEvent failedEvent) {
        super(page, request.frame());
        this.request = request;
        this.requestId = failedEvent.getRequestId();
        this.errorText = failedEvent.getErrorText();
        this.canceled = failedEvent.getCanceled();
        this.blockedReason = BlockedReason.findByValue(failedEvent.getBlockedReason());
    }

    public String requestId() {
        return requestId;
    }

    public FrameRequest request() {
        return request;
    }

    public String errorText() {
        return errorText;
    }

    public Boolean canceled() {
        return canceled;
    }

    public BlockedReason blockedReason() {
        return blockedReason;
    }
}
