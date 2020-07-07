package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class FocusRequest {

    /**
    * Identifier of the node.
    */
    private Integer nodeId;

    /**
    * Identifier of the backend node.
    */
    private Integer backendNodeId;

    /**
    * JavaScript object id of the node wrapper.
    */
    private String objectId;



}