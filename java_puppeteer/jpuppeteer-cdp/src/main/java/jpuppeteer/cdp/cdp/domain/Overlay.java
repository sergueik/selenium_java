package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Overlay {

    private jpuppeteer.cdp.CDPSession session;

    public Overlay(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables domain notifications.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Overlay.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables domain notifications.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("Overlay.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * For testing.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestResponse getHighlightObjectForTest(jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestRequest request, int timeout) throws Exception {
        return session.send("Overlay.getHighlightObjectForTest", request, jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestResponse> asyncGetHighlightObjectForTest(jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.getHighlightObjectForTest", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestResponse>(future, jpuppeteer.cdp.cdp.entity.overlay.GetHighlightObjectForTestResponse.class);
    }

    /**
    * Hides any highlight.
    * experimental
    */
    public void hideHighlight(int timeout) throws Exception {
        session.send("Overlay.hideHighlight", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHideHighlight() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.hideHighlight");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights owner element of the frame with given id.
    * experimental
    */
    public void highlightFrame(jpuppeteer.cdp.cdp.entity.overlay.HighlightFrameRequest request, int timeout) throws Exception {
        session.send("Overlay.highlightFrame", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHighlightFrame(jpuppeteer.cdp.cdp.entity.overlay.HighlightFrameRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.highlightFrame", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights DOM node with given id or with the given JavaScript object wrapper. Either nodeId or objectId must be specified.
    * experimental
    */
    public void highlightNode(jpuppeteer.cdp.cdp.entity.overlay.HighlightNodeRequest request, int timeout) throws Exception {
        session.send("Overlay.highlightNode", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHighlightNode(jpuppeteer.cdp.cdp.entity.overlay.HighlightNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.highlightNode", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights given quad. Coordinates are absolute with respect to the main frame viewport.
    * experimental
    */
    public void highlightQuad(jpuppeteer.cdp.cdp.entity.overlay.HighlightQuadRequest request, int timeout) throws Exception {
        session.send("Overlay.highlightQuad", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHighlightQuad(jpuppeteer.cdp.cdp.entity.overlay.HighlightQuadRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.highlightQuad", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights given rectangle. Coordinates are absolute with respect to the main frame viewport.
    * experimental
    */
    public void highlightRect(jpuppeteer.cdp.cdp.entity.overlay.HighlightRectRequest request, int timeout) throws Exception {
        session.send("Overlay.highlightRect", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHighlightRect(jpuppeteer.cdp.cdp.entity.overlay.HighlightRectRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.highlightRect", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enters the 'inspect' mode. In this mode, elements that user is hovering over are highlighted. Backend then generates 'inspectNodeRequested' event upon element selection.
    * experimental
    */
    public void setInspectMode(jpuppeteer.cdp.cdp.entity.overlay.SetInspectModeRequest request, int timeout) throws Exception {
        session.send("Overlay.setInspectMode", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetInspectMode(jpuppeteer.cdp.cdp.entity.overlay.SetInspectModeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setInspectMode", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Highlights owner element of all frames detected to be ads.
    * experimental
    */
    public void setShowAdHighlights(jpuppeteer.cdp.cdp.entity.overlay.SetShowAdHighlightsRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowAdHighlights", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowAdHighlights(jpuppeteer.cdp.cdp.entity.overlay.SetShowAdHighlightsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowAdHighlights", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public void setPausedInDebuggerMessage(jpuppeteer.cdp.cdp.entity.overlay.SetPausedInDebuggerMessageRequest request, int timeout) throws Exception {
        session.send("Overlay.setPausedInDebuggerMessage", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPausedInDebuggerMessage(jpuppeteer.cdp.cdp.entity.overlay.SetPausedInDebuggerMessageRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setPausedInDebuggerMessage", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that backend shows debug borders on layers
    * experimental
    */
    public void setShowDebugBorders(jpuppeteer.cdp.cdp.entity.overlay.SetShowDebugBordersRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowDebugBorders", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowDebugBorders(jpuppeteer.cdp.cdp.entity.overlay.SetShowDebugBordersRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowDebugBorders", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that backend shows the FPS counter
    * experimental
    */
    public void setShowFPSCounter(jpuppeteer.cdp.cdp.entity.overlay.SetShowFPSCounterRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowFPSCounter", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowFPSCounter(jpuppeteer.cdp.cdp.entity.overlay.SetShowFPSCounterRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowFPSCounter", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that backend shows paint rectangles
    * experimental
    */
    public void setShowPaintRects(jpuppeteer.cdp.cdp.entity.overlay.SetShowPaintRectsRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowPaintRects", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowPaintRects(jpuppeteer.cdp.cdp.entity.overlay.SetShowPaintRectsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowPaintRects", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that backend shows layout shift regions
    * experimental
    */
    public void setShowLayoutShiftRegions(jpuppeteer.cdp.cdp.entity.overlay.SetShowLayoutShiftRegionsRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowLayoutShiftRegions", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowLayoutShiftRegions(jpuppeteer.cdp.cdp.entity.overlay.SetShowLayoutShiftRegionsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowLayoutShiftRegions", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that backend shows scroll bottleneck rects
    * experimental
    */
    public void setShowScrollBottleneckRects(jpuppeteer.cdp.cdp.entity.overlay.SetShowScrollBottleneckRectsRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowScrollBottleneckRects", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowScrollBottleneckRects(jpuppeteer.cdp.cdp.entity.overlay.SetShowScrollBottleneckRectsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowScrollBottleneckRects", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that backend shows hit-test borders on layers
    * experimental
    */
    public void setShowHitTestBorders(jpuppeteer.cdp.cdp.entity.overlay.SetShowHitTestBordersRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowHitTestBorders", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowHitTestBorders(jpuppeteer.cdp.cdp.entity.overlay.SetShowHitTestBordersRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowHitTestBorders", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Paints viewport size upon main frame resize.
    * experimental
    */
    public void setShowViewportSizeOnResize(jpuppeteer.cdp.cdp.entity.overlay.SetShowViewportSizeOnResizeRequest request, int timeout) throws Exception {
        session.send("Overlay.setShowViewportSizeOnResize", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetShowViewportSizeOnResize(jpuppeteer.cdp.cdp.entity.overlay.SetShowViewportSizeOnResizeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Overlay.setShowViewportSizeOnResize", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}