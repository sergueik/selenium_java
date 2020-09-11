package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetWindowBoundsRequest {

    /**
    * Browser window id.
    */
    private Integer windowId;

    /**
    * New window bounds. The 'minimized', 'maximized' and 'fullscreen' states cannot be combined with 'left', 'top', 'width' or 'height'. Leaves unspecified fields unchanged.
    */
    private jpuppeteer.cdp.cdp.entity.browser.Bounds bounds;



}