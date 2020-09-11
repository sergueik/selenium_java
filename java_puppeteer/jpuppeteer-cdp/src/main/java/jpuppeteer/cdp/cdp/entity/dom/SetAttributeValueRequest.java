package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetAttributeValueRequest {

    /**
    * Id of the element to set attribute for.
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