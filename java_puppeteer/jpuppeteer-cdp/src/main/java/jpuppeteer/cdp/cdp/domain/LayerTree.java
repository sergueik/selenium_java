package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class LayerTree {

    private jpuppeteer.cdp.CDPSession session;

    public LayerTree(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Provides the reasons why the given layer was composited.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsResponse compositingReasons(jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsRequest request, int timeout) throws Exception {
        return session.send("LayerTree.compositingReasons", request, jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsResponse> asyncCompositingReasons(jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.compositingReasons", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsResponse>(future, jpuppeteer.cdp.cdp.entity.layertree.CompositingReasonsResponse.class);
    }

    /**
    * Disables compositing tree inspection.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("LayerTree.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables compositing tree inspection.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("LayerTree.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns the snapshot identifier.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotResponse loadSnapshot(jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotRequest request, int timeout) throws Exception {
        return session.send("LayerTree.loadSnapshot", request, jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotResponse> asyncLoadSnapshot(jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.loadSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.layertree.LoadSnapshotResponse.class);
    }

    /**
    * Returns the layer snapshot identifier.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotResponse makeSnapshot(jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotRequest request, int timeout) throws Exception {
        return session.send("LayerTree.makeSnapshot", request, jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotResponse> asyncMakeSnapshot(jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.makeSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.layertree.MakeSnapshotResponse.class);
    }

    /**
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotResponse profileSnapshot(jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotRequest request, int timeout) throws Exception {
        return session.send("LayerTree.profileSnapshot", request, jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotResponse> asyncProfileSnapshot(jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.profileSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.layertree.ProfileSnapshotResponse.class);
    }

    /**
    * Releases layer snapshot captured by the back-end.
    * experimental
    */
    public void releaseSnapshot(jpuppeteer.cdp.cdp.entity.layertree.ReleaseSnapshotRequest request, int timeout) throws Exception {
        session.send("LayerTree.releaseSnapshot", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncReleaseSnapshot(jpuppeteer.cdp.cdp.entity.layertree.ReleaseSnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.releaseSnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Replays the layer snapshot and returns the resulting bitmap.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotResponse replaySnapshot(jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotRequest request, int timeout) throws Exception {
        return session.send("LayerTree.replaySnapshot", request, jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotResponse> asyncReplaySnapshot(jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.replaySnapshot", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotResponse>(future, jpuppeteer.cdp.cdp.entity.layertree.ReplaySnapshotResponse.class);
    }

    /**
    * Replays the layer snapshot and returns canvas log.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogResponse snapshotCommandLog(jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogRequest request, int timeout) throws Exception {
        return session.send("LayerTree.snapshotCommandLog", request, jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogResponse> asyncSnapshotCommandLog(jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("LayerTree.snapshotCommandLog", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogResponse>(future, jpuppeteer.cdp.cdp.entity.layertree.SnapshotCommandLogResponse.class);
    }
}