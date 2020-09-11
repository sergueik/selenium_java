package jpuppeteer.cdp.cdp.entity.browser;

/**
* Chrome histogram bucket.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Bucket {

    /**
    * Minimum value (inclusive).
    */
    private Integer low;

    /**
    * Maximum value (exclusive).
    */
    private Integer high;

    /**
    * Number of samples.
    */
    private Integer count;



}