package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPartialAXTreeRequest {

    /**
    * Identifier of the node to get the partial accessibility tree for.
    */
    private Integer nodeId;

    /**
    * Identifier of the backend node to get the partial accessibility tree for.
    */
    private Integer backendNodeId;

    /**
    * JavaScript object id of the node wrapper to get the partial accessibility tree for.
    */
    private String objectId;

    /**
    * Whether to fetch this nodes ancestors, siblings and children. Defaults to true.
    */
    private Boolean fetchRelatives;



}