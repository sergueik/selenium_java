package jpuppeteer.cdp.cdp.entity.heapprofiler;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetSamplingProfileResponse {

    /**
    * Return the sampling profile being collected.
    */
    private jpuppeteer.cdp.cdp.entity.heapprofiler.SamplingHeapProfile profile;



}