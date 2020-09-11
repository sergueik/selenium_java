package jpuppeteer.cdp.cdp.entity.overlay;

/**
* Fired when the node should be highlighted. This happens after call to `setInspectMode`.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NodeHighlightRequestedEvent {

    /**
    */
    private Integer nodeId;



}