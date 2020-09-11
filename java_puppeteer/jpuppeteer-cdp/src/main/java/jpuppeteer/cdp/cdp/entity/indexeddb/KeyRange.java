package jpuppeteer.cdp.cdp.entity.indexeddb;

/**
* Key range.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class KeyRange {

    /**
    * Lower bound.
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.Key lower;

    /**
    * Upper bound.
    */
    private jpuppeteer.cdp.cdp.entity.indexeddb.Key upper;

    /**
    * If true lower bound is open.
    */
    private Boolean lowerOpen;

    /**
    * If true upper bound is open.
    */
    private Boolean upperOpen;



}