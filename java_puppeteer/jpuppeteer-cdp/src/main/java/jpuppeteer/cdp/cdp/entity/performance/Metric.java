package jpuppeteer.cdp.cdp.entity.performance;

/**
* Run-time execution metric.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Metric {

    /**
    * Metric name.
    */
    private String name;

    /**
    * Metric value.
    */
    private Double value;



}