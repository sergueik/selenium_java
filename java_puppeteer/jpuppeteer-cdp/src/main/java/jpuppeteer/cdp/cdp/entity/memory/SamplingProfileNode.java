package jpuppeteer.cdp.cdp.entity.memory;

/**
* Heap profile sample.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SamplingProfileNode {

    /**
    * Size of the sampled allocation.
    */
    private Double size;

    /**
    * Total bytes attributed to this sample.
    */
    private Double total;

    /**
    * Execution stack at the point of allocation.
    */
    private java.util.List<String> stack;



}