package jpuppeteer.cdp.cdp.entity.webaudio;

/**
* Notifies that a new AudioParam has been created.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AudioParamCreatedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.webaudio.AudioParam param;



}