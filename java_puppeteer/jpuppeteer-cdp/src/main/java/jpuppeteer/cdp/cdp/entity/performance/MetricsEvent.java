package jpuppeteer.cdp.cdp.entity.performance;

/**
* Current values of the metrics.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class MetricsEvent {

    /**
    * Current values of the metrics.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.performance.Metric> metrics;

    /**
    * Timestamp title.
    */
    private String title;



}