package jpuppeteer.cdp.cdp.entity.page;

/**
* Information about the Frame hierarchy.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameTree {

    /**
    * Frame information for this tree item.
    */
    private jpuppeteer.cdp.cdp.entity.page.Frame frame;

    /**
    * Child frames.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.page.FrameTree> childFrames;



}