package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when frame schedules a potential navigation.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameScheduledNavigationEvent {

    /**
    * Id of the frame that has scheduled a navigation.
    */
    private String frameId;

    /**
    * Delay (in seconds) until the navigation is scheduled to begin. The navigation is not guaranteed to start.
    */
    private Double delay;

    /**
    * The reason for the navigation.
    */
    private String reason;

    /**
    * The destination URL for the scheduled navigation.
    */
    private String url;



}