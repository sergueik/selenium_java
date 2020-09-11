package jpuppeteer.cdp.cdp.entity.layertree;

/**
* Rectangle where scrolling happens on the main thread.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScrollRect {

    /**
    * Rectangle itself.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect rect;

    /**
    * Reason for rectangle to force scrolling on the main thread
    */
    private String type;



}