package jpuppeteer.cdp.cdp.entity.webauthn;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class SetUserVerifiedRequest {

    /**
    */
    private String authenticatorId;

    /**
    */
    private Boolean isUserVerified;



}