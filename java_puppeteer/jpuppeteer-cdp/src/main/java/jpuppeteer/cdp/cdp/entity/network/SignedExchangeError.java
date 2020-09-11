package jpuppeteer.cdp.cdp.entity.network;

/**
* Information about a signed exchange response.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SignedExchangeError {

    /**
    * Error message.
    */
    private String message;

    /**
    * The index of the signature which caused the error.
    */
    private Integer signatureIndex;

    /**
    * The field which caused the error.
    */
    private String errorField;



}