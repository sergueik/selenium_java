package jpuppeteer.cdp.cdp.entity.dom;

/**
* Fired when backend wants to provide client with the missing DOM structure. This happens upon most of the calls requesting node ids.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetChildNodesEvent {

    /**
    * Parent node id to populate with children.
    */
    private Integer parentId;

    /**
    * Child nodes array.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.dom.Node> nodes;



}