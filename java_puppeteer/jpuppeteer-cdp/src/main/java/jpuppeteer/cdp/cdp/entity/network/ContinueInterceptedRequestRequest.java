package jpuppeteer.cdp.cdp.entity.network;

/**
*/
@lombok.Setter
@lombok.Getter
@lombok.ToString
public class ContinueInterceptedRequestRequest {

    /**
    */
    private String interceptionId;

    /**
    * If set this causes the request to fail with the given reason. Passing `Aborted` for requests marked with `isNavigationRequest` also cancels the navigation. Must not be set in response to an authChallenge.
    */
    private String errorReason;

    /**
    * If set the requests completes using with the provided base64 encoded raw response, including HTTP status line and headers etc... Must not be set in response to an authChallenge.
    */
    private String rawResponse;

    /**
    * If set the request url will be modified in a way that's not observable by page. Must not be set in response to an authChallenge.
    */
    private String url;

    /**
    * If set this allows the request method to be overridden. Must not be set in response to an authChallenge.
    */
    private String method;

    /**
    * If set this allows postData to be set. Must not be set in response to an authChallenge.
    */
    private String postData;

    /**
    * If set this allows the request headers to be changed. Must not be set in response to an authChallenge.
    */
    private java.util.Map<String, Object> headers;

    /**
    * Response to a requestIntercepted with an authChallenge. Must not be set otherwise.
    */
    private jpuppeteer.cdp.cdp.entity.network.AuthChallengeResponse authChallengeResponse;



}