package jpuppeteer.cdp.cdp.entity.profiler;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetRuntimeCallStatsResponse {

    /**
    * Collected counter information.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.CounterInfo> result;



}