package jpuppeteer.cdp.cdp.entity.page;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResourceContentResponse {

    /**
    * Resource content.
    */
    private String content;

    /**
    * True, if content was served as base64.
    */
    private Boolean base64Encoded;



}