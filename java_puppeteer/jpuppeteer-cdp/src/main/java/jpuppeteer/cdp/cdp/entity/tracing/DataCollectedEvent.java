package jpuppeteer.cdp.cdp.entity.tracing;

/**
* Contains an bucket of collected trace events. When tracing is stopped collected events will be send as a sequence of dataCollected events followed by tracingComplete event.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class DataCollectedEvent {

    /**
    */
    private java.util.List<java.util.Map<String, Object>> value;



}