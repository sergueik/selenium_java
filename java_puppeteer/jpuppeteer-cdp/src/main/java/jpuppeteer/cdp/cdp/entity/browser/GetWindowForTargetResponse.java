package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetWindowForTargetResponse {

    /**
    * Browser window id.
    */
    private Integer windowId;

    /**
    * Bounds information of the window. When window state is 'minimized', the restored window position and size are returned.
    */
    private jpuppeteer.cdp.cdp.entity.browser.Bounds bounds;



}