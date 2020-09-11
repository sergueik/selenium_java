package jpuppeteer.cdp.cdp.entity.dom;

/**
* Box model.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BoxModel {

    /**
    * Content box
    */
    private java.util.List<Double> content;

    /**
    * Padding box
    */
    private java.util.List<Double> padding;

    /**
    * Border box
    */
    private java.util.List<Double> border;

    /**
    * Margin box
    */
    private java.util.List<Double> margin;

    /**
    * Node width
    */
    private Integer width;

    /**
    * Node height
    */
    private Integer height;

    /**
    * Shape outside coordinates
    */
    private jpuppeteer.cdp.cdp.entity.dom.ShapeOutsideInfo shapeOutside;



}