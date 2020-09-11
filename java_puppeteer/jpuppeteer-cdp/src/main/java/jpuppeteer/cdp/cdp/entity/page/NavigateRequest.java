package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NavigateRequest {

    /**
    * URL to navigate the page to.
    */
    private String url;

    /**
    * Referrer URL.
    */
    private String referrer;

    /**
    * Intended transition type.
    */
    private String transitionType;

    /**
    * Frame id to navigate, if not specified navigates the top frame.
    */
    private String frameId;



}