package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class MoveToRequest {

    /**
    * Id of the node to move.
    */
    private Integer nodeId;

    /**
    * Id of the element to drop the moved node into.
    */
    private Integer targetNodeId;

    /**
    * Drop node before this one (if absent, the moved node becomes the last child of `targetNodeId`).
    */
    private Integer insertBeforeNodeId;



}