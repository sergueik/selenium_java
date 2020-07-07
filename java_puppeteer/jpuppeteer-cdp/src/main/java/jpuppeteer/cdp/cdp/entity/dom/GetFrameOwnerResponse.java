package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetFrameOwnerResponse {

    /**
    * Resulting node.
    */
    private Integer backendNodeId;

    /**
    * Id of the node at given coordinates, only when enabled and requested document.
    */
    private Integer nodeId;



}