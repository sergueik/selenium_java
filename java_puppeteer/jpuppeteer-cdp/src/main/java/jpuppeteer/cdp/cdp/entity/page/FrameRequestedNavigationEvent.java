package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when a renderer-initiated navigation is requested. Navigation may still be cancelled after the event is issued.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameRequestedNavigationEvent {

    /**
    * Id of the frame that is being navigated.
    */
    private String frameId;

    /**
    * The reason for the navigation.
    */
    private String reason;

    /**
    * The destination URL for the requested navigation.
    */
    private String url;



}