package jpuppeteer.cdp.cdp.entity.webauthn;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class RemoveCredentialRequest {

    /**
    */
    private String authenticatorId;

    /**
    */
    private String credentialId;



}