package jpuppeteer.cdp.cdp.entity.runtime;

/**
* Stack entry for runtime errors and assertions.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CallFrame {

    /**
    * JavaScript function name.
    */
    private String functionName;

    /**
    * JavaScript script id.
    */
    private String scriptId;

    /**
    * JavaScript script name or url.
    */
    private String url;

    /**
    * JavaScript script line number (0-based).
    */
    private Integer lineNumber;

    /**
    * JavaScript script column number (0-based).
    */
    private Integer columnNumber;



}