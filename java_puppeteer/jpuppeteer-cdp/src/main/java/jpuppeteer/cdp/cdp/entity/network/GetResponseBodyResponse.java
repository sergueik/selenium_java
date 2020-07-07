package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResponseBodyResponse {

    /**
    * Response body.
    */
    private String body;

    /**
    * True, if content was sent as base64.
    */
    private Boolean base64Encoded;



}