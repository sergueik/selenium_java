package jpuppeteer.cdp.cdp.entity.emulation;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDeviceMetricsOverrideRequest {

    /**
    * Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.
    */
    private Integer width;

    /**
    * Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.
    */
    private Integer height;

    /**
    * Overriding device scale factor value. 0 disables the override.
    */
    private Double deviceScaleFactor;

    /**
    * Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text autosizing and more.
    */
    private Boolean mobile;

    /**
    * Scale to apply to resulting view image.
    */
    private Double scale;

    /**
    * Overriding screen width value in pixels (minimum 0, maximum 10000000).
    */
    private Integer screenWidth;

    /**
    * Overriding screen height value in pixels (minimum 0, maximum 10000000).
    */
    private Integer screenHeight;

    /**
    * Overriding view X position on screen in pixels (minimum 0, maximum 10000000).
    */
    private Integer positionX;

    /**
    * Overriding view Y position on screen in pixels (minimum 0, maximum 10000000).
    */
    private Integer positionY;

    /**
    * Do not set visible view size, rely upon explicit setVisibleSize call.
    */
    private Boolean dontSetVisibleSize;

    /**
    * Screen orientation override.
    */
    private jpuppeteer.cdp.cdp.entity.emulation.ScreenOrientation screenOrientation;

    /**
    * If set, the visible area of the page will be overridden to this viewport. This viewport change is not observed by the page, e.g. viewport-relative elements do not change positions.
    */
    private jpuppeteer.cdp.cdp.entity.page.Viewport viewport;



}