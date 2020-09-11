package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetRequestPostDataResponse {

    /**
    * Request body string, omitting files from multipart requests
    */
    private String postData;



}