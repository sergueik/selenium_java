package jpuppeteer.cdp.cdp.entity.runtime;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CompileScriptRequest {

    /**
    * Expression to compile.
    */
    private String expression;

    /**
    * Source url to be set for the script.
    */
    private String sourceURL;

    /**
    * Specifies whether the compiled script should be persisted.
    */
    private Boolean persistScript;

    /**
    * Specifies in which execution context to perform script run. If the parameter is omitted the evaluation will be performed in the context of the inspected page.
    */
    private Integer executionContextId;



}