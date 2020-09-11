package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBreakpointRequest {

    /**
    * Location to set breakpoint in.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location location;

    /**
    * Expression to use as a breakpoint condition. When specified, debugger will only stop on the breakpoint if this expression evaluates to true.
    */
    private String condition;



}