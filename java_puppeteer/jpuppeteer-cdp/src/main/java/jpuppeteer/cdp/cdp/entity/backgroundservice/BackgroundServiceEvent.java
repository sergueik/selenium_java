package jpuppeteer.cdp.cdp.entity.backgroundservice;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BackgroundServiceEvent {

    /**
    * Timestamp of the event (in seconds).
    */
    private Double timestamp;

    /**
    * The origin this event belongs to.
    */
    private String origin;

    /**
    * The Service Worker ID that initiated the event.
    */
    private String serviceWorkerRegistrationId;

    /**
    * The Background Service this event belongs to.
    */
    private String service;

    /**
    * A description of the event.
    */
    private String eventName;

    /**
    * An identifier that groups related events together.
    */
    private String instanceId;

    /**
    * A list of event-specific information.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.backgroundservice.EventMetadata> eventMetadata;



}