package jpuppeteer.cdp.cdp.domain;

/**
* experimental
*/
public class Animation {

    private jpuppeteer.cdp.CDPSession session;

    public Animation(jpuppeteer.cdp.CDPSession session) {
        this.session = session;
    }

    /**
    * Disables animation domain notifications.
    * experimental
    */
    public void disable(int timeout) throws Exception {
        session.send("Animation.disable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncDisable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.disable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Enables animation domain notifications.
    * experimental
    */
    public void enable(int timeout) throws Exception {
        session.send("Animation.enable", null, timeout);
    }


    public java.util.concurrent.Future<Void> asyncEnable() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.enable");
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Returns the current time of the an animation.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeResponse getCurrentTime(jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeRequest request, int timeout) throws Exception {
        return session.send("Animation.getCurrentTime", request, jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeResponse> asyncGetCurrentTime(jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.getCurrentTime", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeResponse>(future, jpuppeteer.cdp.cdp.entity.animation.GetCurrentTimeResponse.class);
    }

    /**
    * Gets the playback rate of the document timeline.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.animation.GetPlaybackRateResponse getPlaybackRate(int timeout) throws Exception {
        return session.send("Animation.getPlaybackRate", null, jpuppeteer.cdp.cdp.entity.animation.GetPlaybackRateResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.animation.GetPlaybackRateResponse> asyncGetPlaybackRate() {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.getPlaybackRate");
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.animation.GetPlaybackRateResponse>(future, jpuppeteer.cdp.cdp.entity.animation.GetPlaybackRateResponse.class);
    }

    /**
    * Releases a set of animations to no longer be manipulated.
    * experimental
    */
    public void releaseAnimations(jpuppeteer.cdp.cdp.entity.animation.ReleaseAnimationsRequest request, int timeout) throws Exception {
        session.send("Animation.releaseAnimations", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncReleaseAnimations(jpuppeteer.cdp.cdp.entity.animation.ReleaseAnimationsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.releaseAnimations", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Gets the remote object of the Animation.
    * experimental
    */
    public jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationResponse resolveAnimation(jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationRequest request, int timeout) throws Exception {
        return session.send("Animation.resolveAnimation", request, jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationResponse.class, timeout);
    }


    public java.util.concurrent.Future<jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationResponse> asyncResolveAnimation(jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.resolveAnimation", request);
        return new jpuppeteer.cdp.CDPFuture<jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationResponse>(future, jpuppeteer.cdp.cdp.entity.animation.ResolveAnimationResponse.class);
    }

    /**
    * Seek a set of animations to a particular time within each animation.
    * experimental
    */
    public void seekAnimations(jpuppeteer.cdp.cdp.entity.animation.SeekAnimationsRequest request, int timeout) throws Exception {
        session.send("Animation.seekAnimations", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSeekAnimations(jpuppeteer.cdp.cdp.entity.animation.SeekAnimationsRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.seekAnimations", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets the paused state of a set of animations.
    * experimental
    */
    public void setPaused(jpuppeteer.cdp.cdp.entity.animation.SetPausedRequest request, int timeout) throws Exception {
        session.send("Animation.setPaused", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPaused(jpuppeteer.cdp.cdp.entity.animation.SetPausedRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.setPaused", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets the playback rate of the document timeline.
    * experimental
    */
    public void setPlaybackRate(jpuppeteer.cdp.cdp.entity.animation.SetPlaybackRateRequest request, int timeout) throws Exception {
        session.send("Animation.setPlaybackRate", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetPlaybackRate(jpuppeteer.cdp.cdp.entity.animation.SetPlaybackRateRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.setPlaybackRate", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }

    /**
    * Sets the timing of an animation node.
    * experimental
    */
    public void setTiming(jpuppeteer.cdp.cdp.entity.animation.SetTimingRequest request, int timeout) throws Exception {
        session.send("Animation.setTiming", request, timeout);
    }


    public java.util.concurrent.Future<Void> asyncSetTiming(jpuppeteer.cdp.cdp.entity.animation.SetTimingRequest request) {
        java.util.concurrent.Future<com.alibaba.fastjson.JSONObject> future = session.asyncSend("Animation.setTiming", request);
        return new jpuppeteer.cdp.CDPFuture<Void>(future, Void.class);
    }
}