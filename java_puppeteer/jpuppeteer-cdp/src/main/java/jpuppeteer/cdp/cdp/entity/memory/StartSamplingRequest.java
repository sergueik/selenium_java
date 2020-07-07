package jpuppeteer.cdp.cdp.entity.memory;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class StartSamplingRequest {

    /**
    * Average number of bytes between samples.
    */
    private Integer samplingInterval;

    /**
    * Do not randomize intervals between samples.
    */
    private Boolean suppressRandomness;



}