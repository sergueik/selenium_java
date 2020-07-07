package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Emulation {

    private jpuppeteer.cdp.CDPSession session;

    public Emulation(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Tells whether emulation is supported.
    */
    public jpuppeteer.cdp.cdp.entity.emulation.CanEmulateResponse canEmulate(int timeout) throws Exception {
        return session.send("Emulation.canEmulate", null, jpuppeteer.cdp.cdp.entity.emulation.CanEmulateResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.emulation.CanEmulateResponse> asyncCanEmulate() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.canEmulate");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.emulation.CanEmulateResponse>(future, jpuppeteer.cdp.cdp.entity.emulation.CanEmulateResponse.class);
    }

    /**
    * Clears the overriden device metrics.
    */
    public void clearDeviceMetricsOverride(int timeout) throws Exception {
        session.send("Emulation.clearDeviceMetricsOverride", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearDeviceMetricsOverride() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.clearDeviceMetricsOverride");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears the overriden Geolocation Position and Error.
    */
    public void clearGeolocationOverride(int timeout) throws Exception {
        session.send("Emulation.clearGeolocationOverride", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearGeolocationOverride() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.clearGeolocationOverride");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Requests that page scale factor is reset to initial values.
    */
    public void resetPageScaleFactor(int timeout) throws Exception {
        session.send("Emulation.resetPageScaleFactor", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncResetPageScaleFactor() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.resetPageScaleFactor");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables or disables simulating a focused and active page.
    */
    public void setFocusEmulationEnabled(jpuppeteer.cdp.cdp.entity.emulation.SetFocusEmulationEnabledRequest request, int timeout) throws Exception {
        session.send("Emulation.setFocusEmulationEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetFocusEmulationEnabled(jpuppeteer.cdp.cdp.entity.emulation.SetFocusEmulationEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setFocusEmulationEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables CPU throttling to emulate slow CPUs.
    */
    public void setCPUThrottlingRate(jpuppeteer.cdp.cdp.entity.emulation.SetCPUThrottlingRateRequest request, int timeout) throws Exception {
        session.send("Emulation.setCPUThrottlingRate", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetCPUThrottlingRate(jpuppeteer.cdp.cdp.entity.emulation.SetCPUThrottlingRateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setCPUThrottlingRate", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets or clears an override of the default background color of the frame. This override is used if the content does not specify one.
    */
    public void setDefaultBackgroundColorOverride(jpuppeteer.cdp.cdp.entity.emulation.SetDefaultBackgroundColorOverrideRequest request, int timeout) throws Exception {
        session.send("Emulation.setDefaultBackgroundColorOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDefaultBackgroundColorOverride(jpuppeteer.cdp.cdp.entity.emulation.SetDefaultBackgroundColorOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setDefaultBackgroundColorOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides the values of device screen dimensions (window.screen.width, window.screen.height, window.innerWidth, window.innerHeight, and "device-width"/"device-height"-related CSS media query results).
    */
    public void setDeviceMetricsOverride(jpuppeteer.cdp.cdp.entity.emulation.SetDeviceMetricsOverrideRequest request, int timeout) throws Exception {
        session.send("Emulation.setDeviceMetricsOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDeviceMetricsOverride(jpuppeteer.cdp.cdp.entity.emulation.SetDeviceMetricsOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setDeviceMetricsOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void setScrollbarsHidden(jpuppeteer.cdp.cdp.entity.emulation.SetScrollbarsHiddenRequest request, int timeout) throws Exception {
        session.send("Emulation.setScrollbarsHidden", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetScrollbarsHidden(jpuppeteer.cdp.cdp.entity.emulation.SetScrollbarsHiddenRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setScrollbarsHidden", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void setDocumentCookieDisabled(jpuppeteer.cdp.cdp.entity.emulation.SetDocumentCookieDisabledRequest request, int timeout) throws Exception {
        session.send("Emulation.setDocumentCookieDisabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDocumentCookieDisabled(jpuppeteer.cdp.cdp.entity.emulation.SetDocumentCookieDisabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setDocumentCookieDisabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public void setEmitTouchEventsForMouse(jpuppeteer.cdp.cdp.entity.emulation.SetEmitTouchEventsForMouseRequest request, int timeout) throws Exception {
        session.send("Emulation.setEmitTouchEventsForMouse", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetEmitTouchEventsForMouse(jpuppeteer.cdp.cdp.entity.emulation.SetEmitTouchEventsForMouseRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setEmitTouchEventsForMouse", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Emulates the given media type or media feature for CSS media queries.
    */
    public void setEmulatedMedia(jpuppeteer.cdp.cdp.entity.emulation.SetEmulatedMediaRequest request, int timeout) throws Exception {
        session.send("Emulation.setEmulatedMedia", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetEmulatedMedia(jpuppeteer.cdp.cdp.entity.emulation.SetEmulatedMediaRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setEmulatedMedia", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position unavailable.
    */
    public void setGeolocationOverride(jpuppeteer.cdp.cdp.entity.emulation.SetGeolocationOverrideRequest request, int timeout) throws Exception {
        session.send("Emulation.setGeolocationOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetGeolocationOverride(jpuppeteer.cdp.cdp.entity.emulation.SetGeolocationOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setGeolocationOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides value returned by the javascript navigator object.
    */
    public void setNavigatorOverrides(jpuppeteer.cdp.cdp.entity.emulation.SetNavigatorOverridesRequest request, int timeout) throws Exception {
        session.send("Emulation.setNavigatorOverrides", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetNavigatorOverrides(jpuppeteer.cdp.cdp.entity.emulation.SetNavigatorOverridesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setNavigatorOverrides", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets a specified page scale factor.
    */
    public void setPageScaleFactor(jpuppeteer.cdp.cdp.entity.emulation.SetPageScaleFactorRequest request, int timeout) throws Exception {
        session.send("Emulation.setPageScaleFactor", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPageScaleFactor(jpuppeteer.cdp.cdp.entity.emulation.SetPageScaleFactorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setPageScaleFactor", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Switches script execution in the page.
    */
    public void setScriptExecutionDisabled(jpuppeteer.cdp.cdp.entity.emulation.SetScriptExecutionDisabledRequest request, int timeout) throws Exception {
        session.send("Emulation.setScriptExecutionDisabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetScriptExecutionDisabled(jpuppeteer.cdp.cdp.entity.emulation.SetScriptExecutionDisabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setScriptExecutionDisabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables touch on platforms which do not support them.
    */
    public void setTouchEmulationEnabled(jpuppeteer.cdp.cdp.entity.emulation.SetTouchEmulationEnabledRequest request, int timeout) throws Exception {
        session.send("Emulation.setTouchEmulationEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetTouchEmulationEnabled(jpuppeteer.cdp.cdp.entity.emulation.SetTouchEmulationEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setTouchEmulationEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Turns on virtual time for all frames (replacing real-time with a synthetic time source) and sets the current virtual time policy.  Note this supersedes any previous time budget.
    */
    public jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyResponse setVirtualTimePolicy(jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyRequest request, int timeout) throws Exception {
        return session.send("Emulation.setVirtualTimePolicy", request, jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyResponse> asyncSetVirtualTimePolicy(jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setVirtualTimePolicy", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyResponse>(future, jpuppeteer.cdp.cdp.entity.emulation.SetVirtualTimePolicyResponse.class);
    }

    /**
    * Overrides default host system timezone with the specified one.
    */
    public void setTimezoneOverride(jpuppeteer.cdp.cdp.entity.emulation.SetTimezoneOverrideRequest request, int timeout) throws Exception {
        session.send("Emulation.setTimezoneOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetTimezoneOverride(jpuppeteer.cdp.cdp.entity.emulation.SetTimezoneOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setTimezoneOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Resizes the frame/viewport of the page. Note that this does not affect the frame's container (e.g. browser window). Can be used to produce screenshots of the specified size. Not supported on Android.
    */
    public void setVisibleSize(jpuppeteer.cdp.cdp.entity.emulation.SetVisibleSizeRequest request, int timeout) throws Exception {
        session.send("Emulation.setVisibleSize", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetVisibleSize(jpuppeteer.cdp.cdp.entity.emulation.SetVisibleSizeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setVisibleSize", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Allows overriding user agent with the given string.
    */
    public void setUserAgentOverride(jpuppeteer.cdp.cdp.entity.emulation.SetUserAgentOverrideRequest request, int timeout) throws Exception {
        session.send("Emulation.setUserAgentOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetUserAgentOverride(jpuppeteer.cdp.cdp.entity.emulation.SetUserAgentOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Emulation.setUserAgentOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}