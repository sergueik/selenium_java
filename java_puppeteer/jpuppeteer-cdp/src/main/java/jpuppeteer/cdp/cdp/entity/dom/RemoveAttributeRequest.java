package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RemoveAttributeRequest {

    /**
    * Id of the element to remove attribute from.
    */
    private Integer nodeId;

    /**
    * Name of the attribute to remove.
    */
    private String name;



}