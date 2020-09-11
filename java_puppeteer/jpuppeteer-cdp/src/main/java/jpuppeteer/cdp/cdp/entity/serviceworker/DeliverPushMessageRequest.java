package jpuppeteer.cdp.cdp.entity.serviceworker;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DeliverPushMessageRequest {

    /**
    */
    private String origin;

    /**
    */
    private String registrationId;

    /**
    */
    private String data;



}