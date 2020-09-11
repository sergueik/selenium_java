package jpuppeteer.cdp.cdp.entity.headlessexperimental;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BeginFrameRequest {

    /**
    * Timestamp of this BeginFrame in Renderer TimeTicks (milliseconds of uptime). If not set, the current time will be used.
    */
    private Double frameTimeTicks;

    /**
    * The interval between BeginFrames that is reported to the compositor, in milliseconds. Defaults to a 60 frames/second interval, i.e. about 16.666 milliseconds.
    */
    private Double interval;

    /**
    * Whether updates should not be committed and drawn onto the display. False by default. If true, only side effects of the BeginFrame will be run, such as layout and animations, but any visual updates may not be visible on the display or in screenshots.
    */
    private Boolean noDisplayUpdates;

    /**
    * If set, a screenshot of the frame will be captured and returned in the response. Otherwise, no screenshot will be captured. Note that capturing a screenshot can fail, for example, during renderer initialization. In such a case, no screenshot data will be returned.
    */
    private jpuppeteer.cdp.cdp.entity.headlessexperimental.ScreenshotParams screenshot;



}