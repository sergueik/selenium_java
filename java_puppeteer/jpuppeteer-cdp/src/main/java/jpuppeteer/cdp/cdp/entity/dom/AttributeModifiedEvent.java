package jpuppeteer.cdp.cdp.entity.dom;

/**
* Fired when `Element`'s attribute is modified.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AttributeModifiedEvent {

    /**
    * Id of the node that has changed.
    */
    private Integer nodeId;

    /**
    * Attribute name.
    */
    private String name;

    /**
    * Attribute value.
    */
    private String value;



}