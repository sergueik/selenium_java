package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that AudioNodes are disconnected. The destination can be null, and it means all the outgoing connections from the source are disconnected.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NodesDisconnectedEvent {

    /**
    */
    private String contextId;

    /**
    */
    private String sourceId;

    /**
    */
    private String destinationId;

    /**
    */
    private Double sourceOutputIndex;

    /**
    */
    private Double destinationInputIndex;



}