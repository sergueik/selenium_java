package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHeapUsageResponse {

    /**
    * Used heap size in bytes.
    */
    private Double usedSize;

    /**
    * Allocated heap size in bytes.
    */
    private Double totalSize;



}