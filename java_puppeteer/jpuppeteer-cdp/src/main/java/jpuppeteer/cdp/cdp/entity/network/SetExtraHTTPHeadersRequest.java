package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetExtraHTTPHeadersRequest {

    /**
    * Map with extra HTTP headers.
    */
    private java.util.Map<String, Object> headers;



}