package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Describes a supported video encoding profile with its associated maximum resolution and maximum framerate.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class VideoEncodeAcceleratorCapability {

    /**
    * Video codec profile that is supported, e.g H264 Main.
    */
    private String profile;

    /**
    * Maximum video dimensions in pixels supported for this |profile|.
    */
    private jpuppeteer.cdp.cdp.entity.systeminfo.Size maxResolution;

    /**
    * Maximum encoding framerate in frames per second supported for this |profile|, as fraction's numerator and denominator, e.g. 24/1 fps, 24000/1001 fps, etc.
    */
    private Integer maxFramerateNumerator;

    /**
    */
    private Integer maxFramerateDenominator;



}