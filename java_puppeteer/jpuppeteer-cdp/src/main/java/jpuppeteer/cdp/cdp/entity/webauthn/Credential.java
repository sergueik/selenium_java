package jpuppeteer.cdp.cdp.entity.webauthn;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class Credential {

    /**
    */
    private String credentialId;

    /**
    */
    private Boolean isResidentCredential;

    /**
    * Relying Party ID the credential is scoped to. Must be set when adding a credential.
    */
    private String rpId;

    /**
    * The ECDSA P-256 private key in PKCS#8 format.
    */
    private String privateKey;

    /**
    * An opaque byte sequence with a maximum size of 64 bytes mapping the credential to a specific user.
    */
    private String userHandle;

    /**
    * Signature counter. This is incremented by one for each successful assertion. See https://w3c.github.io/webauthn/#signature-counter
    */
    private Integer signCount;



}