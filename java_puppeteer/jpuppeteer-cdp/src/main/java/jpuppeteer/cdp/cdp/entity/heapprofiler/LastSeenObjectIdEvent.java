package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* If heap objects tracking has been started then backend regularly sends a current value for last seen object id and corresponding timestamp. If the were changes in the heap since last event then one or more heapStatsUpdate events will be sent before a new lastSeenObjectId event.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class LastSeenObjectIdEvent {

    /**
    */
    private Integer lastSeenObjectId;

    /**
    */
    private Double timestamp;



}