package jpuppeteer.cdp.cdp.entity.network;

/**
* Authorization challenge for HTTP status code 401 or 407.
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AuthChallenge {

    /**
    * Source of the authentication challenge.
    */
    private String source;

    /**
    * Origin of the challenger.
    */
    private String origin;

    /**
    * The authentication scheme used, such as basic or digest
    */
    private String scheme;

    /**
    * The realm of the challenge. May be empty.
    */
    private String realm;



}