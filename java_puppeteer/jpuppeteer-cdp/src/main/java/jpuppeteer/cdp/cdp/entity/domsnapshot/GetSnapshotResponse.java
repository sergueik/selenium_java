package jpuppeteer.cdp.cdp.entity.domsnapshot;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetSnapshotResponse {

    /**
    * The nodes in the DOM tree. The DOMNode at index 0 corresponds to the root document.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domsnapshot.DOMNode> domNodes;

    /**
    * The nodes in the layout tree.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domsnapshot.LayoutTreeNode> layoutTreeNodes;

    /**
    * Whitelisted ComputedStyle properties for each node in the layout tree.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.domsnapshot.ComputedStyle> computedStyles;



}