package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetWindowBoundsResponse {

    /**
    * Bounds information of the window. When window state is 'minimized', the restored window position and size are returned.
    */
    private jpuppeteer.cdp.cdp.entity.browser.Bounds bounds;



}