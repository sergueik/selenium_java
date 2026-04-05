package example.wait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.By;
import example.exception.TimeoutException;
import example.network.NetworkMonitor;

import java.util.function.Supplier;

public class AutoWaitEngine {

	private static final Logger log = LoggerFactory.getLogger(AutoWaitEngine.class);

	private final ElementWaitConditions conditions;
	private final WaitConfig config;
	private final NetworkMonitor networkMonitor;

	public AutoWaitEngine(ElementWaitConditions conditions, WaitConfig config, NetworkMonitor networkMonitor) {
		this.conditions = conditions;
		this.config = config;
		this.networkMonitor = networkMonitor;
	}

	public void waitForElement(By locator) {
		waitForCondition(() -> conditions.isPresent(locator), "Element not present in DOM: " + locator);
	}

	public void waitForElementVisible(By locator) {
		waitForElement(locator);
		if (config.isWaitForVisibility()) {
			waitForCondition(() -> conditions.isVisible(locator), "Element not visible: " + locator);
		}
	}

	public void waitForElementClickable(By locator) {
		waitForElementVisible(locator);
		if (config.isWaitForClickability()) {
			waitForCondition(() -> conditions.isClickable(locator), "Element not clickable: " + locator);
		}
		if (config.isWaitForNetworkIdle()) {
			waitForNetworkIdle();
		}
	}

	public void waitForElementInteractable(By locator) {
		waitForElementVisible(locator);
		if (config.isWaitForClickability()) {
			waitForCondition(() -> conditions.isEditable(locator), "Element not editable: " + locator);
		}
		if (config.isWaitForNetworkIdle()) {
			waitForNetworkIdle();
		}
	}

	public void waitForNetworkIdle() {
		if (networkMonitor == null) {
			return;
		}
		waitForCondition(() -> networkMonitor.isNetworkIdle(config.getNetworkIdleMaxConnections(),
				config.getNetworkIdleDurationMillis()), "Network did not become idle");
	}

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
				throw new TimeoutException("Wait interrupted while polling: " + timeoutMessage);
			}
		}

		throw new TimeoutException(timeoutMessage + " (timeout: " + config.getTimeoutMillis() + " ms)");
	}
}
