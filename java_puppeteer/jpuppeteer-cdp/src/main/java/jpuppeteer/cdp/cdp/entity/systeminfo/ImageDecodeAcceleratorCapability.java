package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Describes a supported image decoding profile with its associated minimum and maximum resolutions and subsampling.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ImageDecodeAcceleratorCapability {

    /**
    * Image coded, e.g. Jpeg.
    */
    private String imageType;

    /**
    * Maximum supported dimensions of the image in pixels.
    */
    private jpuppeteer.cdp.cdp.entity.systeminfo.Size maxDimensions;

    /**
    * Minimum supported dimensions of the image in pixels.
    */
    private jpuppeteer.cdp.cdp.entity.systeminfo.Size minDimensions;

    /**
    * Optional array of supported subsampling formats, e.g. 4:2:0, if known.
    */
    private java.util.List<String> subsamplings;



}