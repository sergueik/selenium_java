package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HighlightQuadRequest {

    /**
    * Quad to highlight
    */
    private java.util.List<Double> quad;

    /**
    * The highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA color;

    /**
    * The highlight outline color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA outlineColor;



}