package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPossibleBreakpointsResponse {

    /**
    * List of the possible breakpoint locations.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.debugger.BreakLocation> locations;



}