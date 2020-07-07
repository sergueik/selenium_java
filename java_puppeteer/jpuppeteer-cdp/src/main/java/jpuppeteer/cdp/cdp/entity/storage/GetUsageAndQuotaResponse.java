package jpuppeteer.cdp.cdp.entity.storage;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetUsageAndQuotaResponse {

    /**
    * Storage usage (bytes).
    */
    private Double usage;

    /**
    * Storage quota (bytes).
    */
    private Double quota;

    /**
    * Storage usage per type (bytes).
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.storage.UsageForType> usageBreakdown;



}