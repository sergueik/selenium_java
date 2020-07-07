package jpuppeteer.cdp.cdp.entity.page;

/**
* Layout viewport position and dimensions.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LayoutViewport {

    /**
    * Horizontal offset relative to the document (CSS pixels).
    */
    private Integer pageX;

    /**
    * Vertical offset relative to the document (CSS pixels).
    */
    private Integer pageY;

    /**
    * Width (CSS pixels), excludes scrollbar if present.
    */
    private Integer clientWidth;

    /**
    * Height (CSS pixels), excludes scrollbar if present.
    */
    private Integer clientHeight;



}