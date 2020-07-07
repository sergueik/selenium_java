package jpuppeteer.cdp.cdp.entity.overlay;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class HighlightNodeRequest {

    /**
    * A descriptor for the highlight appearance.
    */
    private jpuppeteer.cdp.cdp.entity.overlay.HighlightConfig highlightConfig;

    /**
    * Identifier of the node to highlight.
    */
    private Integer nodeId;

    /**
    * Identifier of the backend node to highlight.
    */
    private Integer backendNodeId;

    /**
    * JavaScript object id of the node to be highlighted.
    */
    private String objectId;

    /**
    * Selectors to highlight relevant nodes.
    */
    private String selector;



}