package jpuppeteer.cdp.cdp.entity.webauthn;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class VirtualAuthenticatorOptions {

    /**
    */
    private String protocol;

    /**
    */
    private String transport;

    /**
    * Defaults to false.
    */
    private Boolean hasResidentKey;

    /**
    * Defaults to false.
    */
    private Boolean hasUserVerification;

    /**
    * If set to true, tests of user presence will succeed immediately. Otherwise, they will not be resolved. Defaults to true.
    */
    private Boolean automaticPresenceSimulation;

    /**
    * Sets whether User Verification succeeds or fails for an authenticator. Defaults to false.
    */
    private Boolean isUserVerified;



}