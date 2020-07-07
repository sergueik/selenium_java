package jpuppeteer.cdp.cdp.entity.backgroundservice;

/**
* Called with all existing backgroundServiceEvents when enabled, and all new events afterwards if enabled and recording.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BackgroundServiceEventReceivedEvent {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.backgroundservice.BackgroundServiceEvent backgroundServiceEvent;



}