package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HighlightFrameRequest {

    /**
    * Identifier of the frame to highlight.
    */
    private String frameId;

    /**
    * The content box highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA contentColor;

    /**
    * The content box highlight outline color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA contentOutlineColor;



}