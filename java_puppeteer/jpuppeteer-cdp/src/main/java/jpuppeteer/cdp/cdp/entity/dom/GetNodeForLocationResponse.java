package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetNodeForLocationResponse {

    /**
    * Resulting node.
    */
    private Integer backendNodeId;

    /**
    * Frame this node belongs to.
    */
    private String frameId;

    /**
    * Id of the node at given coordinates, only when enabled and requested document.
    */
    private Integer nodeId;



}