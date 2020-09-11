package jpuppeteer.cdp.cdp.entity.headlessexperimental;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BeginFrameResponse {

    /**
    * Whether the BeginFrame resulted in damage and, thus, a new frame was committed to the display. Reported for diagnostic uses, may be removed in the future.
    */
    private Boolean hasDamage;

    /**
    * Base64-encoded image data of the screenshot, if one was requested and successfully taken.
    */
    private String screenshotData;



}