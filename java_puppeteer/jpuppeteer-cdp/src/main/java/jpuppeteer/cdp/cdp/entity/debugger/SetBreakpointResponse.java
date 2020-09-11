package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBreakpointResponse {

    /**
    * Id of the created breakpoint for further reference.
    */
    private String breakpointId;

    /**
    * Location this breakpoint resolved into.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location actualLocation;



}