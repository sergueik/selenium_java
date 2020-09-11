package jpuppeteer.cdp.cdp.entity.performance;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetMetricsResponse {

    /**
    * Current values for run-time metrics.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.performance.Metric> metrics;



}