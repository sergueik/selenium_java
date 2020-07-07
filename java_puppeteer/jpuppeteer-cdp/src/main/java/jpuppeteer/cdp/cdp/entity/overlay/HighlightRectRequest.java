package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HighlightRectRequest {

    /**
    * X coordinate
    */
    private Integer x;

    /**
    * Y coordinate
    */
    private Integer y;

    /**
    * Rectangle width
    */
    private Integer width;

    /**
    * Rectangle height
    */
    private Integer height;

    /**
    * The highlight fill color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA color;

    /**
    * The highlight outline color (default: transparent).
    */
    private jpuppeteer.cdp.cdp.entity.dom.RGBA outlineColor;



}