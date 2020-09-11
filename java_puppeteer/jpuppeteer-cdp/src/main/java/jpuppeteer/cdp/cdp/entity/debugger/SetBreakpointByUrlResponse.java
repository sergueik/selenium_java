package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetBreakpointByUrlResponse {

    /**
    * Id of the created breakpoint for further reference.
    */
    private String breakpointId;

    /**
    * List of the locations this breakpoint resolved into upon addition.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.Location> locations;



}