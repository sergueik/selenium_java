package jpuppeteer.cdp.cdp.entity.overlay;

/**
* Fired when the node should be inspected. This happens after call to `setInspectMode` or when user manually inspects an element.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class InspectNodeRequestedEvent {

    /**
    * Id of the node to inspect.
    */
    private Integer backendNodeId;



}