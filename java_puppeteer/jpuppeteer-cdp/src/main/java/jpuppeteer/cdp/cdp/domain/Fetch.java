package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Fetch {

    private jpuppeteer.cdp.CDPSession session;

    public Fetch(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables the fetch domain.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Fetch.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables issuing of requestPaused events. A request will be paused until client calls one of failRequest, fulfillRequest or continueRequest/continueWithAuth.
    * experimental
    */
    public void enable(jpuppeteer.cdp.cdp.entity.fetch.EnableRequest request, int timeout) throws Exception {
        session.send("Fetch.enable", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable(jpuppeteer.cdp.cdp.entity.fetch.EnableRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.enable", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Causes the request to fail with specified reason.
    * experimental
    */
    public void failRequest(jpuppeteer.cdp.cdp.entity.fetch.FailRequestRequest request, int timeout) throws Exception {
        session.send("Fetch.failRequest", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncFailRequest(jpuppeteer.cdp.cdp.entity.fetch.FailRequestRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.failRequest", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Provides response to the request.
    * experimental
    */
    public void fulfillRequest(jpuppeteer.cdp.cdp.entity.fetch.FulfillRequestRequest request, int timeout) throws Exception {
        session.send("Fetch.fulfillRequest", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncFulfillRequest(jpuppeteer.cdp.cdp.entity.fetch.FulfillRequestRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.fulfillRequest", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Continues the request, optionally modifying some of its parameters.
    * experimental
    */
    public void continueRequest(jpuppeteer.cdp.cdp.entity.fetch.ContinueRequestRequest request, int timeout) throws Exception {
        session.send("Fetch.continueRequest", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncContinueRequest(jpuppeteer.cdp.cdp.entity.fetch.ContinueRequestRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.continueRequest", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Continues a request supplying authChallengeResponse following authRequired event.
    * experimental
    */
    public void continueWithAuth(jpuppeteer.cdp.cdp.entity.fetch.ContinueWithAuthRequest request, int timeout) throws Exception {
        session.send("Fetch.continueWithAuth", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncContinueWithAuth(jpuppeteer.cdp.cdp.entity.fetch.ContinueWithAuthRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.continueWithAuth", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Causes the body of the response to be received from the server and returned as a single string. May only be issued for a request that is paused in the Response stage and is mutually exclusive with takeResponseBodyForInterceptionAsStream. Calling other methods that affect the request or disabling fetch domain before body is received results in an undefined behavior.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyResponse getResponseBody(jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyRequest request, int timeout) throws Exception {
        return session.send("Fetch.getResponseBody", request, jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyResponse> asyncGetResponseBody(jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.getResponseBody", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyResponse>(future, jpuppeteer.cdp.cdp.entity.fetch.GetResponseBodyResponse.class);
    }

    /**
    * Returns a handle to the stream representing the response body. The request must be paused in the HeadersReceived stage. Note that after this command the request can't be continued as is -- client either needs to cancel it or to provide the response body. The stream only supports sequential read, IO.read will fail if the position is specified. This method is mutually exclusive with getResponseBody. Calling other methods that affect the request or disabling fetch domain before body is received results in an undefined behavior.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamResponse takeResponseBodyAsStream(jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamRequest request, int timeout) throws Exception {
        return session.send("Fetch.takeResponseBodyAsStream", request, jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamResponse> asyncTakeResponseBodyAsStream(jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Fetch.takeResponseBodyAsStream", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamResponse>(future, jpuppeteer.cdp.cdp.entity.fetch.TakeResponseBodyAsStreamResponse.class);
    }
}