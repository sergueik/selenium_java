package jpuppeteer.cdp.cdp.entity.network;

/**
* Information about a signed exchange response.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SignedExchangeInfo {

    /**
    * The outer response of signed HTTP exchange which was received from network.
    */
    private jpuppeteer.cdp.cdp.entity.network.Response outerResponse;

    /**
    * Information about the signed exchange header.
    */
    private jpuppeteer.cdp.cdp.entity.network.SignedExchangeHeader header;

    /**
    * Security details for the signed exchange header.
    */
    private jpuppeteer.cdp.cdp.entity.network.SecurityDetails securityDetails;

    /**
    * Errors occurred while handling the signed exchagne.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.SignedExchangeError> errors;



}