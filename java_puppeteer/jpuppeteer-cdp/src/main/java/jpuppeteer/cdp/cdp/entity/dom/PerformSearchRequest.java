package jpuppeteer.cdp.cdp.entity.dom;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class PerformSearchRequest {

    /**
    * Plain text or query selector or XPath search query.
    */
    private String query;

    /**
    * True to search in user agent shadow DOM.
    */
    private Boolean includeUserAgentShadowDOM;



}