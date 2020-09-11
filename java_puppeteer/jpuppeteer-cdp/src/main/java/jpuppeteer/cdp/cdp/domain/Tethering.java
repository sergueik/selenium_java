package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Tethering {

    private jpuppeteer.cdp.CDPSession session;

    public Tethering(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Request browser port binding.
    * experimental
    */
    public void bind(jpuppeteer.cdp.cdp.entity.tethering.BindRequest request, int timeout) throws Exception {
        session.send("Tethering.bind", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncBind(jpuppeteer.cdp.cdp.entity.tethering.BindRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tethering.bind", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Request browser port unbinding.
    * experimental
    */
    public void unbind(jpuppeteer.cdp.cdp.entity.tethering.UnbindRequest request, int timeout) throws Exception {
        session.send("Tethering.unbind", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncUnbind(jpuppeteer.cdp.cdp.entity.tethering.UnbindRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Tethering.unbind", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}