package jpuppeteer.cdp.cdp.entity.profiler;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetSamplingIntervalRequest {

    /**
    * New sampling interval in microseconds.
    */
    private Integer interval;



}