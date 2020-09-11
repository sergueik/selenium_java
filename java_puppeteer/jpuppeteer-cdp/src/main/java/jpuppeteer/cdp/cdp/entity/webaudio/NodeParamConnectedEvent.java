package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that an AudioNode is connected to an AudioParam.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NodeParamConnectedEvent {

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