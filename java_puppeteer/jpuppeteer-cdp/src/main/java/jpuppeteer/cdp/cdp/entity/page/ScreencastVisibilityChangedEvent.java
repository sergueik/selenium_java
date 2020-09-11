package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when the page with currently enabled screencast was shown or hidden `.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScreencastVisibilityChangedEvent {

    /**
    * True if the page is visible.
    */
    private Boolean visible;



}