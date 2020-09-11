package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Provides information about the GPU(s) on the system.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GPUInfo {

    /**
    * The graphics devices on the system. Element 0 is the primary GPU.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.systeminfo.GPUDevice> devices;

    /**
    * An optional dictionary of additional GPU related attributes.
    */
    private java.util.Map<String, Object> auxAttributes;

    /**
    * An optional dictionary of graphics features and their status.
    */
    private java.util.Map<String, Object> featureStatus;

    /**
    * An optional array of GPU driver bug workarounds.
    */
    private java.util.List<String> driverBugWorkarounds;

    /**
    * Supported accelerated video decoding capabilities.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.systeminfo.VideoDecodeAcceleratorCapability> videoDecoding;

    /**
    * Supported accelerated video encoding capabilities.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.systeminfo.VideoEncodeAcceleratorCapability> videoEncoding;

    /**
    * Supported accelerated image decoding capabilities.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.systeminfo.ImageDecodeAcceleratorCapability> imageDecoding;



}