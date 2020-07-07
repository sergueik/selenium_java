package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Security {

    private jpuppeteer.cdp.CDPSession session;

    public Security(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables tracking security state changes.
    */
    public void disable(int timeout) throws Exception {
        session.send("Security.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Security.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables tracking security state changes.
    */
    public void enable(int timeout) throws Exception {
        session.send("Security.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Security.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable/disable whether all certificate errors should be ignored.
    */
    public void setIgnoreCertificateErrors(jpuppeteer.cdp.cdp.entity.security.SetIgnoreCertificateErrorsRequest request, int timeout) throws Exception {
        session.send("Security.setIgnoreCertificateErrors", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetIgnoreCertificateErrors(jpuppeteer.cdp.cdp.entity.security.SetIgnoreCertificateErrorsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Security.setIgnoreCertificateErrors", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Handles a certificate error that fired a certificateError event.
    */
    public void handleCertificateError(jpuppeteer.cdp.cdp.entity.security.HandleCertificateErrorRequest request, int timeout) throws Exception {
        session.send("Security.handleCertificateError", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHandleCertificateError(jpuppeteer.cdp.cdp.entity.security.HandleCertificateErrorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Security.handleCertificateError", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable/disable overriding certificate errors. If enabled, all certificate error events need to be handled by the DevTools client and should be answered with `handleCertificateError` commands.
    */
    public void setOverrideCertificateErrors(jpuppeteer.cdp.cdp.entity.security.SetOverrideCertificateErrorsRequest request, int timeout) throws Exception {
        session.send("Security.setOverrideCertificateErrors", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetOverrideCertificateErrors(jpuppeteer.cdp.cdp.entity.security.SetOverrideCertificateErrorsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Security.setOverrideCertificateErrors", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}