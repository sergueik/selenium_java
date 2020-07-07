package jpuppeteer.cdp.cdp.entity.tracing;

/**
* Signals that tracing is stopped and there is no trace buffers pending flush, all data were delivered via dataCollected events.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TracingCompleteEvent {

    /**
    * Indicates whether some trace data is known to have been lost, e.g. because the trace ring buffer wrapped around.
    */
    private Boolean dataLossOccurred;

    /**
    * A handle of the stream that holds resulting trace data.
    */
    private String stream;

    /**
    * Trace data format of returned stream.
    */
    private String traceFormat;

    /**
    * Compression format of returned stream.
    */
    private String streamCompression;



}