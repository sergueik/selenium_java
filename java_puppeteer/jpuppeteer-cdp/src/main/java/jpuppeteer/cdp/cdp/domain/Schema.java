package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Schema {

    private jpuppeteer.cdp.CDPSession session;

    public Schema(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Returns supported domains.
    */
    public jpuppeteer.cdp.cdp.entity.schema.GetDomainsResponse getDomains(int timeout) throws Exception {
        return session.send("Schema.getDomains", null, jpuppeteer.cdp.cdp.entity.schema.GetDomainsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.schema.GetDomainsResponse> asyncGetDomains() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Schema.getDomains");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.schema.GetDomainsResponse>(future, jpuppeteer.cdp.cdp.entity.schema.GetDomainsResponse.class);
    }
}