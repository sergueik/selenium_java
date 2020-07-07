package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when HTTP request has finished loading.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LoadingFinishedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Timestamp.
    */
    private Double timestamp;

    /**
    * Total number of bytes received for this request.
    */
    private Double encodedDataLength;

    /**
    * Set when 1) response was blocked by Cross-Origin Read Blocking and also 2) this needs to be reported to the DevTools console.
    */
    private Boolean shouldReportCorbBlocking;



}