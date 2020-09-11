package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBreakpointOnFunctionCallRequest {

    /**
    * Function object id.
    */
    private String objectId;

    /**
    * Expression to use as a breakpoint condition. When specified, debugger will stop on the breakpoint if this expression evaluates to true.
    */
    private String condition;



}