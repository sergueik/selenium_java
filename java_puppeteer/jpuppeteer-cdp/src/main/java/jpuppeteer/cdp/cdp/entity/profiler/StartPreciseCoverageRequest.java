package jpuppeteer.cdp.cdp.entity.profiler;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StartPreciseCoverageRequest {

    /**
    * Collect accurate call counts beyond simple 'covered' or 'not covered'.
    */
    private Boolean callCount;

    /**
    * Collect block-based coverage.
    */
    private Boolean detailed;



}