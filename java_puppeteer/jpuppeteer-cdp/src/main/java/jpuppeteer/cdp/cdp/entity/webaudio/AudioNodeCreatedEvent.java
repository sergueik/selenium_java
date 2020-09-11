package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that a new AudioNode has been created.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioNodeCreatedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.webaudio.AudioNode node;



}