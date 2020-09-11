package jpuppeteer.cdp.cdp.entity.systeminfo;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetProcessInfoResponse {

    /**
    * An array of process info blocks.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.systeminfo.ProcessInfo> processInfo;



}