package jpuppeteer.cdp.cdp.entity.profiler;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class TakeTypeProfileResponse {

    /**
    * Type profile for all scripts since startTypeProfile() was turned on.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.profiler.ScriptTypeProfile> result;



}