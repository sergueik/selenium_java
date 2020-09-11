package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class DOMSnapshot {

    private jpuppeteer.cdp.CDPSession session;

    public DOMSnapshot(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables DOM snapshot agent for the given page.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("DOMSnapshot.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMSnapshot.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables DOM snapshot agent for the given page.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("DOMSnapshot.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMSnapshot.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns a document snapshot, including the full DOM tree of the root node (including iframes, template contents, and imported documents) in a flattened array, as well as layout and white-listed computed style information for the nodes. Shadow DOM in the returned DOM tree is flattened.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotResponse getSnapshot(jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotRequest request, int timeout) throws Exception {
        return session.send("DOMSnapshot.getSnapshot", request, jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotResponse> asyncGetSnapshot(jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMSnapshot.getSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.domsnapshot.GetSnapshotResponse.class);
    }

    /**
    * Returns a document snapshot, including the full DOM tree of the root node (including iframes, template contents, and imported documents) in a flattened array, as well as layout and white-listed computed style information for the nodes. Shadow DOM in the returned DOM tree is flattened.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotResponse captureSnapshot(jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotRequest request, int timeout) throws Exception {
        return session.send("DOMSnapshot.captureSnapshot", request, jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotResponse> asyncCaptureSnapshot(jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("DOMSnapshot.captureSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.domsnapshot.CaptureSnapshotResponse.class);
    }
}