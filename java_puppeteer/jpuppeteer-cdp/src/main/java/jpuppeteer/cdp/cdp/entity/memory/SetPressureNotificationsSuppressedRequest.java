package jpuppeteer.cdp.cdp.entity.memory;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetPressureNotificationsSuppressedRequest {

    /**
    * If true, memory pressure notifications will be suppressed.
    */
    private Boolean suppressed;



}