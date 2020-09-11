package jpuppeteer.cdp.cdp.entity.layertree;

/**
* Sticky position constraints.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StickyPositionConstraint {

    /**
    * Layout rectangle of the sticky element before being shifted
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect stickyBoxRect;

    /**
    * Layout rectangle of the containing block of the sticky element
    */
    private jpuppeteer.cdp.cdp.entity.dom.Rect containingBlockRect;

    /**
    * The nearest sticky layer that shifts the sticky box
    */
    private String nearestLayerShiftingStickyBox;

    /**
    * The nearest sticky layer that shifts the containing block
    */
    private String nearestLayerShiftingContainingBlock;



}