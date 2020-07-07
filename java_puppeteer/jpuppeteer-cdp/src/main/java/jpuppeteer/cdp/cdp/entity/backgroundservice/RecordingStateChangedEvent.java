package jpuppeteer.cdp.cdp.entity.backgroundservice;

/**
* Called when the recording state for the service has been updated.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RecordingStateChangedEvent {

    /**
    */
    private Boolean isRecording;

    /**
    */
    private String service;



}