package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that two AudioNodes are connected.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NodesConnectedEvent {

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