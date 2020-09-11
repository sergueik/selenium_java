package jpuppeteer.cdp.cdp.entity.tracing;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StartRequest {

    /**
    * Category/tag filter
    */
    private String categories;

    /**
    * Tracing options
    */
    private String options;

    /**
    * If set, the agent will issue bufferUsage events at this interval, specified in milliseconds
    */
    private Double bufferUsageReportingInterval;

    /**
    * Whether to report trace events as series of dataCollected events or to save trace to a stream (defaults to `ReportEvents`).
    */
    private String transferMode;

    /**
    * Trace data format to use. This only applies when using `ReturnAsStream` transfer mode (defaults to `json`).
    */
    private String streamFormat;

    /**
    * Compression format to use. This only applies when using `ReturnAsStream` transfer mode (defaults to `none`)
    */
    private String streamCompression;

    /**
    */
    private jpuppeteer.cdp.cdp.entity.tracing.TraceConfig traceConfig;



}