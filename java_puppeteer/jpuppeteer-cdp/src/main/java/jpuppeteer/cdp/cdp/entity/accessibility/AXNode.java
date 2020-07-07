package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* A node in the accessibility tree.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AXNode {

    /**
    * Unique identifier for this node.
    */
    private String nodeId;

    /**
    * Whether this node is ignored for accessibility
    */
    private Boolean ignored;

    /**
    * Collection of reasons why this node is hidden.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.accessibility.AXProperty> ignoredReasons;

    /**
    * This `Node`'s role, whether explicit or implicit.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue role;

    /**
    * The accessible name for this `Node`.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue name;

    /**
    * The accessible description for this `Node`.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue description;

    /**
    * The value for this `Node`.
    */
    private jpuppeteer.cdp.cdp.entity.accessibility.AXValue value;

    /**
    * All other properties
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.accessibility.AXProperty> properties;

    /**
    * IDs for each of this node's child nodes.
    */
    private java.util.List<String> childIds;

    /**
    * The backend ID for the associated DOM node, if any.
    */
    private Integer backendDOMNodeId;



}