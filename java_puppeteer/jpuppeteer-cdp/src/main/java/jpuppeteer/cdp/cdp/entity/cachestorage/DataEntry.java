package jpuppeteer.cdp.cdp.entity.cachestorage;

/**
* Data entry.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DataEntry {

    /**
    * Request URL.
    */
    private String requestURL;

    /**
    * Request method.
    */
    private String requestMethod;

    /**
    * Request headers
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.cachestorage.Header> requestHeaders;

    /**
    * Number of seconds since epoch.
    */
    private Double responseTime;

    /**
    * HTTP response status code.
    */
    private Integer responseStatus;

    /**
    * HTTP response status text.
    */
    private String responseStatusText;

    /**
    * HTTP response type
    */
    private String responseType;

    /**
    * Response headers
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.cachestorage.Header> responseHeaders;



}