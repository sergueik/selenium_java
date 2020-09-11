package jpuppeteer.cdp.cdp.entity.media;

/**
* Send events as a list, allowing them to be batched on the browser for less congestion. If batched, events must ALWAYS be in chronological order.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PlayerEventsAddedEvent {

    /**
    */
    private String playerId;

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.media.PlayerEvent> events;



}