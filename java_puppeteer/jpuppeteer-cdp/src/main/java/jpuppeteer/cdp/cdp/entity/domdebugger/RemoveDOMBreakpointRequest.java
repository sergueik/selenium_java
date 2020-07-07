package jpuppeteer.cdp.cdp.entity.domdebugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RemoveDOMBreakpointRequest {

    /**
    * Identifier of the node to remove breakpoint from.
    */
    private Integer nodeId;

    /**
    * Type of the breakpoint to remove.
    */
    private String type;



}