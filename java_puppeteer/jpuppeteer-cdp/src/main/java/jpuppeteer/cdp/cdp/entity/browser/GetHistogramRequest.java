package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHistogramRequest {

    /**
    * Requested histogram name.
    */
    private String name;

    /**
    * If true, retrieve delta since last call.
    */
    private Boolean delta;



}