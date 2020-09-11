package jpuppeteer.cdp.cdp.entity.memory;

/**
* Array of heap profile samples.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SamplingProfile {

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.memory.SamplingProfileNode> samples;

    /**
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.memory.Module> modules;



}