package jpuppeteer.cdp.cdp.entity.dom;

/**
* Mirrors `DOMNodeRemoved` event.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ChildNodeRemovedEvent {

    /**
    * Parent id.
    */
    private Integer parentNodeId;

    /**
    * Id of the node that has been removed.
    */
    private Integer nodeId;



}