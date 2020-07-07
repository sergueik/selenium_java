package jpuppeteer.cdp.cdp.domain;

/**
*/
public class Browser {

    private jpuppeteer.cdp.CDPConnection connection;

    public Browser(jpuppeteer.cdp.CDPConnection connection) {
        this.connection = connection;
    }

    /**
    * Set permission settings for given origin.
    */
    public void setPermission(jpuppeteer.cdp.cdp.entity.browser.SetPermissionRequest request, int timeout) throws Exception {
        connection.send("Browser.setPermission", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPermission(jpuppeteer.cdp.cdp.entity.browser.SetPermissionRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.setPermission", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Grant specific permissions to the given origin and reject all others.
    */
    public void grantPermissions(jpuppeteer.cdp.cdp.entity.browser.GrantPermissionsRequest request, int timeout) throws Exception {
        connection.send("Browser.grantPermissions", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncGrantPermissions(jpuppeteer.cdp.cdp.entity.browser.GrantPermissionsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.grantPermissions", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Reset all permission management for all origins.
    */
    public void resetPermissions(jpuppeteer.cdp.cdp.entity.browser.ResetPermissionsRequest request, int timeout) throws Exception {
        connection.send("Browser.resetPermissions", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncResetPermissions(jpuppeteer.cdp.cdp.entity.browser.ResetPermissionsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.resetPermissions", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Close browser gracefully.
    */
    public void close(int timeout) throws Exception {
        connection.send("Browser.close", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncClose() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.close");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Crashes browser on the main thread.
    */
    public void crash(int timeout) throws Exception {
        connection.send("Browser.crash", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncCrash() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.crash");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Crashes GPU process.
    */
    public void crashGpuProcess(int timeout) throws Exception {
        connection.send("Browser.crashGpuProcess", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncCrashGpuProcess() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.crashGpuProcess");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns version information.
    */
    public jpuppeteer.cdp.cdp.entity.browser.GetVersionResponse getVersion(int timeout) throws Exception {
        return connection.send("Browser.getVersion", null, jpuppeteer.cdp.cdp.entity.browser.GetVersionResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.browser.GetVersionResponse> asyncGetVersion() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.getVersion");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.browser.GetVersionResponse>(future, jpuppeteer.cdp.cdp.entity.browser.GetVersionResponse.class);
    }

    /**
    * Returns the command line switches for the browser process if, and only if --enable-automation is on the commandline.
    */
    public jpuppeteer.cdp.cdp.entity.browser.GetBrowserCommandLineResponse getBrowserCommandLine(int timeout) throws Exception {
        return connection.send("Browser.getBrowserCommandLine", null, jpuppeteer.cdp.cdp.entity.browser.GetBrowserCommandLineResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.browser.GetBrowserCommandLineResponse> asyncGetBrowserCommandLine() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.getBrowserCommandLine");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.browser.GetBrowserCommandLineResponse>(future, jpuppeteer.cdp.cdp.entity.browser.GetBrowserCommandLineResponse.class);
    }

    /**
    * Get Chrome histograms.
    */
    public jpuppeteer.cdp.cdp.entity.browser.GetHistogramsResponse getHistograms(jpuppeteer.cdp.cdp.entity.browser.GetHistogramsRequest request, int timeout) throws Exception {
        return connection.send("Browser.getHistograms", request, jpuppeteer.cdp.cdp.entity.browser.GetHistogramsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.browser.GetHistogramsResponse> asyncGetHistograms(jpuppeteer.cdp.cdp.entity.browser.GetHistogramsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.getHistograms", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.browser.GetHistogramsResponse>(future, jpuppeteer.cdp.cdp.entity.browser.GetHistogramsResponse.class);
    }

    /**
    * Get a Chrome histogram by name.
    */
    public jpuppeteer.cdp.cdp.entity.browser.GetHistogramResponse getHistogram(jpuppeteer.cdp.cdp.entity.browser.GetHistogramRequest request, int timeout) throws Exception {
        return connection.send("Browser.getHistogram", request, jpuppeteer.cdp.cdp.entity.browser.GetHistogramResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.browser.GetHistogramResponse> asyncGetHistogram(jpuppeteer.cdp.cdp.entity.browser.GetHistogramRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.getHistogram", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.browser.GetHistogramResponse>(future, jpuppeteer.cdp.cdp.entity.browser.GetHistogramResponse.class);
    }

    /**
    * Get position and size of the browser window.
    */
    public jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsResponse getWindowBounds(jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsRequest request, int timeout) throws Exception {
        return connection.send("Browser.getWindowBounds", request, jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsResponse> asyncGetWindowBounds(jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.getWindowBounds", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsResponse>(future, jpuppeteer.cdp.cdp.entity.browser.GetWindowBoundsResponse.class);
    }

    /**
    * Get the browser window that contains the devtools target.
    */
    public jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetResponse getWindowForTarget(jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetRequest request, int timeout) throws Exception {
        return connection.send("Browser.getWindowForTarget", request, jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetResponse> asyncGetWindowForTarget(jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.getWindowForTarget", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetResponse>(future, jpuppeteer.cdp.cdp.entity.browser.GetWindowForTargetResponse.class);
    }

    /**
    * Set position and/or size of the browser window.
    */
    public void setWindowBounds(jpuppeteer.cdp.cdp.entity.browser.SetWindowBoundsRequest request, int timeout) throws Exception {
        connection.send("Browser.setWindowBounds", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetWindowBounds(jpuppeteer.cdp.cdp.entity.browser.SetWindowBoundsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.setWindowBounds", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Set dock tile details, platform-specific.
    */
    public void setDockTile(jpuppeteer.cdp.cdp.entity.browser.SetDockTileRequest request, int timeout) throws Exception {
        connection.send("Browser.setDockTile", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetDockTile(jpuppeteer.cdp.cdp.entity.browser.SetDockTileRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = connection.asyncSend("Browser.setDockTile", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}