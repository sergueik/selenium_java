package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class CSS {

    private jpuppeteer.cdp.CDPSession session;

    public CSS(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Inserts a new rule with the given `ruleText` in a stylesheet with given `styleSheetId`, at the position specified by `location`.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.AddRuleResponse addRule(jpuppeteer.cdp.cdp.entity.css.AddRuleRequest request, int timeout) throws Exception {
        return session.send("CSS.addRule", request, jpuppeteer.cdp.cdp.entity.css.AddRuleResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.AddRuleResponse> asyncAddRule(jpuppeteer.cdp.cdp.entity.css.AddRuleRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.addRule", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.AddRuleResponse>(future, jpuppeteer.cdp.cdp.entity.css.AddRuleResponse.class);
    }

    /**
    * Returns all class names from specified stylesheet.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.CollectClassNamesResponse collectClassNames(jpuppeteer.cdp.cdp.entity.css.CollectClassNamesRequest request, int timeout) throws Exception {
        return session.send("CSS.collectClassNames", request, jpuppeteer.cdp.cdp.entity.css.CollectClassNamesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.CollectClassNamesResponse> asyncCollectClassNames(jpuppeteer.cdp.cdp.entity.css.CollectClassNamesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.collectClassNames", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.CollectClassNamesResponse>(future, jpuppeteer.cdp.cdp.entity.css.CollectClassNamesResponse.class);
    }

    /**
    * Creates a new special "via-inspector" stylesheet in the frame with given `frameId`.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetResponse createStyleSheet(jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetRequest request, int timeout) throws Exception {
        return session.send("CSS.createStyleSheet", request, jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetResponse> asyncCreateStyleSheet(jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.createStyleSheet", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetResponse>(future, jpuppeteer.cdp.cdp.entity.css.CreateStyleSheetResponse.class);
    }

    /**
    * Disables the CSS agent for the given page.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("CSS.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables the CSS agent for the given page. Clients should not assume that the CSS agent has been enabled until the result of this command is received.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("CSS.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Ensures that the given node will have specified pseudo-classes whenever its style is computed by the browser.
    * experimental
    */
    public void forcePseudoState(jpuppeteer.cdp.cdp.entity.css.ForcePseudoStateRequest request, int timeout) throws Exception {
        session.send("CSS.forcePseudoState", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncForcePseudoState(jpuppeteer.cdp.cdp.entity.css.ForcePseudoStateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.forcePseudoState", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsResponse getBackgroundColors(jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsRequest request, int timeout) throws Exception {
        return session.send("CSS.getBackgroundColors", request, jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsResponse> asyncGetBackgroundColors(jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getBackgroundColors", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetBackgroundColorsResponse.class);
    }

    /**
    * Returns the computed style for a DOM node identified by `nodeId`.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeResponse getComputedStyleForNode(jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeRequest request, int timeout) throws Exception {
        return session.send("CSS.getComputedStyleForNode", request, jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeResponse> asyncGetComputedStyleForNode(jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getComputedStyleForNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetComputedStyleForNodeResponse.class);
    }

    /**
    * Returns the styles defined inline (explicitly in the "style" attribute and implicitly, using DOM attributes) for a DOM node identified by `nodeId`.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeResponse getInlineStylesForNode(jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeRequest request, int timeout) throws Exception {
        return session.send("CSS.getInlineStylesForNode", request, jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeResponse> asyncGetInlineStylesForNode(jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getInlineStylesForNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetInlineStylesForNodeResponse.class);
    }

    /**
    * Returns requested styles for a DOM node identified by `nodeId`.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeResponse getMatchedStylesForNode(jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeRequest request, int timeout) throws Exception {
        return session.send("CSS.getMatchedStylesForNode", request, jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeResponse> asyncGetMatchedStylesForNode(jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getMatchedStylesForNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetMatchedStylesForNodeResponse.class);
    }

    /**
    * Returns all media queries parsed by the rendering engine.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetMediaQueriesResponse getMediaQueries(int timeout) throws Exception {
        return session.send("CSS.getMediaQueries", null, jpuppeteer.cdp.cdp.entity.css.GetMediaQueriesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetMediaQueriesResponse> asyncGetMediaQueries() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getMediaQueries");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetMediaQueriesResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetMediaQueriesResponse.class);
    }

    /**
    * Requests information about platform fonts which we used to render child TextNodes in the given node.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeResponse getPlatformFontsForNode(jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeRequest request, int timeout) throws Exception {
        return session.send("CSS.getPlatformFontsForNode", request, jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeResponse> asyncGetPlatformFontsForNode(jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getPlatformFontsForNode", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetPlatformFontsForNodeResponse.class);
    }

    /**
    * Returns the current textual content for a stylesheet.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextResponse getStyleSheetText(jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextRequest request, int timeout) throws Exception {
        return session.send("CSS.getStyleSheetText", request, jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextResponse> asyncGetStyleSheetText(jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.getStyleSheetText", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextResponse>(future, jpuppeteer.cdp.cdp.entity.css.GetStyleSheetTextResponse.class);
    }

    /**
    * Find a rule with the given active property for the given node and set the new value for this property
    * experimental
    */
    public void setEffectivePropertyValueForNode(jpuppeteer.cdp.cdp.entity.css.SetEffectivePropertyValueForNodeRequest request, int timeout) throws Exception {
        session.send("CSS.setEffectivePropertyValueForNode", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetEffectivePropertyValueForNode(jpuppeteer.cdp.cdp.entity.css.SetEffectivePropertyValueForNodeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.setEffectivePropertyValueForNode", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Modifies the keyframe rule key text.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyResponse setKeyframeKey(jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyRequest request, int timeout) throws Exception {
        return session.send("CSS.setKeyframeKey", request, jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyResponse> asyncSetKeyframeKey(jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.setKeyframeKey", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyResponse>(future, jpuppeteer.cdp.cdp.entity.css.SetKeyframeKeyResponse.class);
    }

    /**
    * Modifies the rule selector.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.SetMediaTextResponse setMediaText(jpuppeteer.cdp.cdp.entity.css.SetMediaTextRequest request, int timeout) throws Exception {
        return session.send("CSS.setMediaText", request, jpuppeteer.cdp.cdp.entity.css.SetMediaTextResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.SetMediaTextResponse> asyncSetMediaText(jpuppeteer.cdp.cdp.entity.css.SetMediaTextRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.setMediaText", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.SetMediaTextResponse>(future, jpuppeteer.cdp.cdp.entity.css.SetMediaTextResponse.class);
    }

    /**
    * Modifies the rule selector.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorResponse setRuleSelector(jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorRequest request, int timeout) throws Exception {
        return session.send("CSS.setRuleSelector", request, jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorResponse> asyncSetRuleSelector(jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.setRuleSelector", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorResponse>(future, jpuppeteer.cdp.cdp.entity.css.SetRuleSelectorResponse.class);
    }

    /**
    * Sets the new stylesheet text.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextResponse setStyleSheetText(jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextRequest request, int timeout) throws Exception {
        return session.send("CSS.setStyleSheetText", request, jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextResponse> asyncSetStyleSheetText(jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.setStyleSheetText", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextResponse>(future, jpuppeteer.cdp.cdp.entity.css.SetStyleSheetTextResponse.class);
    }

    /**
    * Applies specified style edits one after another in the given order.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.SetStyleTextsResponse setStyleTexts(jpuppeteer.cdp.cdp.entity.css.SetStyleTextsRequest request, int timeout) throws Exception {
        return session.send("CSS.setStyleTexts", request, jpuppeteer.cdp.cdp.entity.css.SetStyleTextsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.SetStyleTextsResponse> asyncSetStyleTexts(jpuppeteer.cdp.cdp.entity.css.SetStyleTextsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.setStyleTexts", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.SetStyleTextsResponse>(future, jpuppeteer.cdp.cdp.entity.css.SetStyleTextsResponse.class);
    }

    /**
    * Enables the selector recording.
    * experimental
    */
    public void startRuleUsageTracking(int timeout) throws Exception {
        session.send("CSS.startRuleUsageTracking", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartRuleUsageTracking() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.startRuleUsageTracking");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Stop tracking rule usage and return the list of rules that were used since last call to `takeCoverageDelta` (or since start of coverage instrumentation)
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.StopRuleUsageTrackingResponse stopRuleUsageTracking(int timeout) throws Exception {
        return session.send("CSS.stopRuleUsageTracking", null, jpuppeteer.cdp.cdp.entity.css.StopRuleUsageTrackingResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.StopRuleUsageTrackingResponse> asyncStopRuleUsageTracking() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.stopRuleUsageTracking");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.StopRuleUsageTrackingResponse>(future, jpuppeteer.cdp.cdp.entity.css.StopRuleUsageTrackingResponse.class);
    }

    /**
    * Obtain list of rules that became used since last call to this method (or since start of coverage instrumentation)
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.css.TakeCoverageDeltaResponse takeCoverageDelta(int timeout) throws Exception {
        return session.send("CSS.takeCoverageDelta", null, jpuppeteer.cdp.cdp.entity.css.TakeCoverageDeltaResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.css.TakeCoverageDeltaResponse> asyncTakeCoverageDelta() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("CSS.takeCoverageDelta");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.css.TakeCoverageDeltaResponse>(future, jpuppeteer.cdp.cdp.entity.css.TakeCoverageDeltaResponse.class);
    }
}