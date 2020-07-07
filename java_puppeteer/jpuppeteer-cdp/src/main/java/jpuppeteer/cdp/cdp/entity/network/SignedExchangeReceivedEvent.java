package jpuppeteer.cdp.cdp.entity.network;

/**
* Fired when a signed exchange was received over the network
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SignedExchangeReceivedEvent {

    /**
    * Request identifier.
    */
    private String requestId;

    /**
    * Information about the signed exchange response.
    */
    private jpuppeteer.cdp.cdp.entity.network.SignedExchangeInfo info;



}