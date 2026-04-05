package io.github.ashwithpoojary98.wait;

import io.github.ashwithpoojary98.By;
import io.github.ashwithpoojary98.exception.TimeoutException;
import io.github.ashwithpoojary98.network.NetworkMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Implements automatic waiting for element conditions before interactions.
 *
 * <p>This engine mirrors the Playwright auto-wait philosophy: every interaction
 * waits for the element to reach the required state (present → visible → clickable)
 * before proceeding, eliminating the need for explicit {@code Thread.sleep} calls
 * in test code.
 *
 * <p>The polling loop uses {@link Thread#sleep} so it does not burn CPU while
 * waiting. Interruption is handled correctly: the thread's interrupt flag is restored
 * and a {@link TimeoutException} is thrown.
 */
public class AutoWaitEngine {

    private static final Logger log = LoggerFactory.getLogger(AutoWaitEngine.class);

    private final ElementWaitConditions conditions;
    private final WaitConfig            config;
    private final NetworkMonitor        networkMonitor;

    public AutoWaitEngine(
            ElementWaitConditions conditions,
            WaitConfig config,
            NetworkMonitor networkMonitor) {
        this.conditions    = conditions;
        this.config        = config;
        this.networkMonitor = networkMonitor;
    }

    // ── Public wait methods ───────────────────────────────────────────────────

    /**
     * Waits until the element identified by {@code locator} is present in the DOM.
     *
     * @param locator element locator
     * @throws TimeoutException if the element is not present within the configured timeout
     */
    public void waitForElement(By locator) {
        waitForCondition(
                () -> conditions.isPresent(locator),
                "Element not present in DOM: " + locator);
    }

    /**
     * Waits until the element is present and (if configured) visible.
     *
     * @param locator element locator
     * @throws TimeoutException if the condition is not met within the configured timeout
     */
    public void waitForElementVisible(By locator) {
        waitForElement(locator);
        if (config.isWaitForVisibility()) {
            waitForCondition(
                    () -> conditions.isVisible(locator),
                    "Element not visible: " + locator);
        }
    }

    /**
     * Waits until the element is visible and (if configured) fully clickable
     * — i.e. stable, not obscured, and not disabled.
     *
     * @param locator element locator
     * @throws TimeoutException if the condition is not met within the configured timeout
     */
    public void waitForElementClickable(By locator) {
        waitForElementVisible(locator);
        if (config.isWaitForClickability()) {
            waitForCondition(
                    () -> conditions.isClickable(locator),
                    "Element not clickable: " + locator);
        }
        if (config.isWaitForNetworkIdle()) {
            waitForNetworkIdle();
        }
    }

    /**
     * Waits until the element is visible and editable (not read-only, not disabled).
     *
     * @param locator element locator
     * @throws TimeoutException if the condition is not met within the configured timeout
     */
    public void waitForElementInteractable(By locator) {
        waitForElementVisible(locator);
        if (config.isWaitForClickability()) {
            waitForCondition(
                    () -> conditions.isEditable(locator),
                    "Element not editable: " + locator);
        }
        if (config.isWaitForNetworkIdle()) {
            waitForNetworkIdle();
        }
    }

    /**
     * Waits until the network is considered idle (no more than
     * {@link WaitConfig#getNetworkIdleMaxConnections()} active connections for
     * at least {@link WaitConfig#getNetworkIdleDurationMillis()} ms).
     *
     * @throws TimeoutException if the network does not become idle within the timeout
     */
    public void waitForNetworkIdle() {
        if (networkMonitor == null) {
            return;
        }
        waitForCondition(
                () -> networkMonitor.isNetworkIdle(
                        config.getNetworkIdleMaxConnections(),
                        config.getNetworkIdleDurationMillis()),
                "Network did not become idle");
    }

    // ── Core polling loop ─────────────────────────────────────────────────────

    /**
     * Polls {@code condition} every {@link WaitConfig#getPollingIntervalMillis()} ms
     * until it returns {@code true} or the timeout expires.
     *
     * <p>Uses {@link Thread#sleep} — never a busy-spin — to avoid CPU waste.
     *
     * @param condition      predicate to evaluate
     * @param timeoutMessage message included in the {@link TimeoutException} if timed out
     * @throws TimeoutException if the condition is not met within the configured timeout
     */
    private void waitForCondition(Supplier<Boolean> condition, String timeoutMessage) {
        long deadline = System.currentTimeMillis() + config.getTimeoutMillis();

        while (System.currentTimeMillis() < deadline) {
            try {
                if (Boolean.TRUE.equals(condition.get())) {
                    return;
                }
            } catch (Exception e) {
                // Condition threw — treat as "not yet met" and keep polling.
                log.trace("Condition check threw (will retry): {}", e.getMessage());
            }

            try {
                Thread.sleep(config.getPollingIntervalMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new TimeoutException(
                        "Wait interrupted while polling: " + timeoutMessage);
            }
        }

        throw new TimeoutException(
                timeoutMessage + " (timeout: " + config.getTimeoutMillis() + " ms)");
    }
}
