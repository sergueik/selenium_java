package jpuppeteer.cdp.cdp.entity.dom;

/**
* Called when distrubution is changed.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DistributedNodesUpdatedEvent {

    /**
    * Insertion point where distrubuted nodes were updated.
    */
    private Integer insertionPointId;

    /**
    * Distributed nodes for given insertion point.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.dom.BackendNode> distributedNodes;



}