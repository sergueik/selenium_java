package jpuppeteer.cdp.cdp.entity.tracing;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestMemoryDumpResponse {

    /**
    * GUID of the resulting global memory dump.
    */
    private String dumpGuid;

    /**
    * True iff the global memory dump succeeded.
    */
    private Boolean success;



}