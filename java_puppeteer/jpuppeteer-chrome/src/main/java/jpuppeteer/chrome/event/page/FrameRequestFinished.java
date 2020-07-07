package jpuppeteer.chrome.event.page;

import jpuppeteer.cdp.cdp.entity.network.LoadingFinishedEvent;
import jpuppeteer.chrome.ChromePage;

public class FrameRequestFinished extends FrameEvent {

    private final String requestId;

    private final FrameRequest request;

    private final Double encodedDataLength;

    private final Boolean shouldReportCorbBlocking;

    public FrameRequestFinished(ChromePage page, FrameRequest request, LoadingFinishedEvent finishedEvent) {
        super(page, request.frame());
        this.request = request;
        this.requestId = finishedEvent.getRequestId();
        this.encodedDataLength = finishedEvent.getEncodedDataLength();
        this.shouldReportCorbBlocking = finishedEvent.getShouldReportCorbBlocking();
    }

    public String requestId() {
        return requestId;
    }

    public FrameRequest request() {
        return request;
    }

    public Double encodedDataLength() {
        return encodedDataLength;
    }

    public Boolean shouldReportCorbBlocking() {
        return shouldReportCorbBlocking;
    }
}
