package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBreakpointByUrlRequest {

    /**
    * Line number to set breakpoint at.
    */
    private Integer lineNumber;

    /**
    * URL of the resources to set breakpoint on.
    */
    private String url;

    /**
    * Regex pattern for the URLs of the resources to set breakpoints on. Either `url` or `urlRegex` must be specified.
    */
    private String urlRegex;

    /**
    * Script hash of the resources to set breakpoint on.
    */
    private String scriptHash;

    /**
    * Offset in the line to set breakpoint at.
    */
    private Integer columnNumber;

    /**
    * Expression to use as a breakpoint condition. When specified, debugger will only stop on the breakpoint if this expression evaluates to true.
    */
    private String condition;



}