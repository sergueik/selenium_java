package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Debugger {

    private jpuppeteer.cdp.CDPSession session;

    public Debugger(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Continues execution until specific location is reached.
    */
    public void continueToLocation(jpuppeteer.cdp.cdp.entity.debugger.ContinueToLocationRequest request, int timeout) throws Exception {
        session.send("Debugger.continueToLocation", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncContinueToLocation(jpuppeteer.cdp.cdp.entity.debugger.ContinueToLocationRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.continueToLocation", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables debugger for given page.
    */
    public void disable(int timeout) throws Exception {
        session.send("Debugger.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables debugger for the given page. Clients should not assume that the debugging has been enabled until the result for this command is received.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.EnableResponse enable(jpuppeteer.cdp.cdp.entity.debugger.EnableRequest request, int timeout) throws Exception {
        return session.send("Debugger.enable", request, jpuppeteer.cdp.cdp.entity.debugger.EnableResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.EnableResponse> asyncEnable(jpuppeteer.cdp.cdp.entity.debugger.EnableRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.enable", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.EnableResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.EnableResponse.class);
    }

    /**
    * Evaluates expression on a given call frame.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameResponse evaluateOnCallFrame(jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameRequest request, int timeout) throws Exception {
        return session.send("Debugger.evaluateOnCallFrame", request, jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameResponse> asyncEvaluateOnCallFrame(jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.evaluateOnCallFrame", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.EvaluateOnCallFrameResponse.class);
    }

    /**
    * Returns possible locations for breakpoint. scriptId in start and end range locations should be the same.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsResponse getPossibleBreakpoints(jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsRequest request, int timeout) throws Exception {
        return session.send("Debugger.getPossibleBreakpoints", request, jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsResponse> asyncGetPossibleBreakpoints(jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.getPossibleBreakpoints", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.GetPossibleBreakpointsResponse.class);
    }

    /**
    * Returns source for the script with given id.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceResponse getScriptSource(jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceRequest request, int timeout) throws Exception {
        return session.send("Debugger.getScriptSource", request, jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceResponse> asyncGetScriptSource(jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.getScriptSource", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.GetScriptSourceResponse.class);
    }

    /**
    * This command is deprecated. Use getScriptSource instead.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeResponse getWasmBytecode(jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeRequest request, int timeout) throws Exception {
        return session.send("Debugger.getWasmBytecode", request, jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeResponse> asyncGetWasmBytecode(jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.getWasmBytecode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.GetWasmBytecodeResponse.class);
    }

    /**
    * Returns stack trace with given `stackTraceId`.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceResponse getStackTrace(jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceRequest request, int timeout) throws Exception {
        return session.send("Debugger.getStackTrace", request, jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceResponse> asyncGetStackTrace(jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.getStackTrace", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.GetStackTraceResponse.class);
    }

    /**
    * Stops on the next JavaScript statement.
    */
    public void pause(int timeout) throws Exception {
        session.send("Debugger.pause", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncPause() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.pause");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void pauseOnAsyncCall(jpuppeteer.cdp.cdp.entity.debugger.PauseOnAsyncCallRequest request, int timeout) throws Exception {
        session.send("Debugger.pauseOnAsyncCall", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncPauseOnAsyncCall(jpuppeteer.cdp.cdp.entity.debugger.PauseOnAsyncCallRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.pauseOnAsyncCall", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes JavaScript breakpoint.
    */
    public void removeBreakpoint(jpuppeteer.cdp.cdp.entity.debugger.RemoveBreakpointRequest request, int timeout) throws Exception {
        session.send("Debugger.removeBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveBreakpoint(jpuppeteer.cdp.cdp.entity.debugger.RemoveBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.removeBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Restarts particular call frame from the beginning.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.RestartFrameResponse restartFrame(jpuppeteer.cdp.cdp.entity.debugger.RestartFrameRequest request, int timeout) throws Exception {
        return session.send("Debugger.restartFrame", request, jpuppeteer.cdp.cdp.entity.debugger.RestartFrameResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.RestartFrameResponse> asyncRestartFrame(jpuppeteer.cdp.cdp.entity.debugger.RestartFrameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.restartFrame", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.RestartFrameResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.RestartFrameResponse.class);
    }

    /**
    * Resumes JavaScript execution.
    */
    public void resume(int timeout) throws Exception {
        session.send("Debugger.resume", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncResume() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.resume");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Searches for given string in script content.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.SearchInContentResponse searchInContent(jpuppeteer.cdp.cdp.entity.debugger.SearchInContentRequest request, int timeout) throws Exception {
        return session.send("Debugger.searchInContent", request, jpuppeteer.cdp.cdp.entity.debugger.SearchInContentResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.SearchInContentResponse> asyncSearchInContent(jpuppeteer.cdp.cdp.entity.debugger.SearchInContentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.searchInContent", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.SearchInContentResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.SearchInContentResponse.class);
    }

    /**
    * Enables or disables async call stacks tracking.
    */
    public void setAsyncCallStackDepth(jpuppeteer.cdp.cdp.entity.debugger.SetAsyncCallStackDepthRequest request, int timeout) throws Exception {
        session.send("Debugger.setAsyncCallStackDepth", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetAsyncCallStackDepth(jpuppeteer.cdp.cdp.entity.debugger.SetAsyncCallStackDepthRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setAsyncCallStackDepth", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Replace previous blackbox patterns with passed ones. Forces backend to skip stepping/pausing in scripts with url matching one of the patterns. VM will try to leave blackboxed script by performing 'step in' several times, finally resorting to 'step out' if unsuccessful.
    */
    public void setBlackboxPatterns(jpuppeteer.cdp.cdp.entity.debugger.SetBlackboxPatternsRequest request, int timeout) throws Exception {
        session.send("Debugger.setBlackboxPatterns", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetBlackboxPatterns(jpuppeteer.cdp.cdp.entity.debugger.SetBlackboxPatternsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setBlackboxPatterns", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Makes backend skip steps in the script in blackboxed ranges. VM will try leave blacklisted scripts by performing 'step in' several times, finally resorting to 'step out' if unsuccessful. Positions array contains positions where blackbox state is changed. First interval isn't blackboxed. Array should be sorted.
    */
    public void setBlackboxedRanges(jpuppeteer.cdp.cdp.entity.debugger.SetBlackboxedRangesRequest request, int timeout) throws Exception {
        session.send("Debugger.setBlackboxedRanges", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetBlackboxedRanges(jpuppeteer.cdp.cdp.entity.debugger.SetBlackboxedRangesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setBlackboxedRanges", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets JavaScript breakpoint at a given location.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointResponse setBreakpoint(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointRequest request, int timeout) throws Exception {
        return session.send("Debugger.setBreakpoint", request, jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointResponse> asyncSetBreakpoint(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointResponse.class);
    }

    /**
    * Sets instrumentation breakpoint.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointResponse setInstrumentationBreakpoint(jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointRequest request, int timeout) throws Exception {
        return session.send("Debugger.setInstrumentationBreakpoint", request, jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointResponse> asyncSetInstrumentationBreakpoint(jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setInstrumentationBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.SetInstrumentationBreakpointResponse.class);
    }

    /**
    * Sets JavaScript breakpoint at given location specified either by URL or URL regex. Once this command is issued, all existing parsed scripts will have breakpoints resolved and returned in `locations` property. Further matching script parsing will result in subsequent `breakpointResolved` events issued. This logical breakpoint will survive page reloads.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlResponse setBreakpointByUrl(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlRequest request, int timeout) throws Exception {
        return session.send("Debugger.setBreakpointByUrl", request, jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlResponse> asyncSetBreakpointByUrl(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setBreakpointByUrl", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointByUrlResponse.class);
    }

    /**
    * Sets JavaScript breakpoint before each call to the given function. If another function was created from the same source as a given one, calling it will also trigger the breakpoint.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallResponse setBreakpointOnFunctionCall(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallRequest request, int timeout) throws Exception {
        return session.send("Debugger.setBreakpointOnFunctionCall", request, jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallResponse> asyncSetBreakpointOnFunctionCall(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setBreakpointOnFunctionCall", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointOnFunctionCallResponse.class);
    }

    /**
    * Activates / deactivates all breakpoints on the page.
    */
    public void setBreakpointsActive(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointsActiveRequest request, int timeout) throws Exception {
        session.send("Debugger.setBreakpointsActive", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetBreakpointsActive(jpuppeteer.cdp.cdp.entity.debugger.SetBreakpointsActiveRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setBreakpointsActive", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Defines pause on exceptions state. Can be set to stop on all exceptions, uncaught exceptions or no exceptions. Initial pause on exceptions state is `none`.
    */
    public void setPauseOnExceptions(jpuppeteer.cdp.cdp.entity.debugger.SetPauseOnExceptionsRequest request, int timeout) throws Exception {
        session.send("Debugger.setPauseOnExceptions", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPauseOnExceptions(jpuppeteer.cdp.cdp.entity.debugger.SetPauseOnExceptionsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setPauseOnExceptions", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Changes return value in top frame. Available only at return break position.
    */
    public void setReturnValue(jpuppeteer.cdp.cdp.entity.debugger.SetReturnValueRequest request, int timeout) throws Exception {
        session.send("Debugger.setReturnValue", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetReturnValue(jpuppeteer.cdp.cdp.entity.debugger.SetReturnValueRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setReturnValue", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Edits JavaScript source live.
    */
    public jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceResponse setScriptSource(jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceRequest request, int timeout) throws Exception {
        return session.send("Debugger.setScriptSource", request, jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceResponse> asyncSetScriptSource(jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setScriptSource", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceResponse>(future, jpuppeteer.cdp.cdp.entity.debugger.SetScriptSourceResponse.class);
    }

    /**
    * Makes page not interrupt on any pauses (breakpoint, exception, dom exception etc).
    */
    public void setSkipAllPauses(jpuppeteer.cdp.cdp.entity.debugger.SetSkipAllPausesRequest request, int timeout) throws Exception {
        session.send("Debugger.setSkipAllPauses", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetSkipAllPauses(jpuppeteer.cdp.cdp.entity.debugger.SetSkipAllPausesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setSkipAllPauses", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Changes value of variable in a callframe. Object-based scopes are not supported and must be mutated manually.
    */
    public void setVariableValue(jpuppeteer.cdp.cdp.entity.debugger.SetVariableValueRequest request, int timeout) throws Exception {
        session.send("Debugger.setVariableValue", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetVariableValue(jpuppeteer.cdp.cdp.entity.debugger.SetVariableValueRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.setVariableValue", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Steps into the function call.
    */
    public void stepInto(jpuppeteer.cdp.cdp.entity.debugger.StepIntoRequest request, int timeout) throws Exception {
        session.send("Debugger.stepInto", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStepInto(jpuppeteer.cdp.cdp.entity.debugger.StepIntoRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.stepInto", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Steps out of the function call.
    */
    public void stepOut(int timeout) throws Exception {
        session.send("Debugger.stepOut", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStepOut() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.stepOut");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Steps over the statement.
    */
    public void stepOver(int timeout) throws Exception {
        session.send("Debugger.stepOver", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStepOver() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Debugger.stepOver");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}