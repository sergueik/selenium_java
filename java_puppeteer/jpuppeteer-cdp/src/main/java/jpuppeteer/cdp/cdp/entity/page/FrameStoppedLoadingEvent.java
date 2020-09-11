package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired when frame has stopped loading.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameStoppedLoadingEvent {

    /**
    * Id of the frame that has stopped loading.
    */
    private String frameId;



}