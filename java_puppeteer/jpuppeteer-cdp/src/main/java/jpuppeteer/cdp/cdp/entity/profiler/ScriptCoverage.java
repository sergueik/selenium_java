package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Coverage data for a JavaScript script.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScriptCoverage {

    /**
    * JavaScript script id.
    */
    private String scriptId;

    /**
    * JavaScript script name or url.
    */
    private String url;

    /**
    * Functions contained in the script that has coverage data.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.FunctionCoverage> functions;



}