package jpuppeteer.cdp.cdp.entity.tracing;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class BufferUsageEvent {

    /**
    * A number in range [0..1] that indicates the used size of event buffer as a fraction of its total size.
    */
    private Double percentFull;

    /**
    * An approximate number of events in the trace log.
    */
    private Double eventCount;

    /**
    * A number in range [0..1] that indicates the used size of event buffer as a fraction of its total size.
    */
    private Double value;



}