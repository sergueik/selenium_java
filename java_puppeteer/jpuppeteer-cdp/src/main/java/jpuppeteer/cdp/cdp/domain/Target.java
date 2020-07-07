package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Target {

    private jpuppeteer.cdp.CDPConnection connection;

    public Target(jpuppeteer.cdp.CDPConnection connection) {
        this.connection = connection;
    }

    /**
    * Activates (focuses) the target.
    */
    public void activateTarget(jpuppeteer.cdp.cdp.entity.target.ActivateTargetRequest request, int timeout) throws Exception {
        connection.send("Target.activateTarget", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncActivateTarget(jpuppeteer.cdp.cdp.entity.target.ActivateTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.activateTarget", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Attaches to the target with given id.
    */
    public jpuppeteer.cdp.cdp.entity.target.AttachToTargetResponse attachToTarget(jpuppeteer.cdp.cdp.entity.target.AttachToTargetRequest request, int timeout) throws Exception {
        return connection.send("Target.attachToTarget", request, jpuppeteer.cdp.cdp.entity.target.AttachToTargetResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.AttachToTargetResponse> asyncAttachToTarget(jpuppeteer.cdp.cdp.entity.target.AttachToTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.attachToTarget", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.AttachToTargetResponse>(future, jpuppeteer.cdp.cdp.entity.target.AttachToTargetResponse.class);
    }

    /**
    * Attaches to the browser target, only uses flat sessionId mode.
    */
    public jpuppeteer.cdp.cdp.entity.target.AttachToBrowserTargetResponse attachToBrowserTarget(int timeout) throws Exception {
        return connection.send("Target.attachToBrowserTarget", null, jpuppeteer.cdp.cdp.entity.target.AttachToBrowserTargetResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.AttachToBrowserTargetResponse> asyncAttachToBrowserTarget() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.attachToBrowserTarget");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.AttachToBrowserTargetResponse>(future, jpuppeteer.cdp.cdp.entity.target.AttachToBrowserTargetResponse.class);
    }

    /**
    * Closes the target. If the target is a page that gets closed too.
    */
    public jpuppeteer.cdp.cdp.entity.target.CloseTargetResponse closeTarget(jpuppeteer.cdp.cdp.entity.target.CloseTargetRequest request, int timeout) throws Exception {
        return connection.send("Target.closeTarget", request, jpuppeteer.cdp.cdp.entity.target.CloseTargetResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.CloseTargetResponse> asyncCloseTarget(jpuppeteer.cdp.cdp.entity.target.CloseTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.closeTarget", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.CloseTargetResponse>(future, jpuppeteer.cdp.cdp.entity.target.CloseTargetResponse.class);
    }

    /**
    * Inject object to the target's main frame that provides a communication channel with browser target.  Injected object will be available as `window[bindingName]`.  The object has the follwing API: - `binding.send(json)` - a method to send messages over the remote debugging protocol - `binding.onmessage = json => handleMessage(json)` - a callback that will be called for the protocol notifications and command responses.
    */
    public void exposeDevToolsProtocol(jpuppeteer.cdp.cdp.entity.target.ExposeDevToolsProtocolRequest request, int timeout) throws Exception {
        connection.send("Target.exposeDevToolsProtocol", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncExposeDevToolsProtocol(jpuppeteer.cdp.cdp.entity.target.ExposeDevToolsProtocolRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.exposeDevToolsProtocol", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Creates a new empty BrowserContext. Similar to an incognito profile but you can have more than one.
    */
    public jpuppeteer.cdp.cdp.entity.target.CreateBrowserContextResponse createBrowserContext(int timeout) throws Exception {
        return connection.send("Target.createBrowserContext", null, jpuppeteer.cdp.cdp.entity.target.CreateBrowserContextResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.CreateBrowserContextResponse> asyncCreateBrowserContext() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.createBrowserContext");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.CreateBrowserContextResponse>(future, jpuppeteer.cdp.cdp.entity.target.CreateBrowserContextResponse.class);
    }

    /**
    * Returns all browser contexts created with `Target.createBrowserContext` method.
    */
    public jpuppeteer.cdp.cdp.entity.target.GetBrowserContextsResponse getBrowserContexts(int timeout) throws Exception {
        return connection.send("Target.getBrowserContexts", null, jpuppeteer.cdp.cdp.entity.target.GetBrowserContextsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.GetBrowserContextsResponse> asyncGetBrowserContexts() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.getBrowserContexts");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.GetBrowserContextsResponse>(future, jpuppeteer.cdp.cdp.entity.target.GetBrowserContextsResponse.class);
    }

    /**
    * Creates a new page.
    */
    public jpuppeteer.cdp.cdp.entity.target.CreateTargetResponse createTarget(jpuppeteer.cdp.cdp.entity.target.CreateTargetRequest request, int timeout) throws Exception {
        return connection.send("Target.createTarget", request, jpuppeteer.cdp.cdp.entity.target.CreateTargetResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.CreateTargetResponse> asyncCreateTarget(jpuppeteer.cdp.cdp.entity.target.CreateTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.createTarget", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.CreateTargetResponse>(future, jpuppeteer.cdp.cdp.entity.target.CreateTargetResponse.class);
    }

    /**
    * Detaches session with given id.
    */
    public void detachFromTarget(jpuppeteer.cdp.cdp.entity.target.DetachFromTargetRequest request, int timeout) throws Exception {
        connection.send("Target.detachFromTarget", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDetachFromTarget(jpuppeteer.cdp.cdp.entity.target.DetachFromTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.detachFromTarget", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Deletes a BrowserContext. All the belonging pages will be closed without calling their beforeunload hooks.
    */
    public void disposeBrowserContext(jpuppeteer.cdp.cdp.entity.target.DisposeBrowserContextRequest request, int timeout) throws Exception {
        connection.send("Target.disposeBrowserContext", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisposeBrowserContext(jpuppeteer.cdp.cdp.entity.target.DisposeBrowserContextRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.disposeBrowserContext", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns information about a target.
    */
    public jpuppeteer.cdp.cdp.entity.target.GetTargetInfoResponse getTargetInfo(jpuppeteer.cdp.cdp.entity.target.GetTargetInfoRequest request, int timeout) throws Exception {
        return connection.send("Target.getTargetInfo", request, jpuppeteer.cdp.cdp.entity.target.GetTargetInfoResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.GetTargetInfoResponse> asyncGetTargetInfo(jpuppeteer.cdp.cdp.entity.target.GetTargetInfoRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.getTargetInfo", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.GetTargetInfoResponse>(future, jpuppeteer.cdp.cdp.entity.target.GetTargetInfoResponse.class);
    }

    /**
    * Retrieves a list of available targets.
    */
    public jpuppeteer.cdp.cdp.entity.target.GetTargetsResponse getTargets(int timeout) throws Exception {
        return connection.send("Target.getTargets", null, jpuppeteer.cdp.cdp.entity.target.GetTargetsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.target.GetTargetsResponse> asyncGetTargets() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.getTargets");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.target.GetTargetsResponse>(future, jpuppeteer.cdp.cdp.entity.target.GetTargetsResponse.class);
    }

    /**
    * Sends protocol message over session with given id. Consider using flat mode instead; see commands attachToTarget, setAutoAttach, and crbug.com/991325.
    */
    public void sendMessageToTarget(jpuppeteer.cdp.cdp.entity.target.SendMessageToTargetRequest request, int timeout) throws Exception {
        connection.send("Target.sendMessageToTarget", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSendMessageToTarget(jpuppeteer.cdp.cdp.entity.target.SendMessageToTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.sendMessageToTarget", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Controls whether to automatically attach to new targets which are considered to be related to this one. When turned on, attaches to all existing related targets as well. When turned off, automatically detaches from all currently attached targets.
    */
    public void setAutoAttach(jpuppeteer.cdp.cdp.entity.target.SetAutoAttachRequest request, int timeout) throws Exception {
        connection.send("Target.setAutoAttach", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetAutoAttach(jpuppeteer.cdp.cdp.entity.target.SetAutoAttachRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.setAutoAttach", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Controls whether to discover available targets and notify via `targetCreated/targetInfoChanged/targetDestroyed` events.
    */
    public void setDiscoverTargets(jpuppeteer.cdp.cdp.entity.target.SetDiscoverTargetsRequest request, int timeout) throws Exception {
        connection.send("Target.setDiscoverTargets", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDiscoverTargets(jpuppeteer.cdp.cdp.entity.target.SetDiscoverTargetsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.setDiscoverTargets", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables target discovery for the specified locations, when `setDiscoverTargets` was set to `true`.
    */
    public void setRemoteLocations(jpuppeteer.cdp.cdp.entity.target.SetRemoteLocationsRequest request, int timeout) throws Exception {
        connection.send("Target.setRemoteLocations", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetRemoteLocations(jpuppeteer.cdp.cdp.entity.target.SetRemoteLocationsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Target.setRemoteLocations", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}