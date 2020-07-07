package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class WebAuthn {

    private jpuppeteer.cdp.CDPSession session;

    public WebAuthn(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Enable the WebAuthn domain and start intercepting credential storage and retrieval with a virtual authenticator.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("WebAuthn.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disable the WebAuthn domain.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("WebAuthn.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Creates and adds a virtual authenticator.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorResponse addVirtualAuthenticator(jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorRequest request, int timeout) throws Exception {
        return session.send("WebAuthn.addVirtualAuthenticator", request, jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorResponse> asyncAddVirtualAuthenticator(jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.addVirtualAuthenticator", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorResponse>(future, jpuppeteer.cdp.cdp.entity.webauthn.AddVirtualAuthenticatorResponse.class);
    }

    /**
    * Removes the given authenticator.
    * experimental
    */
    public void removeVirtualAuthenticator(jpuppeteer.cdp.cdp.entity.webauthn.RemoveVirtualAuthenticatorRequest request, int timeout) throws Exception {
        session.send("WebAuthn.removeVirtualAuthenticator", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveVirtualAuthenticator(jpuppeteer.cdp.cdp.entity.webauthn.RemoveVirtualAuthenticatorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.removeVirtualAuthenticator", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Adds the credential to the specified authenticator.
    * experimental
    */
    public void addCredential(jpuppeteer.cdp.cdp.entity.webauthn.AddCredentialRequest request, int timeout) throws Exception {
        session.send("WebAuthn.addCredential", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncAddCredential(jpuppeteer.cdp.cdp.entity.webauthn.AddCredentialRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.addCredential", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns a single credential stored in the given virtual authenticator that matches the credential ID.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialResponse getCredential(jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialRequest request, int timeout) throws Exception {
        return session.send("WebAuthn.getCredential", request, jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialResponse> asyncGetCredential(jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.getCredential", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialResponse>(future, jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialResponse.class);
    }

    /**
    * Returns all the credentials stored in the given virtual authenticator.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsResponse getCredentials(jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsRequest request, int timeout) throws Exception {
        return session.send("WebAuthn.getCredentials", request, jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsResponse> asyncGetCredentials(jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.getCredentials", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsResponse>(future, jpuppeteer.cdp.cdp.entity.webauthn.GetCredentialsResponse.class);
    }

    /**
    * Removes a credential from the authenticator.
    * experimental
    */
    public void removeCredential(jpuppeteer.cdp.cdp.entity.webauthn.RemoveCredentialRequest request, int timeout) throws Exception {
        session.send("WebAuthn.removeCredential", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveCredential(jpuppeteer.cdp.cdp.entity.webauthn.RemoveCredentialRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.removeCredential", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears all the credentials from the specified device.
    * experimental
    */
    public void clearCredentials(jpuppeteer.cdp.cdp.entity.webauthn.ClearCredentialsRequest request, int timeout) throws Exception {
        session.send("WebAuthn.clearCredentials", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearCredentials(jpuppeteer.cdp.cdp.entity.webauthn.ClearCredentialsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.clearCredentials", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets whether User Verification succeeds or fails for an authenticator. The default is true.
    * experimental
    */
    public void setUserVerified(jpuppeteer.cdp.cdp.entity.webauthn.SetUserVerifiedRequest request, int timeout) throws Exception {
        session.send("WebAuthn.setUserVerified", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetUserVerified(jpuppeteer.cdp.cdp.entity.webauthn.SetUserVerifiedRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("WebAuthn.setUserVerified", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}