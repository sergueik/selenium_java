package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that an existing BaseAudioContext will be destroyed.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContextWillBeDestroyedEvent {

    /**
    */
    private String contextId;



}