package jpuppeteer.cdp.cdp.entity.network;

/**
* Information about a signed exchange header. https://wicg.github.io/webpackage/draft-yasskin-httpbis-origin-signed-exchanges-impl.html#cbor-representation
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SignedExchangeHeader {

    /**
    * Signed exchange request URL.
    */
    private String requestUrl;

    /**
    * Signed exchange response code.
    */
    private Integer responseCode;

    /**
    * Signed exchange response headers.
    */
    private java.util.Map<String, Object> responseHeaders;

    /**
    * Signed exchange response signature.
    */
    private java.util.List<jpuppeteer.cdp.cdp.entity.network.SignedExchangeSignature> signatures;

    /**
    * Signed exchange header integrity hash in the form of "sha256-<base64-hash-value>".
    */
    private String headerIntegrity;



}