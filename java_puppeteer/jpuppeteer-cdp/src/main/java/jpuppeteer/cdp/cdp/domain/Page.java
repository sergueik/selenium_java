package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Page {

    private jpuppeteer.cdp.CDPSession session;

    public Page(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Deprecated, please use addScriptToEvaluateOnNewDocument instead.
    */
    public jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadResponse addScriptToEvaluateOnLoad(jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadRequest request, int timeout) throws Exception {
        return session.send("Page.addScriptToEvaluateOnLoad", request, jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadResponse> asyncAddScriptToEvaluateOnLoad(jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.addScriptToEvaluateOnLoad", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadResponse>(future, jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnLoadResponse.class);
    }

    /**
    * Evaluates given script in every frame upon creation (before loading frame's scripts).
    */
    public jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentResponse addScriptToEvaluateOnNewDocument(jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentRequest request, int timeout) throws Exception {
        return session.send("Page.addScriptToEvaluateOnNewDocument", request, jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentResponse> asyncAddScriptToEvaluateOnNewDocument(jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.addScriptToEvaluateOnNewDocument", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentResponse>(future, jpuppeteer.cdp.cdp.entity.page.AddScriptToEvaluateOnNewDocumentResponse.class);
    }

    /**
    * Brings page to front (activates tab).
    */
    public void bringToFront(int timeout) throws Exception {
        session.send("Page.bringToFront", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncBringToFront() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.bringToFront");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Capture page screenshot.
    */
    public jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotResponse captureScreenshot(jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotRequest request, int timeout) throws Exception {
        return session.send("Page.captureScreenshot", request, jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotResponse> asyncCaptureScreenshot(jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.captureScreenshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotResponse>(future, jpuppeteer.cdp.cdp.entity.page.CaptureScreenshotResponse.class);
    }

    /**
    * Returns a snapshot of the page as a string. For MHTML format, the serialization includes iframes, shadow DOM, external resources, and element-inline styles.
    */
    public jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotResponse captureSnapshot(jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotRequest request, int timeout) throws Exception {
        return session.send("Page.captureSnapshot", request, jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotResponse> asyncCaptureSnapshot(jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.captureSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.page.CaptureSnapshotResponse.class);
    }

    /**
    * Clears the overriden device metrics.
    */
    public void clearDeviceMetricsOverride(int timeout) throws Exception {
        session.send("Page.clearDeviceMetricsOverride", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearDeviceMetricsOverride() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.clearDeviceMetricsOverride");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears the overridden Device Orientation.
    */
    public void clearDeviceOrientationOverride(int timeout) throws Exception {
        session.send("Page.clearDeviceOrientationOverride", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearDeviceOrientationOverride() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.clearDeviceOrientationOverride");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears the overriden Geolocation Position and Error.
    */
    public void clearGeolocationOverride(int timeout) throws Exception {
        session.send("Page.clearGeolocationOverride", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearGeolocationOverride() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.clearGeolocationOverride");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Creates an isolated world for the given frame.
    */
    public jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldResponse createIsolatedWorld(jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldRequest request, int timeout) throws Exception {
        return session.send("Page.createIsolatedWorld", request, jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldResponse> asyncCreateIsolatedWorld(jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.createIsolatedWorld", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldResponse>(future, jpuppeteer.cdp.cdp.entity.page.CreateIsolatedWorldResponse.class);
    }

    /**
    * Deletes browser cookie with given name, domain and path.
    */
    public void deleteCookie(jpuppeteer.cdp.cdp.entity.page.DeleteCookieRequest request, int timeout) throws Exception {
        session.send("Page.deleteCookie", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDeleteCookie(jpuppeteer.cdp.cdp.entity.page.DeleteCookieRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.deleteCookie", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Disables page domain notifications.
    */
    public void disable(int timeout) throws Exception {
        session.send("Page.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables page domain notifications.
    */
    public void enable(int timeout) throws Exception {
        session.send("Page.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    */
    public jpuppeteer.cdp.cdp.entity.page.GetAppManifestResponse getAppManifest(int timeout) throws Exception {
        return session.send("Page.getAppManifest", null, jpuppeteer.cdp.cdp.entity.page.GetAppManifestResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetAppManifestResponse> asyncGetAppManifest() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getAppManifest");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetAppManifestResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetAppManifestResponse.class);
    }

    /**
    */
    public jpuppeteer.cdp.cdp.entity.page.GetInstallabilityErrorsResponse getInstallabilityErrors(int timeout) throws Exception {
        return session.send("Page.getInstallabilityErrors", null, jpuppeteer.cdp.cdp.entity.page.GetInstallabilityErrorsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetInstallabilityErrorsResponse> asyncGetInstallabilityErrors() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getInstallabilityErrors");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetInstallabilityErrorsResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetInstallabilityErrorsResponse.class);
    }

    /**
    */
    public jpuppeteer.cdp.cdp.entity.page.GetManifestIconsResponse getManifestIcons(int timeout) throws Exception {
        return session.send("Page.getManifestIcons", null, jpuppeteer.cdp.cdp.entity.page.GetManifestIconsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetManifestIconsResponse> asyncGetManifestIcons() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getManifestIcons");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetManifestIconsResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetManifestIconsResponse.class);
    }

    /**
    * Returns all browser cookies. Depending on the backend support, will return detailed cookie information in the `cookies` field.
    */
    public jpuppeteer.cdp.cdp.entity.page.GetCookiesResponse getCookies(int timeout) throws Exception {
        return session.send("Page.getCookies", null, jpuppeteer.cdp.cdp.entity.page.GetCookiesResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetCookiesResponse> asyncGetCookies() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getCookies");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetCookiesResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetCookiesResponse.class);
    }

    /**
    * Returns present frame tree structure.
    */
    public jpuppeteer.cdp.cdp.entity.page.GetFrameTreeResponse getFrameTree(int timeout) throws Exception {
        return session.send("Page.getFrameTree", null, jpuppeteer.cdp.cdp.entity.page.GetFrameTreeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetFrameTreeResponse> asyncGetFrameTree() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getFrameTree");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetFrameTreeResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetFrameTreeResponse.class);
    }

    /**
    * Returns metrics relating to the layouting of the page, such as viewport bounds/scale.
    */
    public jpuppeteer.cdp.cdp.entity.page.GetLayoutMetricsResponse getLayoutMetrics(int timeout) throws Exception {
        return session.send("Page.getLayoutMetrics", null, jpuppeteer.cdp.cdp.entity.page.GetLayoutMetricsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetLayoutMetricsResponse> asyncGetLayoutMetrics() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getLayoutMetrics");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetLayoutMetricsResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetLayoutMetricsResponse.class);
    }

    /**
    * Returns navigation history for the current page.
    */
    public jpuppeteer.cdp.cdp.entity.page.GetNavigationHistoryResponse getNavigationHistory(int timeout) throws Exception {
        return session.send("Page.getNavigationHistory", null, jpuppeteer.cdp.cdp.entity.page.GetNavigationHistoryResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetNavigationHistoryResponse> asyncGetNavigationHistory() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getNavigationHistory");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetNavigationHistoryResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetNavigationHistoryResponse.class);
    }

    /**
    * Resets navigation history for the current page.
    */
    public void resetNavigationHistory(int timeout) throws Exception {
        session.send("Page.resetNavigationHistory", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncResetNavigationHistory() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.resetNavigationHistory");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns content of the given resource.
    */
    public jpuppeteer.cdp.cdp.entity.page.GetResourceContentResponse getResourceContent(jpuppeteer.cdp.cdp.entity.page.GetResourceContentRequest request, int timeout) throws Exception {
        return session.send("Page.getResourceContent", request, jpuppeteer.cdp.cdp.entity.page.GetResourceContentResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetResourceContentResponse> asyncGetResourceContent(jpuppeteer.cdp.cdp.entity.page.GetResourceContentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getResourceContent", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetResourceContentResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetResourceContentResponse.class);
    }

    /**
    * Returns present frame / resource tree structure.
    */
    public jpuppeteer.cdp.cdp.entity.page.GetResourceTreeResponse getResourceTree(int timeout) throws Exception {
        return session.send("Page.getResourceTree", null, jpuppeteer.cdp.cdp.entity.page.GetResourceTreeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.GetResourceTreeResponse> asyncGetResourceTree() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.getResourceTree");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.GetResourceTreeResponse>(future, jpuppeteer.cdp.cdp.entity.page.GetResourceTreeResponse.class);
    }

    /**
    * Accepts or dismisses a JavaScript initiated dialog (alert, confirm, prompt, or onbeforeunload).
    */
    public void handleJavaScriptDialog(jpuppeteer.cdp.cdp.entity.page.HandleJavaScriptDialogRequest request, int timeout) throws Exception {
        session.send("Page.handleJavaScriptDialog", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncHandleJavaScriptDialog(jpuppeteer.cdp.cdp.entity.page.HandleJavaScriptDialogRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.handleJavaScriptDialog", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Navigates current page to the given URL.
    */
    public jpuppeteer.cdp.cdp.entity.page.NavigateResponse navigate(jpuppeteer.cdp.cdp.entity.page.NavigateRequest request, int timeout) throws Exception {
        return session.send("Page.navigate", request, jpuppeteer.cdp.cdp.entity.page.NavigateResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.NavigateResponse> asyncNavigate(jpuppeteer.cdp.cdp.entity.page.NavigateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.navigate", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.NavigateResponse>(future, jpuppeteer.cdp.cdp.entity.page.NavigateResponse.class);
    }

    /**
    * Navigates current page to the given history entry.
    */
    public void navigateToHistoryEntry(jpuppeteer.cdp.cdp.entity.page.NavigateToHistoryEntryRequest request, int timeout) throws Exception {
        session.send("Page.navigateToHistoryEntry", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncNavigateToHistoryEntry(jpuppeteer.cdp.cdp.entity.page.NavigateToHistoryEntryRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.navigateToHistoryEntry", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Print page as PDF.
    */
    public jpuppeteer.cdp.cdp.entity.page.PrintToPDFResponse printToPDF(jpuppeteer.cdp.cdp.entity.page.PrintToPDFRequest request, int timeout) throws Exception {
        return session.send("Page.printToPDF", request, jpuppeteer.cdp.cdp.entity.page.PrintToPDFResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.PrintToPDFResponse> asyncPrintToPDF(jpuppeteer.cdp.cdp.entity.page.PrintToPDFRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.printToPDF", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.PrintToPDFResponse>(future, jpuppeteer.cdp.cdp.entity.page.PrintToPDFResponse.class);
    }

    /**
    * Reloads given page optionally ignoring the cache.
    */
    public void reload(jpuppeteer.cdp.cdp.entity.page.ReloadRequest request, int timeout) throws Exception {
        session.send("Page.reload", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncReload(jpuppeteer.cdp.cdp.entity.page.ReloadRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.reload", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Deprecated, please use removeScriptToEvaluateOnNewDocument instead.
    */
    public void removeScriptToEvaluateOnLoad(jpuppeteer.cdp.cdp.entity.page.RemoveScriptToEvaluateOnLoadRequest request, int timeout) throws Exception {
        session.send("Page.removeScriptToEvaluateOnLoad", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveScriptToEvaluateOnLoad(jpuppeteer.cdp.cdp.entity.page.RemoveScriptToEvaluateOnLoadRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.removeScriptToEvaluateOnLoad", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Removes given script from the list.
    */
    public void removeScriptToEvaluateOnNewDocument(jpuppeteer.cdp.cdp.entity.page.RemoveScriptToEvaluateOnNewDocumentRequest request, int timeout) throws Exception {
        session.send("Page.removeScriptToEvaluateOnNewDocument", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncRemoveScriptToEvaluateOnNewDocument(jpuppeteer.cdp.cdp.entity.page.RemoveScriptToEvaluateOnNewDocumentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.removeScriptToEvaluateOnNewDocument", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Acknowledges that a screencast frame has been received by the frontend.
    */
    public void screencastFrameAck(jpuppeteer.cdp.cdp.entity.page.ScreencastFrameAckRequest request, int timeout) throws Exception {
        session.send("Page.screencastFrameAck", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncScreencastFrameAck(jpuppeteer.cdp.cdp.entity.page.ScreencastFrameAckRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.screencastFrameAck", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Searches for given string in resource content.
    */
    public jpuppeteer.cdp.cdp.entity.page.SearchInResourceResponse searchInResource(jpuppeteer.cdp.cdp.entity.page.SearchInResourceRequest request, int timeout) throws Exception {
        return session.send("Page.searchInResource", request, jpuppeteer.cdp.cdp.entity.page.SearchInResourceResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.page.SearchInResourceResponse> asyncSearchInResource(jpuppeteer.cdp.cdp.entity.page.SearchInResourceRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.searchInResource", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.page.SearchInResourceResponse>(future, jpuppeteer.cdp.cdp.entity.page.SearchInResourceResponse.class);
    }

    /**
    * Enable Chrome's experimental ad filter on all sites.
    */
    public void setAdBlockingEnabled(jpuppeteer.cdp.cdp.entity.page.SetAdBlockingEnabledRequest request, int timeout) throws Exception {
        session.send("Page.setAdBlockingEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetAdBlockingEnabled(jpuppeteer.cdp.cdp.entity.page.SetAdBlockingEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setAdBlockingEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enable page Content Security Policy by-passing.
    */
    public void setBypassCSP(jpuppeteer.cdp.cdp.entity.page.SetBypassCSPRequest request, int timeout) throws Exception {
        session.send("Page.setBypassCSP", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetBypassCSP(jpuppeteer.cdp.cdp.entity.page.SetBypassCSPRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setBypassCSP", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides the values of device screen dimensions (window.screen.width, window.screen.height, window.innerWidth, window.innerHeight, and "device-width"/"device-height"-related CSS media query results).
    */
    public void setDeviceMetricsOverride(jpuppeteer.cdp.cdp.entity.page.SetDeviceMetricsOverrideRequest request, int timeout) throws Exception {
        session.send("Page.setDeviceMetricsOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDeviceMetricsOverride(jpuppeteer.cdp.cdp.entity.page.SetDeviceMetricsOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setDeviceMetricsOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides the Device Orientation.
    */
    public void setDeviceOrientationOverride(jpuppeteer.cdp.cdp.entity.page.SetDeviceOrientationOverrideRequest request, int timeout) throws Exception {
        session.send("Page.setDeviceOrientationOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDeviceOrientationOverride(jpuppeteer.cdp.cdp.entity.page.SetDeviceOrientationOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setDeviceOrientationOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Set generic font families.
    */
    public void setFontFamilies(jpuppeteer.cdp.cdp.entity.page.SetFontFamiliesRequest request, int timeout) throws Exception {
        session.send("Page.setFontFamilies", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetFontFamilies(jpuppeteer.cdp.cdp.entity.page.SetFontFamiliesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setFontFamilies", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Set default font sizes.
    */
    public void setFontSizes(jpuppeteer.cdp.cdp.entity.page.SetFontSizesRequest request, int timeout) throws Exception {
        session.send("Page.setFontSizes", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetFontSizes(jpuppeteer.cdp.cdp.entity.page.SetFontSizesRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setFontSizes", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets given markup as the document's HTML.
    */
    public void setDocumentContent(jpuppeteer.cdp.cdp.entity.page.SetDocumentContentRequest request, int timeout) throws Exception {
        session.send("Page.setDocumentContent", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDocumentContent(jpuppeteer.cdp.cdp.entity.page.SetDocumentContentRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setDocumentContent", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Set the behavior when downloading a file.
    */
    public void setDownloadBehavior(jpuppeteer.cdp.cdp.entity.page.SetDownloadBehaviorRequest request, int timeout) throws Exception {
        session.send("Page.setDownloadBehavior", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDownloadBehavior(jpuppeteer.cdp.cdp.entity.page.SetDownloadBehaviorRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setDownloadBehavior", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position unavailable.
    */
    public void setGeolocationOverride(jpuppeteer.cdp.cdp.entity.page.SetGeolocationOverrideRequest request, int timeout) throws Exception {
        session.send("Page.setGeolocationOverride", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetGeolocationOverride(jpuppeteer.cdp.cdp.entity.page.SetGeolocationOverrideRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setGeolocationOverride", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Controls whether page will emit lifecycle events.
    */
    public void setLifecycleEventsEnabled(jpuppeteer.cdp.cdp.entity.page.SetLifecycleEventsEnabledRequest request, int timeout) throws Exception {
        session.send("Page.setLifecycleEventsEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetLifecycleEventsEnabled(jpuppeteer.cdp.cdp.entity.page.SetLifecycleEventsEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setLifecycleEventsEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Toggles mouse event-based touch event emulation.
    */
    public void setTouchEmulationEnabled(jpuppeteer.cdp.cdp.entity.page.SetTouchEmulationEnabledRequest request, int timeout) throws Exception {
        session.send("Page.setTouchEmulationEnabled", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetTouchEmulationEnabled(jpuppeteer.cdp.cdp.entity.page.SetTouchEmulationEnabledRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setTouchEmulationEnabled", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Starts sending each frame using the `screencastFrame` event.
    */
    public void startScreencast(jpuppeteer.cdp.cdp.entity.page.StartScreencastRequest request, int timeout) throws Exception {
        session.send("Page.startScreencast", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStartScreencast(jpuppeteer.cdp.cdp.entity.page.StartScreencastRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.startScreencast", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Force the page stop all navigations and pending resource fetches.
    */
    public void stopLoading(int timeout) throws Exception {
        session.send("Page.stopLoading", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopLoading() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.stopLoading");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Crashes renderer on the IO thread, generates minidumps.
    */
    public void crash(int timeout) throws Exception {
        session.send("Page.crash", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncCrash() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.crash");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Tries to close page, running its beforeunload hooks, if any.
    */
    public void close(int timeout) throws Exception {
        session.send("Page.close", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClose() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.close");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Tries to update the web lifecycle state of the page. It will transition the page to the given state according to: https://github.com/WICG/web-lifecycle/
    */
    public void setWebLifecycleState(jpuppeteer.cdp.cdp.entity.page.SetWebLifecycleStateRequest request, int timeout) throws Exception {
        session.send("Page.setWebLifecycleState", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetWebLifecycleState(jpuppeteer.cdp.cdp.entity.page.SetWebLifecycleStateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setWebLifecycleState", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Stops sending each frame in the `screencastFrame`.
    */
    public void stopScreencast(int timeout) throws Exception {
        session.send("Page.stopScreencast", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncStopScreencast() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.stopScreencast");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Forces compilation cache to be generated for every subresource script.
    */
    public void setProduceCompilationCache(jpuppeteer.cdp.cdp.entity.page.SetProduceCompilationCacheRequest request, int timeout) throws Exception {
        session.send("Page.setProduceCompilationCache", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetProduceCompilationCache(jpuppeteer.cdp.cdp.entity.page.SetProduceCompilationCacheRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setProduceCompilationCache", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Seeds compilation cache for given url. Compilation cache does not survive cross-process navigation.
    */
    public void addCompilationCache(jpuppeteer.cdp.cdp.entity.page.AddCompilationCacheRequest request, int timeout) throws Exception {
        session.send("Page.addCompilationCache", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncAddCompilationCache(jpuppeteer.cdp.cdp.entity.page.AddCompilationCacheRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.addCompilationCache", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Clears seeded compilation cache.
    */
    public void clearCompilationCache(int timeout) throws Exception {
        session.send("Page.clearCompilationCache", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClearCompilationCache() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.clearCompilationCache");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Generates a report for testing.
    */
    public void generateTestReport(jpuppeteer.cdp.cdp.entity.page.GenerateTestReportRequest request, int timeout) throws Exception {
        session.send("Page.generateTestReport", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncGenerateTestReport(jpuppeteer.cdp.cdp.entity.page.GenerateTestReportRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.generateTestReport", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Pauses page execution. Can be resumed using generic Runtime.runIfWaitingForDebugger.
    */
    public void waitForDebugger(int timeout) throws Exception {
        session.send("Page.waitForDebugger", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncWaitForDebugger() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.waitForDebugger");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Intercept file chooser requests and transfer control to protocol clients. When file chooser interception is enabled, native file chooser dialog is not shown. Instead, a protocol event `Page.fileChooserOpened` is emitted.
    */
    public void setInterceptFileChooserDialog(jpuppeteer.cdp.cdp.entity.page.SetInterceptFileChooserDialogRequest request, int timeout) throws Exception {
        session.send("Page.setInterceptFileChooserDialog", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetInterceptFileChooserDialog(jpuppeteer.cdp.cdp.entity.page.SetInterceptFileChooserDialogRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Page.setInterceptFileChooserDialog", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}