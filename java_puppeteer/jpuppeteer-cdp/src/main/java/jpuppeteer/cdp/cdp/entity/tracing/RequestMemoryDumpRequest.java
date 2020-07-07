package jpuppeteer.cdp.cdp.entity.tracing;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestMemoryDumpRequest {

    /**
    * Enables more deterministic results by forcing garbage collection
    */
    private Boolean deterministic;



}