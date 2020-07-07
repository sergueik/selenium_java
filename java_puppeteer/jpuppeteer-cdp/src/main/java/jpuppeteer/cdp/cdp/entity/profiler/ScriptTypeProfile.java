package jpuppeteer.cdp.cdp.entity.profiler;

/**
* Type profile data collected during runtime for a JavaScript script.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScriptTypeProfile {

    /**
    * JavaScript script id.
    */
    private String scriptId;

    /**
    * JavaScript script name or url.
    */
    private String url;

    /**
    * Type profile entries for parameters and return values of the functions in the script.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.TypeProfileEntry> entries;



}