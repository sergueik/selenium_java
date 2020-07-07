package jpuppeteer.cdp.cdp.entity.debugger;

/**
* Fired when breakpoint is resolved to an actual script and location.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BreakpointResolvedEvent {

    /**
    * Breakpoint unique identifier.
    */
    private String breakpointId;

    /**
    * Actual breakpoint location.
    */
    private jpuppeteer.cdp.cdp.entity.debugger.Location location;



}