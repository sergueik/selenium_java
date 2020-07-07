package jpuppeteer.cdp.cdp.entity.page;

/**
* Viewport for capturing screenshot.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Viewport {

    /**
    * X offset in device independent pixels (dip).
    */
    private Double x;

    /**
    * Y offset in device independent pixels (dip).
    */
    private Double y;

    /**
    * Rectangle width in device independent pixels (dip).
    */
    private Double width;

    /**
    * Rectangle height in device independent pixels (dip).
    */
    private Double height;

    /**
    * Page scale factor.
    */
    private Double scale;



}