package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetSnapshotRequest {

    /**
    * Whitelist of computed styles to return.
    */
    private java.util.List<String> computedStyleWhitelist;

    /**
    * Whether or not to retrieve details of DOM listeners (default false).
    */
    private Boolean includeEventListeners;

    /**
    * Whether to determine and include the paint order index of LayoutTreeNodes (default false).
    */
    private Boolean includePaintOrder;

    /**
    * Whether to include UA shadow tree in the snapshot (default false).
    */
    private Boolean includeUserAgentShadowTree;



}