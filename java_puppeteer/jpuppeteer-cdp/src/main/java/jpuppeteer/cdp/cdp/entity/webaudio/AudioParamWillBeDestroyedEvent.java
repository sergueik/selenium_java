package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that an existing AudioParam has been destroyed.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioParamWillBeDestroyedEvent {

    /**
    */
    private String contextId;

    /**
    */
    private String nodeId;

    /**
    */
    private String paramId;



}