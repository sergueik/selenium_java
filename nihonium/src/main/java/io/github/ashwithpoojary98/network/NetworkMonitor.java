package io.github.ashwithpoojary98.network;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.cdp.domain.NetworkDomain;
import io.github.ashwithpoojary98.wait.WaitConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class NetworkMonitor {

    private final NetworkDomain networkDomain;
    private final ConcurrentHashMap<String, Long> activeRequests;
    private final AtomicLong lastActivityTime;
    private volatile boolean enabled = false;

    public NetworkMonitor(NetworkDomain networkDomain) {
        this.networkDomain = networkDomain;
        this.activeRequests = new ConcurrentHashMap<>();
        this.lastActivityTime = new AtomicLong(System.currentTimeMillis());
    }

    public void enable() {
        if (!enabled) {
            networkDomain.enable().join();

            networkDomain.subscribeToRequestWillBeSent(this::onRequestStarted);
            networkDomain.subscribeToLoadingFinished(this::onRequestFinished);
            networkDomain.subscribeToLoadingFailed(this::onRequestFailed);

            enabled = true;
        }
    }

    public void disable() {
        if (enabled) {
            networkDomain.disable().join();
            activeRequests.clear();
            enabled = false;
        }
    }

    private void onRequestStarted(JsonObject event) {
        String requestId = event.get("requestId").getAsString();
        activeRequests.put(requestId, System.currentTimeMillis());
        lastActivityTime.set(System.currentTimeMillis());
    }

    private void onRequestFinished(JsonObject event) {
        String requestId = event.get("requestId").getAsString();
        activeRequests.remove(requestId);
        lastActivityTime.set(System.currentTimeMillis());
    }

    private void onRequestFailed(JsonObject event) {
        String requestId = event.get("requestId").getAsString();
        activeRequests.remove(requestId);
        lastActivityTime.set(System.currentTimeMillis());
    }

    public boolean isNetworkIdle(int maxConnections, long idleDurationMillis) {
        if (!enabled) {
            return true;
        }

        int activeCount = activeRequests.size();
        if (activeCount > maxConnections) {
            return false;
        }

        long timeSinceLastActivity = System.currentTimeMillis() - lastActivityTime.get();
        return timeSinceLastActivity >= idleDurationMillis;
    }

    public boolean isNetworkIdle() {
        return isNetworkIdle(
                WaitConfig.DEFAULT_NETWORK_IDLE_MAX_CONNECTIONS,
                WaitConfig.DEFAULT_NETWORK_IDLE_DURATION_MILLIS);
    }

    public int getActiveRequestCount() {
        return activeRequests.size();
    }
}
