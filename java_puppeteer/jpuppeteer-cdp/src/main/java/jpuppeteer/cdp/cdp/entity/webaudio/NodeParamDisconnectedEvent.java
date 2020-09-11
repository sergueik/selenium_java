package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that an AudioNode is disconnected to an AudioParam.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NodeParamDisconnectedEvent {

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



}