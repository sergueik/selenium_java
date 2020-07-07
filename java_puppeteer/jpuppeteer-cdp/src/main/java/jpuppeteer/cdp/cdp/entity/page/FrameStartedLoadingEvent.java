package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when frame has started loading.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameStartedLoadingEvent {

    /**
    * Id of the frame that has started loading.
    */
    private String frameId;



}