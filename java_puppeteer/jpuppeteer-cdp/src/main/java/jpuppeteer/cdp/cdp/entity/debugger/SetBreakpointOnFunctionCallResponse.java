package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBreakpointOnFunctionCallResponse {

    /**
    * Id of the created breakpoint for further reference.
    */
    private String breakpointId;



}