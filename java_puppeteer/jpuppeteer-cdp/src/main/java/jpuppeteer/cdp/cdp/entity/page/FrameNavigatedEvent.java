package jpuppeteer.cdp.cdp.entity.page;

/**
* Fired once navigation of the frame has completed. Frame is now associated with the new loader.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FrameNavigatedEvent {

    /**
    * Frame object.
    */
    private jpuppeteer.cdp.cdp.entity.page.Frame frame;



}