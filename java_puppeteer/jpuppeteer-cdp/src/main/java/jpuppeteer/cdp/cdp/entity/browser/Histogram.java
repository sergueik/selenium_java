package jpuppeteer.cdp.cdp.entity.browser;

/**
* Chrome histogram.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Histogram {

    /**
    * Name.
    */
    private String name;

    /**
    * Sum of sample values.
    */
    private Integer sum;

    /**
    * Total number of samples.
    */
    private Integer count;

    /**
    * Buckets.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.browser.Bucket> buckets;



}