package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that existing BaseAudioContext has changed some properties (id stays the same)..
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContextChangedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.webaudio.BaseAudioContext context;



}