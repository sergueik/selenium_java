package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* Sampling Heap Profile node. Holds callsite information, allocation statistics and child nodes.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SamplingHeapProfileNode {

    /**
    * Function location.
    */
    private jpuppeteer.cdp.cdp.entity.runtime.CallFrame callFrame;

    /**
    * Allocations size in bytes for the node excluding children.
    */
    private Double selfSize;

    /**
    * Node id. Ids are unique across all profiles collected between startSampling and stopSampling.
    */
    private Integer id;

    /**
    * Child nodes.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.heapprofiler.SamplingHeapProfileNode> children;



}