package jpuppeteer.cdp.cdp.entity.dom;

/**
* Fired when `Container`'s child node count has changed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ChildNodeCountUpdatedEvent {

    /**
    * Id of the node that has changed.
    */
    private Integer nodeId;

    /**
    * New node count.
    */
    private Integer childNodeCount;



}