package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetDocumentContentRequest {

    /**
    * Frame id to set HTML for.
    */
    private String frameId;

    /**
    * HTML content to set.
    */
    private String html;



}