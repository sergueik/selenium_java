package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Coverage data for a JavaScript function.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FunctionCoverage {

    /**
    * JavaScript function name.
    */
    private String functionName;

    /**
    * Source ranges inside the function with coverage data.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.CoverageRange> ranges;

    /**
    * Whether coverage data for this function has block granularity.
    */
    private Boolean isBlockCoverage;



}