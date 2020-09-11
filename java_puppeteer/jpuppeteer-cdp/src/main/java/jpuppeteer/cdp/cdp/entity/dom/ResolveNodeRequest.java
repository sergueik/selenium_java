package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ResolveNodeRequest {

    /**
    * Id of the node to resolve.
    */
    private Integer nodeId;

    /**
    * Backend identifier of the node to resolve.
    */
    private Integer backendNodeId;

    /**
    * Symbolic group name that can be used to release multiple objects.
    */
    private String objectGroup;

    /**
    * Execution context in which to resolve the node.
    */
    private Integer executionContextId;



}