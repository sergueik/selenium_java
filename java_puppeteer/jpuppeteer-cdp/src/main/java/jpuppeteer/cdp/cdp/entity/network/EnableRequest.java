package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EnableRequest {

    /**
    * Buffer size in bytes to use when preserving network payloads (XHRs, etc).
    */
    private Integer maxTotalBufferSize;

    /**
    * Per-resource buffer size in bytes to use when preserving network payloads (XHRs, etc).
    */
    private Integer maxResourceBufferSize;

    /**
    * Longest post body size (in bytes) that would be included in requestWillBeSent notification
    */
    private Integer maxPostDataSize;



}