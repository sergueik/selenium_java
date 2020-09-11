package jpuppeteer.cdp.cdp.entity.serviceworker;

/**
* ServiceWorker registration.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ServiceWorkerRegistration {

    /**
    */
    private String registrationId;

    /**
    */
    private String scopeURL;

    /**
    */
    private Boolean isDeleted;



}