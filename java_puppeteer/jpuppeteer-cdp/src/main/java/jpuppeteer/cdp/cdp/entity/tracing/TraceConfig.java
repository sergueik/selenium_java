package jpuppeteer.cdp.cdp.entity.tracing;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TraceConfig {

    /**
    * Controls how the trace buffer stores data.
    */
    private String recordMode;

    /**
    * Turns on JavaScript stack sampling.
    */
    private Boolean enableSampling;

    /**
    * Turns on system tracing.
    */
    private Boolean enableSystrace;

    /**
    * Turns on argument filter.
    */
    private Boolean enableArgumentFilter;

    /**
    * Included category filters.
    */
    private java.util.List<String> includedCategories;

    /**
    * Excluded category filters.
    */
    private java.util.List<String> excludedCategories;

    /**
    * Configuration to synthesize the delays in tracing.
    */
    private java.util.List<String> syntheticDelays;

    /**
    * Configuration for memory dump triggers. Used only when "memory-infra" category is enabled.
    */
    private java.util.Map<String, Object> memoryDumpConfig;



}