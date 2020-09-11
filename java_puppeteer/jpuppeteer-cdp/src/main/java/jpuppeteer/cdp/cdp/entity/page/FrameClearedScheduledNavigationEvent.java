package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when frame no longer has a scheduled navigation.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameClearedScheduledNavigationEvent {

    /**
    * Id of the frame that has cleared its scheduled navigation.
    */
    private String frameId;



}