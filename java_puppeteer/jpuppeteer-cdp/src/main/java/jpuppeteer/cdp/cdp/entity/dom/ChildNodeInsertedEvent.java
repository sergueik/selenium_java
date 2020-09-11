package jpuppeteer.cdp.cdp.entity.dom;

/**
* Mirrors `DOMNodeInserted` event.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ChildNodeInsertedEvent {

    /**
    * Id of the node that has changed.
    */
    private Integer parentNodeId;

    /**
    * If of the previous siblint.
    */
    private Integer previousNodeId;

    /**
    * Inserted node data.
    */
    private jpuppeteer.cdp.cdp.entity.dom.Node node;



}