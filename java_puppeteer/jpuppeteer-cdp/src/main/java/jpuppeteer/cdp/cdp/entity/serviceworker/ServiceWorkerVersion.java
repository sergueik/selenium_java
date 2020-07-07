package jpuppeteer.cdp.cdp.entity.serviceworker;

/**
* ServiceWorker version.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ServiceWorkerVersion {

    /**
    */
    private String versionId;

    /**
    */
    private String registrationId;

    /**
    */
    private String scriptURL;

    /**
    */
    private String runningStatus;

    /**
    */
    private String status;

    /**
    * The Last-Modified header value of the main script.
    */
    private Double scriptLastModified;

    /**
    * The time at which the response headers of the main script were received from the server. For cached script it is the last time the cache entry was validated.
    */
    private Double scriptResponseTime;

    /**
    */
    private java.util.List<String> controlledClients;

    /**
    */
    private String targetId;



}