package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResourceContentRequest {

    /**
    * Frame id to get resource for.
    */
    private String frameId;

    /**
    * URL of the resource to get content for.
    */
    private String url;



}