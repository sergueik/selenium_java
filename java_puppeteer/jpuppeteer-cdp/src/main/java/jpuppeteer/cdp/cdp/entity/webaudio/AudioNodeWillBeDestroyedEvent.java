package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that an existing AudioNode has been destroyed.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioNodeWillBeDestroyedEvent {

    /**
    */
    private String contextId;

    /**
    */
    private String nodeId;



}