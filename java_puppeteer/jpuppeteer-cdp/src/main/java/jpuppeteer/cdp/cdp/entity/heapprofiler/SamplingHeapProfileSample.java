package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* A single sample from a sampling profile.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SamplingHeapProfileSample {

    /**
    * Allocation size in bytes attributed to the sample.
    */
    private Double size;

    /**
    * Id of the corresponding profile tree node.
    */
    private Integer nodeId;

    /**
    * Time-ordered sample ordinal number. It is unique across all profiles retrieved between startSampling and stopSampling.
    */
    private Double ordinal;



}