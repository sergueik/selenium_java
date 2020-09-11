package jpuppeteer.cdp.cdp.entity.serviceworker;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DispatchSyncEventRequest {

    /**
    */
    private String origin;

    /**
    */
    private String registrationId;

    /**
    */
    private String tag;

    /**
    */
    private Boolean lastChance;



}