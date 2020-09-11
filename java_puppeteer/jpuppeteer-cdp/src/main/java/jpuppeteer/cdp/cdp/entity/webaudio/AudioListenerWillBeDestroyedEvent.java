package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that a new AudioListener has been created.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioListenerWillBeDestroyedEvent {

    /**
    */
    private String contextId;

    /**
    */
    private String listenerId;



}