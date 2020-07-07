package jpuppeteer.cdp.cdp.domain;

/**
*/
public class DOMDebugger {

    private jpuppeteer.cdp.CDPSession session;

    public DOMDebugger(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Returns event listeners of the given object.
    */
    public jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersResponse getEventListeners(jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersRequest request, int timeout) throws Exception {
        return session.send("DOMDebugger.getEventListeners", request, jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersResponse> asyncGetEventListeners(jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.getEventListeners", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersResponse>(future, jpuppeteer.cdp.cdp.entity.domdebugger.GetEventListenersResponse.class);
    }

    /**
    * Removes DOM breakpoint that was set using `setDOMBreakpoint`.
    */
    public void removeDOMBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveDOMBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.removeDOMBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveDOMBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveDOMBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.removeDOMBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes breakpoint on particular DOM event.
    */
    public void removeEventListenerBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveEventListenerBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.removeEventListenerBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveEventListenerBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveEventListenerBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.removeEventListenerBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes breakpoint on particular native event.
    */
    public void removeInstrumentationBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveInstrumentationBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.removeInstrumentationBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveInstrumentationBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveInstrumentationBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.removeInstrumentationBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes breakpoint from XMLHttpRequest.
    */
    public void removeXHRBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveXHRBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.removeXHRBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveXHRBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.RemoveXHRBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.removeXHRBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets breakpoint on particular operation with DOM.
    */
    public void setDOMBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetDOMBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.setDOMBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDOMBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetDOMBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.setDOMBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets breakpoint on particular DOM event.
    */
    public void setEventListenerBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetEventListenerBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.setEventListenerBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetEventListenerBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetEventListenerBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.setEventListenerBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets breakpoint on particular native event.
    */
    public void setInstrumentationBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetInstrumentationBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.setInstrumentationBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetInstrumentationBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetInstrumentationBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.setInstrumentationBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets breakpoint on XMLHttpRequest.
    */
    public void setXHRBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetXHRBreakpointRequest request, int timeout) throws Exception {
        session.send("DOMDebugger.setXHRBreakpoint", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetXHRBreakpoint(jpuppeteer.cdp.cdp.entity.domdebugger.SetXHRBreakpointRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMDebugger.setXHRBreakpoint", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}