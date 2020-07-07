package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Fired when virtual machine parses script. This event is also fired for all known and uncollected scripts upon enabling debugger.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ScriptParsedEvent {

    /**
    * Identifier of the script parsed.
    */
    private String scriptId;

    /**
    * URL or name of the script parsed (if any).
    */
    private String url;

    /**
    * Line offset of the script within the resource with given URL (for script tags).
    */
    private Integer startLine;

    /**
    * Column offset of the script within the resource with given URL.
    */
    private Integer startColumn;

    /**
    * Last line of the script.
    */
    private Integer endLine;

    /**
    * Length of the last line of the script.
    */
    private Integer endColumn;

    /**
    * Specifies script creation context.
    */
    private Integer executionContextId;

    /**
    * Content hash of the script.
    */
    private String hash;

    /**
    * Embedder-specific auxiliary data.
    */
    private java.util.Map<String, Object> executionContextAuxData;

    /**
    * True, if this script is generated as a result of the live edit operation.
    */
    private Boolean isLiveEdit;

    /**
    * URL of source map associated with script (if any).
    */
    private String sourceMapURL;

    /**
    * True, if this script has sourceURL.
    */
    private Boolean hasSourceURL;

    /**
    * True, if this script is ES6 module.
    */
    private Boolean isModule;

    /**
    * This script length.
    */
    private Integer length;

    /**
    * JavaScript top stack frame of where the script parsed event was triggered if available.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.StackTrace stackTrace;



}