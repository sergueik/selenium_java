package io.github.ashwithpoojary98.wait;

/**
 * Immutable configuration for the auto-wait engine.
 *
 * <p>All time values are in milliseconds. Use the {@link Builder} to create instances.
 *
 * <pre>{@code
 * WaitConfig config = WaitConfig.builder()
 *     .timeout(15_000)
 *     .pollingInterval(200)
 *     .waitForNetworkIdle(true)
 *     .build();
 * }</pre>
 */
public final class WaitConfig {

    // ── Default values ────────────────────────────────────────────────────────

    /** Default maximum time to wait for a condition to become true. */
    public static final long DEFAULT_TIMEOUT_MILLIS            = 10_000L;

    /** Default interval between condition re-checks. */
    public static final long DEFAULT_POLLING_INTERVAL_MILLIS  = 100L;

    /** Default maximum number of in-flight connections for network-idle detection (0 = none). */
    public static final int  DEFAULT_NETWORK_IDLE_MAX_CONNECTIONS  = 0;

    /** Default consecutive idle duration before network is considered idle. */
    public static final long DEFAULT_NETWORK_IDLE_DURATION_MILLIS  = 500L;

    // ── Fields ────────────────────────────────────────────────────────────────

    private final long    timeoutMillis;
    private final long    pollingIntervalMillis;
    private final boolean waitForVisibility;
    private final boolean waitForClickability;
    private final boolean waitForNetworkIdle;
    private final boolean waitForAnimations;
    private final int     networkIdleMaxConnections;
    private final long    networkIdleDurationMillis;

    private WaitConfig(Builder builder) {
        this.timeoutMillis            = builder.timeoutMillis;
        this.pollingIntervalMillis    = builder.pollingIntervalMillis;
        this.waitForVisibility        = builder.waitForVisibility;
        this.waitForClickability      = builder.waitForClickability;
        this.waitForNetworkIdle       = builder.waitForNetworkIdle;
        this.waitForAnimations        = builder.waitForAnimations;
        this.networkIdleMaxConnections = builder.networkIdleMaxConnections;
        this.networkIdleDurationMillis = builder.networkIdleDurationMillis;
    }

    // ── Factory methods ───────────────────────────────────────────────────────

    public static Builder builder() {
        return new Builder();
    }

    /** Returns a {@link WaitConfig} with all defaults applied. */
    public static WaitConfig defaultConfig() {
        return builder().build();
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public long    getTimeoutMillis()             { return timeoutMillis; }
    public long    getPollingIntervalMillis()      { return pollingIntervalMillis; }
    public boolean isWaitForVisibility()          { return waitForVisibility; }
    public boolean isWaitForClickability()        { return waitForClickability; }
    public boolean isWaitForNetworkIdle()         { return waitForNetworkIdle; }
    public boolean isWaitForAnimations()          { return waitForAnimations; }
    public int     getNetworkIdleMaxConnections() { return networkIdleMaxConnections; }
    public long    getNetworkIdleDurationMillis() { return networkIdleDurationMillis; }

    // ── Builder ───────────────────────────────────────────────────────────────

    public static final class Builder {

        private long    timeoutMillis             = DEFAULT_TIMEOUT_MILLIS;
        private long    pollingIntervalMillis     = DEFAULT_POLLING_INTERVAL_MILLIS;
        private boolean waitForVisibility         = true;
        private boolean waitForClickability       = true;
        private boolean waitForNetworkIdle        = false;
        private boolean waitForAnimations         = false;
        private int     networkIdleMaxConnections = DEFAULT_NETWORK_IDLE_MAX_CONNECTIONS;
        private long    networkIdleDurationMillis = DEFAULT_NETWORK_IDLE_DURATION_MILLIS;

        /** Maximum time (ms) to wait before a {@link io.github.ashwithpoojary98.exception.TimeoutException} is thrown. */
        public Builder timeout(long millis) {
            this.timeoutMillis = millis;
            return this;
        }

        /** Interval (ms) between successive condition checks. */
        public Builder pollingInterval(long millis) {
            this.pollingIntervalMillis = millis;
            return this;
        }

        /** Whether to wait for the element to be visible before interacting. */
        public Builder waitForVisibility(boolean wait) {
            this.waitForVisibility = wait;
            return this;
        }

        /** Whether to wait for the element to be clickable before clicking. */
        public Builder waitForClickability(boolean wait) {
            this.waitForClickability = wait;
            return this;
        }

        /** Whether to wait for network idle before considering an interaction complete. */
        public Builder waitForNetworkIdle(boolean wait) {
            this.waitForNetworkIdle = wait;
            return this;
        }

        /** Whether to wait for CSS animations to finish. */
        public Builder waitForAnimations(boolean wait) {
            this.waitForAnimations = wait;
            return this;
        }

        /** Maximum simultaneous connections that still count as "network idle". */
        public Builder networkIdleMaxConnections(int maxConnections) {
            this.networkIdleMaxConnections = maxConnections;
            return this;
        }

        /** Duration (ms) of inactivity required before the network is declared idle. */
        public Builder networkIdleDuration(long millis) {
            this.networkIdleDurationMillis = millis;
            return this;
        }

        public WaitConfig build() {
            return new WaitConfig(this);
        }
    }
}
