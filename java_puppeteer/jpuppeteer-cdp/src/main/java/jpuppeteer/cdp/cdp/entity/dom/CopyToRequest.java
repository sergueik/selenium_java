package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class CopyToRequest {

    /**
    * Id of the node to copy.
    */
    private Integer nodeId;

    /**
    * Id of the element to drop the copy into.
    */
    private Integer targetNodeId;

    /**
    * Drop the copy before this node (if absent, the copy becomes the last child of `targetNodeId`).
    */
    private Integer insertBeforeNodeId;



}