package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* Sampling profile.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SamplingHeapProfile {

    /**
    */
    private jpuppeteer.cdp.cdp.entity.heapprofiler.SamplingHeapProfileNode head;

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.heapprofiler.SamplingHeapProfileSample> samples;



}