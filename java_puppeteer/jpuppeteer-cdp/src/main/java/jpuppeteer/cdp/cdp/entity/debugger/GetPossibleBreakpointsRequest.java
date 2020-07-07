package jpuppeteer.cdp.cdp.entity.debugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPossibleBreakpointsRequest {

    /**
    * Start of range to search possible breakpoint locations in.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location start;

    /**
    * End of range to search possible breakpoint locations in (excluding). When not specified, end of scripts is used as end of range.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location end;

    /**
    * Only consider locations which are in the same (non-nested) function as start.
    */
    private Boolean restrictToFunction;



}