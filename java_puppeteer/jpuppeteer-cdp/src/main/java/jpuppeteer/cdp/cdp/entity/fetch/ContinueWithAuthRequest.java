package jpuppeteer.cdp.cdp.entity.fetch;

/**
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContinueWithAuthRequest {

    /**
    * An id the client received in authRequired event.
    */
    private String requestId;

    /**
    * Response to  with an authChallenge.
    */
    private jpuppeteer.cdp.cdp.entity.fetch.AuthChallengeResponse authChallengeResponse;



}