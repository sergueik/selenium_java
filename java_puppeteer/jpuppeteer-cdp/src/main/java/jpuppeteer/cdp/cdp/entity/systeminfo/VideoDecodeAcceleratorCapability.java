package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Describes a supported video decoding profile with its associated minimum and maximum resolutions.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class VideoDecodeAcceleratorCapability {

    /**
    * Video codec profile that is supported, e.g. VP9 Profile 2.
    */
    private String profile;

    /**
    * Maximum video dimensions in pixels supported for this |profile|.
    */
    private jpuppeteer.cdp.cdp.entity.systeminfo.Size maxResolution;

    /**
    * Minimum video dimensions in pixels supported for this |profile|.
    */
    private jpuppeteer.cdp.cdp.entity.systeminfo.Size minResolution;



}