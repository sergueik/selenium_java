package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that a new BaseAudioContext has been created.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContextCreatedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.webaudio.BaseAudioContext context;



}