package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetInfoResponse {

    /**
    * Information about the GPUs on the system.
    */
    private jpuppeteer.cdp.cdp.entity.systeminfo.GPUInfo gpu;

    /**
    * A platform-dependent description of the model of the machine. On Mac OS, this is, for example, 'MacBookPro'. Will be the empty string if not supported.
    */
    private String modelName;

    /**
    * A platform-dependent description of the version of the machine. On Mac OS, this is, for example, '10.1'. Will be the empty string if not supported.
    */
    private String modelVersion;

    /**
    * The command line string used to launch the browser. Will be the empty string if not supported.
    */
    private String commandLine;



}