package jpuppeteer.cdp.cdp.entity.dom;

/**
* CSS Shape Outside details.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ShapeOutsideInfo {

    /**
    * Shape bounds
    */
    private java.util.List<Double> bounds;

    /**
    * Shape coordinate details
    */
    private java.util.List<Object> shape;

    /**
    * Margin shape bounds
    */
    private java.util.List<Object> marginShape;



}