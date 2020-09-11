package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class NavigateResponse {

    /**
    * Frame id that has navigated (or failed to navigate)
    */
    private String frameId;

    /**
    * Loader identifier.
    */
    private String loaderId;

    /**
    * User friendly error message, present if and only if navigation has failed.
    */
    private String errorText;



}