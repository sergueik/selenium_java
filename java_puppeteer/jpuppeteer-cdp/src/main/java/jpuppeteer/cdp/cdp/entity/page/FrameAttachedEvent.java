package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when frame has been attached to its parent.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameAttachedEvent {

    /**
    * Id of the frame that has been attached.
    */
    private String frameId;

    /**
    * Parent frame identifier.
    */
    private String parentFrameId;

    /**
    * JavaScript stack trace of when frame was attached, only set if frame initiated from script.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace stack;



}