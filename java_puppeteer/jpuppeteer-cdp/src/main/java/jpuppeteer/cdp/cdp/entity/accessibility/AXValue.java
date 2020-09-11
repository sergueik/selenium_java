package jpuppeteer.cdp.cdp.entity.accessibility;

/**
* A single computed AX property.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AXValue {

    /**
    * The type of this value.
    */
    private String type;

    /**
    * The computed value of this property.
    */
    private Object value;

    /**
    * One or more related nodes, if applicable.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.accessibility.AXRelatedNode> relatedNodes;

    /**
    * The sources which contributed to the computation of this property.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.accessibility.AXValueSource> sources;



}