package jpuppeteer.cdp.cdp.entity.serviceworker;

/**
* ServiceWorker error message.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ServiceWorkerErrorMessage {

    /**
    */
    private String errorMessage;

    /**
    */
    private String registrationId;

    /**
    */
    private String versionId;

    /**
    */
    private String sourceURL;

    /**
    */
    private Integer lineNumber;

    /**
    */
    private Integer columnNumber;



}