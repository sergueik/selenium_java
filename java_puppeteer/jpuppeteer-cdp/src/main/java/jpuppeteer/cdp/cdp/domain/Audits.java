package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Audits {

    private jpuppeteer.cdp.CDPSession session;

    public Audits(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Returns the response body and size if it were re-encoded with the specified settings. Only applies to images.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseResponse getEncodedResponse(jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseRequest request, int timeout) throws Exception {
        return session.send("Audits.getEncodedResponse", request, jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseResponse> asyncGetEncodedResponse(jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Audits.getEncodedResponse", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseResponse>(future, jpuppeteer.cdp.cdp.entity.audits.GetEncodedResponseResponse.class);
    }
}