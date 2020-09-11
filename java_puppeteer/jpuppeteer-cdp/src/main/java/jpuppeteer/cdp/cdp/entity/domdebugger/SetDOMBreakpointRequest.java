package jpuppeteer.cdp.cdp.entity.domdebugger;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDOMBreakpointRequest {

    /**
    * Identifier of the node to set breakpoint on.
    */
    private Integer nodeId;

    /**
    * Type of the operation to stop upon.
    */
    private String type;



}