package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetPartialAXTreeResponse {

    /**
    * The `Accessibility.AXNode` for this DOM node, if it exists, plus its ancestors, siblings and children, if requested.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.accessibility.AXNode> nodes;



}