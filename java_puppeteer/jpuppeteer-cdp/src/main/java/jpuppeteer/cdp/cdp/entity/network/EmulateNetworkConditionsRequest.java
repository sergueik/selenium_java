package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class EmulateNetworkConditionsRequest {

    /**
    * True to emulate internet disconnection.
    */
    private Boolean offline;

    /**
    * Minimum latency from request sent to response headers received (ms).
    */
    private Double latency;

    /**
    * Maximal aggregated download throughput (bytes/sec). -1 disables download throttling.
    */
    private Double downloadThroughput;

    /**
    * Maximal aggregated upload throughput (bytes/sec).  -1 disables upload throttling.
    */
    private Double uploadThroughput;

    /**
    * Connection type if known.
    */
    private String connectionType;



}