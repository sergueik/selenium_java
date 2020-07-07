package jpuppeteer.cdp.cdp.entity.headlessexperimental;

/**
* Encoding options for a screenshot.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScreenshotParams {

    /**
    * Image compression format (defaults to png).
    */
    private String format;

    /**
    * Compression quality from range [0..100] (jpeg only).
    */
    private Integer quality;



}