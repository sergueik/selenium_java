package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* Describes a single graphics processor (GPU).
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GPUDevice {

    /**
    * PCI ID of the GPU vendor, if available; 0 otherwise.
    */
    private Double vendorId;

    /**
    * PCI ID of the GPU device, if available; 0 otherwise.
    */
    private Double deviceId;

    /**
    * Sub sys ID of the GPU, only available on Windows.
    */
    private Double subSysId;

    /**
    * Revision of the GPU, only available on Windows.
    */
    private Double revision;

    /**
    * String description of the GPU vendor, if the PCI ID is not available.
    */
    private String vendorString;

    /**
    * String description of the GPU device, if the PCI ID is not available.
    */
    private String deviceString;

    /**
    * String description of the GPU driver vendor.
    */
    private String driverVendor;

    /**
    * String description of the GPU driver version.
    */
    private String driverVersion;



}