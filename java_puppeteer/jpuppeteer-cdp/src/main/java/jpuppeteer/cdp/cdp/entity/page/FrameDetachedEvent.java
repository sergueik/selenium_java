package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when frame has been detached from its parent.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameDetachedEvent {

    /**
    * Id of the frame that has been detached.
    */
    private String frameId;



}