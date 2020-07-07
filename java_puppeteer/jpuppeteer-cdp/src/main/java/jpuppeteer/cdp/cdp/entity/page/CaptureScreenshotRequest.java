package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CaptureScreenshotRequest {

    /**
    * Image compression format (defaults to png).
    */
    private String format;

    /**
    * Compression quality from range [0..100] (jpeg only).
    */
    private Integer quality;

    /**
    * Capture the screenshot of a given region only.
    */
    private jpuppeteer.cdp.cdp.entity.page.Viewport clip;

    /**
    * Capture the screenshot from the surface, rather than the view. Defaults to true.
    */
    private Boolean fromSurface;



}