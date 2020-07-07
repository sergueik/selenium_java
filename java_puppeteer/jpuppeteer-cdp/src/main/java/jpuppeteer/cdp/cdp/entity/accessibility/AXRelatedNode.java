package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AXRelatedNode {

    /**
    * The BackendNodeId of the related DOM node.
    */
    private Integer backendDOMNodeId;

    /**
    * The IDRef value provided, if any.
    */
    private String idref;

    /**
    * The text alternative of this node in the current context.
    */
    private String text;



}