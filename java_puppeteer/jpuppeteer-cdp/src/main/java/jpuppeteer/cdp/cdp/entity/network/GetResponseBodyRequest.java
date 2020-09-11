package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class GetResponseBodyRequest {

    /**
    * Identifier of the network request to get content for.
    */
    private String requestId;



}