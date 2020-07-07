package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Runtime {

    private jpuppeteer.cdp.CDPSession session;

    public Runtime(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Add handler to promise with given promise object id.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseResponse awaitPromise(jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseRequest request, int timeout) throws Exception {
        return session.send("Runtime.awaitPromise", request, jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseResponse> asyncAwaitPromise(jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.awaitPromise", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.AwaitPromiseResponse.class);
    }

    /**
    * Calls function with given declaration on the given object. Object group of the result is inherited from the target object.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnResponse callFunctionOn(jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnRequest request, int timeout) throws Exception {
        return session.send("Runtime.callFunctionOn", request, jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnResponse> asyncCallFunctionOn(jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.callFunctionOn", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.CallFunctionOnResponse.class);
    }

    /**
    * Compiles expression.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.CompileScriptResponse compileScript(jpuppeteer.cdp.cdp.entity.runtime.CompileScriptRequest request, int timeout) throws Exception {
        return session.send("Runtime.compileScript", request, jpuppeteer.cdp.cdp.entity.runtime.CompileScriptResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.CompileScriptResponse> asyncCompileScript(jpuppeteer.cdp.cdp.entity.runtime.CompileScriptRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.compileScript", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.CompileScriptResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.CompileScriptResponse.class);
    }

    /**
    * Disables reporting of execution contexts creation.
    */
    public void disable(int timeout) throws Exception {
        session.send("Runtime.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Discards collected exceptions and console API calls.
    */
    public void discardConsoleEntries(int timeout) throws Exception {
        session.send("Runtime.discardConsoleEntries", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDiscardConsoleEntries() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.discardConsoleEntries");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables reporting of execution contexts creation by means of `executionContextCreated` event. When the reporting gets enabled the event will be sent immediately for each existing execution context.
    */
    public void enable(int timeout) throws Exception {
        session.send("Runtime.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Evaluates expression on global object.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.EvaluateResponse evaluate(jpuppeteer.cdp.cdp.entity.runtime.EvaluateRequest request, int timeout) throws Exception {
        return session.send("Runtime.evaluate", request, jpuppeteer.cdp.cdp.entity.runtime.EvaluateResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.EvaluateResponse> asyncEvaluate(jpuppeteer.cdp.cdp.entity.runtime.EvaluateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.evaluate", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.EvaluateResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.EvaluateResponse.class);
    }

    /**
    * Returns the isolate id.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.GetIsolateIdResponse getIsolateId(int timeout) throws Exception {
        return session.send("Runtime.getIsolateId", null, jpuppeteer.cdp.cdp.entity.runtime.GetIsolateIdResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.GetIsolateIdResponse> asyncGetIsolateId() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.getIsolateId");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.GetIsolateIdResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.GetIsolateIdResponse.class);
    }

    /**
    * Returns the JavaScript heap usage. It is the total usage of the corresponding isolate not scoped to a particular Runtime.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.GetHeapUsageResponse getHeapUsage(int timeout) throws Exception {
        return session.send("Runtime.getHeapUsage", null, jpuppeteer.cdp.cdp.entity.runtime.GetHeapUsageResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.GetHeapUsageResponse> asyncGetHeapUsage() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.getHeapUsage");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.GetHeapUsageResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.GetHeapUsageResponse.class);
    }

    /**
    * Returns properties of a given object. Object group of the result is inherited from the target object.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesResponse getProperties(jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesRequest request, int timeout) throws Exception {
        return session.send("Runtime.getProperties", request, jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesResponse> asyncGetProperties(jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.getProperties", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.GetPropertiesResponse.class);
    }

    /**
    * Returns all let, const and class variables from global scope.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesResponse globalLexicalScopeNames(jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesRequest request, int timeout) throws Exception {
        return session.send("Runtime.globalLexicalScopeNames", request, jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesResponse> asyncGlobalLexicalScopeNames(jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.globalLexicalScopeNames", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.GlobalLexicalScopeNamesResponse.class);
    }

    /**
    */
    public jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsResponse queryObjects(jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsRequest request, int timeout) throws Exception {
        return session.send("Runtime.queryObjects", request, jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsResponse> asyncQueryObjects(jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.queryObjects", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.QueryObjectsResponse.class);
    }

    /**
    * Releases remote object with given id.
    */
    public void releaseObject(jpuppeteer.cdp.cdp.entity.runtime.ReleaseObjectRequest request, int timeout) throws Exception {
        session.send("Runtime.releaseObject", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncReleaseObject(jpuppeteer.cdp.cdp.entity.runtime.ReleaseObjectRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.releaseObject", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Releases all remote objects that belong to a given group.
    */
    public void releaseObjectGroup(jpuppeteer.cdp.cdp.entity.runtime.ReleaseObjectGroupRequest request, int timeout) throws Exception {
        session.send("Runtime.releaseObjectGroup", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncReleaseObjectGroup(jpuppeteer.cdp.cdp.entity.runtime.ReleaseObjectGroupRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.releaseObjectGroup", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Tells inspected instance to run if it was waiting for debugger to attach.
    */
    public void runIfWaitingForDebugger(int timeout) throws Exception {
        session.send("Runtime.runIfWaitingForDebugger", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRunIfWaitingForDebugger() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.runIfWaitingForDebugger");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Runs script with given id in a given context.
    */
    public jpuppeteer.cdp.cdp.entity.runtime.RunScriptResponse runScript(jpuppeteer.cdp.cdp.entity.runtime.RunScriptRequest request, int timeout) throws Exception {
        return session.send("Runtime.runScript", request, jpuppeteer.cdp.cdp.entity.runtime.RunScriptResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.runtime.RunScriptResponse> asyncRunScript(jpuppeteer.cdp.cdp.entity.runtime.RunScriptRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.runScript", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.runtime.RunScriptResponse>(future, jpuppeteer.cdp.cdp.entity.runtime.RunScriptResponse.class);
    }

    /**
    * Enables or disables async call stacks tracking.
    */
    public void setAsyncCallStackDepth(jpuppeteer.cdp.cdp.entity.runtime.SetAsyncCallStackDepthRequest request, int timeout) throws Exception {
        session.send("Runtime.setAsyncCallStackDepth", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetAsyncCallStackDepth(jpuppeteer.cdp.cdp.entity.runtime.SetAsyncCallStackDepthRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.setAsyncCallStackDepth", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void setCustomObjectFormatterEnabled(jpuppeteer.cdp.cdp.entity.runtime.SetCustomObjectFormatterEnabledRequest request, int timeout) throws Exception {
        session.send("Runtime.setCustomObjectFormatterEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetCustomObjectFormatterEnabled(jpuppeteer.cdp.cdp.entity.runtime.SetCustomObjectFormatterEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.setCustomObjectFormatterEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void setMaxCallStackSizeToCapture(jpuppeteer.cdp.cdp.entity.runtime.SetMaxCallStackSizeToCaptureRequest request, int timeout) throws Exception {
        session.send("Runtime.setMaxCallStackSizeToCapture", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetMaxCallStackSizeToCapture(jpuppeteer.cdp.cdp.entity.runtime.SetMaxCallStackSizeToCaptureRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.setMaxCallStackSizeToCapture", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Terminate current or next JavaScript execution. Will cancel the termination when the outer-most script execution ends.
    */
    public void terminateExecution(int timeout) throws Exception {
        session.send("Runtime.terminateExecution", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncTerminateExecution() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.terminateExecution");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * If executionContextId is empty, adds binding with the given name on the global objects of all inspected contexts, including those created later, bindings survive reloads. If executionContextId is specified, adds binding only on global object of given execution context. Binding function takes exactly one argument, this argument should be string, in case of any other input, function throws an exception. Each binding function call produces Runtime.bindingCalled notification.
    */
    public void addBinding(jpuppeteer.cdp.cdp.entity.runtime.AddBindingRequest request, int timeout) throws Exception {
        session.send("Runtime.addBinding", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncAddBinding(jpuppeteer.cdp.cdp.entity.runtime.AddBindingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.addBinding", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * This method does not remove binding function from global object but unsubscribes current runtime agent from Runtime.bindingCalled notifications.
    */
    public void removeBinding(jpuppeteer.cdp.cdp.entity.runtime.RemoveBindingRequest request, int timeout) throws Exception {
        session.send("Runtime.removeBinding", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveBinding(jpuppeteer.cdp.cdp.entity.runtime.RemoveBindingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Runtime.removeBinding", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}