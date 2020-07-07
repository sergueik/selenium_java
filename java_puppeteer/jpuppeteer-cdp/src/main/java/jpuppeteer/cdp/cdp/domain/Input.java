package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Input {

    private jpuppeteer.cdp.CDPSession session;

    public Input(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Dispatches a key event to the page.
    */
    public void dispatchKeyEvent(jpuppeteer.cdp.cdp.entity.input.DispatchKeyEventRequest request, int timeout) throws Exception {
        session.send("Input.dispatchKeyEvent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDispatchKeyEvent(jpuppeteer.cdp.cdp.entity.input.DispatchKeyEventRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.dispatchKeyEvent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * This method emulates inserting text that doesn't come from a key press, for example an emoji keyboard or an IME.
    */
    public void insertText(jpuppeteer.cdp.cdp.entity.input.InsertTextRequest request, int timeout) throws Exception {
        session.send("Input.insertText", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncInsertText(jpuppeteer.cdp.cdp.entity.input.InsertTextRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.insertText", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Dispatches a mouse event to the page.
    */
    public void dispatchMouseEvent(jpuppeteer.cdp.cdp.entity.input.DispatchMouseEventRequest request, int timeout) throws Exception {
        session.send("Input.dispatchMouseEvent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDispatchMouseEvent(jpuppeteer.cdp.cdp.entity.input.DispatchMouseEventRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.dispatchMouseEvent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Dispatches a touch event to the page.
    */
    public void dispatchTouchEvent(jpuppeteer.cdp.cdp.entity.input.DispatchTouchEventRequest request, int timeout) throws Exception {
        session.send("Input.dispatchTouchEvent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDispatchTouchEvent(jpuppeteer.cdp.cdp.entity.input.DispatchTouchEventRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.dispatchTouchEvent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Emulates touch event from the mouse event parameters.
    */
    public void emulateTouchFromMouseEvent(jpuppeteer.cdp.cdp.entity.input.EmulateTouchFromMouseEventRequest request, int timeout) throws Exception {
        session.send("Input.emulateTouchFromMouseEvent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEmulateTouchFromMouseEvent(jpuppeteer.cdp.cdp.entity.input.EmulateTouchFromMouseEventRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.emulateTouchFromMouseEvent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Ignores input events (useful while auditing page).
    */
    public void setIgnoreInputEvents(jpuppeteer.cdp.cdp.entity.input.SetIgnoreInputEventsRequest request, int timeout) throws Exception {
        session.send("Input.setIgnoreInputEvents", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetIgnoreInputEvents(jpuppeteer.cdp.cdp.entity.input.SetIgnoreInputEventsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.setIgnoreInputEvents", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Synthesizes a pinch gesture over a time period by issuing appropriate touch events.
    */
    public void synthesizePinchGesture(jpuppeteer.cdp.cdp.entity.input.SynthesizePinchGestureRequest request, int timeout) throws Exception {
        session.send("Input.synthesizePinchGesture", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSynthesizePinchGesture(jpuppeteer.cdp.cdp.entity.input.SynthesizePinchGestureRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.synthesizePinchGesture", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Synthesizes a scroll gesture over a time period by issuing appropriate touch events.
    */
    public void synthesizeScrollGesture(jpuppeteer.cdp.cdp.entity.input.SynthesizeScrollGestureRequest request, int timeout) throws Exception {
        session.send("Input.synthesizeScrollGesture", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSynthesizeScrollGesture(jpuppeteer.cdp.cdp.entity.input.SynthesizeScrollGestureRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.synthesizeScrollGesture", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Synthesizes a tap gesture over a time period by issuing appropriate touch events.
    */
    public void synthesizeTapGesture(jpuppeteer.cdp.cdp.entity.input.SynthesizeTapGestureRequest request, int timeout) throws Exception {
        session.send("Input.synthesizeTapGesture", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSynthesizeTapGesture(jpuppeteer.cdp.cdp.entity.input.SynthesizeTapGestureRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Input.synthesizeTapGesture", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}