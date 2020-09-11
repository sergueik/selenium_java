package jpuppeteer.cdp.cdp.entity.profiler;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetBestEffortCoverageResponse {

    /**
    * Coverage data for the current isolate.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.ScriptCoverage> result;



}