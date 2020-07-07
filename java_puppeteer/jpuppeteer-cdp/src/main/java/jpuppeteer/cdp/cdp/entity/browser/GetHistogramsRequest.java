package jpuppeteer.cdp.cdp.entity.browser;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetHistogramsRequest {

    /**
    * Requested substring in name. Only histograms which have query as a substring in their name are extracted. An empty or absent query returns all histograms.
    */
    private String query;

    /**
    * If true, retrieve delta since last call.
    */
    private Boolean delta;



}