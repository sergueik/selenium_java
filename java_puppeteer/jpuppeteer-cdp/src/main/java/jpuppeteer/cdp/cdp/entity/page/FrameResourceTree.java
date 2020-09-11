package jpuppeteer.cdp.cdp.entity.page;

/**
* Information about the Frame hierarchy along with their cached resources.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameResourceTree {

    /**
    * Frame information for this tree item.
    */
    private jpuppeteer.cdp.cdp.entity.page.Frame frame;

    /**
    * Child frames.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.page.FrameResourceTree> childFrames;

    /**
    * Information about frame resources.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.page.FrameResource> resources;



}