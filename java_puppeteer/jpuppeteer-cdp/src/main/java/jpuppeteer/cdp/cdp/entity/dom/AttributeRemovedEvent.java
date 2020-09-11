package jpuppeteer.cdp.cdp.entity.dom;

/**
* Fired when `Element`'s attribute is removed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AttributeRemovedEvent {

    /**
    * Id of the node that has changed.
    */
    private Integer nodeId;

    /**
    * A ttribute name.
    */
    private String name;



}