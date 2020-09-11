package jpuppeteer.cdp.cdp.entity.fetch;

/**
* Issued when the domain is enabled with handleAuthRequests set to true. The request is paused until client responds with continueWithAuth.
* experimental
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class AuthRequiredEvent {

    /**
    * Each request the page makes will have a unique id.
    */
    private String requestId;

    /**
    * The details of the request.
    */
    private jpuppeteer.cdp.cdp.entity.network.Request request;

    /**
    * The id of the frame that initiated the request.
    */
    private String frameId;

    /**
    * How the requested resource will be used.
    */
    private String resourceType;

    /**
    * Details of the Authorization Challenge encountered. If this is set, client should respond with continueRequest that contains AuthChallengeResponse.
    */
    private jpuppeteer.cdp.cdp.entity.fetch.AuthChallenge authChallenge;



}