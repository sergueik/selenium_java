package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StopSamplingResponse {

    /**
    * Recorded sampling heap profile.
    */
    private jpuppeteer.cdp.cdp.entity.heapprofiler.SamplingHeapProfile profile;



}