package jpuppeteer.cdp.cdp.entity.overlay;

/**
* Fired when user asks to capture screenshot of some area on the page.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScreenshotRequestedEvent {

    /**
    * Viewport to capture, in device independent pixels (dip).
    */
    private jpuppeteer.cdp.cdp.entity.page.Viewport viewport;



}