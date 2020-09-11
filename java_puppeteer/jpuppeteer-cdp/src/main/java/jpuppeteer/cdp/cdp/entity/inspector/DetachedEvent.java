package jpuppeteer.cdp.cdp.entity.inspector;

/**
* Fired when remote debugging connection is about to be terminated. Contains detach reason.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DetachedEvent {

    /**
    * The reason why connection has been terminated.
    */
    private String reason;



}