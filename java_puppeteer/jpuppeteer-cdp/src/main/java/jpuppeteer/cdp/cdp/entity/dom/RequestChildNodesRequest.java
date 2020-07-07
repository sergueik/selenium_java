package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RequestChildNodesRequest {

    /**
    * Id of the node to get children for.
    */
    private Integer nodeId;

    /**
    * The maximum depth at which children should be retrieved, defaults to 1. Use -1 for the entire subtree or provide an integer larger than 0.
    */
    private Integer depth;

    /**
    * Whether or not iframes and shadow roots should be traversed when returning the sub-tree (default is false).
    */
    private Boolean pierce;



}