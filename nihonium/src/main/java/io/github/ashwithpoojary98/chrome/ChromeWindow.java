package io.github.ashwithpoojary98.chrome;

import com.google.gson.JsonObject;
import io.github.ashwithpoojary98.Dimension;
import io.github.ashwithpoojary98.Point;
import io.github.ashwithpoojary98.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDP-backed implementation of {@link WebDriver.Window}.
 *
 * <p>All operations delegate to {@link io.github.ashwithpoojary98.cdp.domain.BrowserDomain}
 * using the current page's CDP target ID, so the correct browser window is always targeted.
 */
class ChromeWindow implements WebDriver.Window {

    private static final Logger log = LoggerFactory.getLogger(ChromeWindow.class);

    // ── CDP bounds field names ────────────────────────────────────────────────

    private static final String FIELD_WIDTH  = "width";
    private static final String FIELD_HEIGHT = "height";
    private static final String FIELD_LEFT   = "left";
    private static final String FIELD_TOP    = "top";

    // ─────────────────────────────────────────────────────────────────────────

    private final ChromeDriver driver;

    ChromeWindow(ChromeDriver driver) {
        this.driver = driver;
    }

    // ── Size ──────────────────────────────────────────────────────────────────

    /**
     * Returns the current inner size of the browser window.
     *
     * @return window size, or {@link Dimension#ZERO} if it cannot be determined
     */
    @Override
    public Dimension getSize() {
        try {
            JsonObject bounds = driver.getBrowserDomain()
                    .getWindowBounds(driver.getCurrentTargetId())
                    .join();
            return new Dimension(
                    bounds.get(FIELD_WIDTH).getAsInt(),
                    bounds.get(FIELD_HEIGHT).getAsInt());
        } catch (Exception e) {
            log.warn("Failed to get window size, returning zero: {}", e.getMessage());
            return new Dimension(0, 0);
        }
    }

    /**
     * Resizes the browser window.
     *
     * @param targetSize the desired window size; must not be {@code null}
     */
    @Override
    public void setSize(Dimension targetSize) {
        try {
            driver.getBrowserDomain()
                    .setWindowSize(driver.getCurrentTargetId(),
                            targetSize.getWidth(), targetSize.getHeight())
                    .join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to set window size to " + targetSize, e);
        }
    }

    // ── Position ──────────────────────────────────────────────────────────────

    /**
     * Returns the current top-left position of the browser window on screen.
     *
     * @return window position, or {@link Point#ORIGIN} if it cannot be determined
     */
    @Override
    public Point getPosition() {
        try {
            JsonObject bounds = driver.getBrowserDomain()
                    .getWindowBounds(driver.getCurrentTargetId())
                    .join();
            return new Point(
                    bounds.get(FIELD_LEFT).getAsInt(),
                    bounds.get(FIELD_TOP).getAsInt());
        } catch (Exception e) {
            log.warn("Failed to get window position, returning origin: {}", e.getMessage());
            return new Point(0, 0);
        }
    }

    /**
     * Moves the browser window to the given screen position.
     *
     * @param targetPosition the desired top-left position; must not be {@code null}
     */
    @Override
    public void setPosition(Point targetPosition) {
        try {
            driver.getBrowserDomain()
                    .setWindowPosition(driver.getCurrentTargetId(),
                            targetPosition.getX(), targetPosition.getY())
                    .join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to set window position to " + targetPosition, e);
        }
    }

    // ── Window state ──────────────────────────────────────────────────────────

    /** Maximises the browser window. */
    @Override
    public void maximize() {
        try {
            driver.getBrowserDomain()
                    .maximizeWindow(driver.getCurrentTargetId())
                    .join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to maximise window", e);
        }
    }

    /** Minimises the browser window. */
    @Override
    public void minimize() {
        try {
            driver.getBrowserDomain()
                    .minimizeWindow(driver.getCurrentTargetId())
                    .join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to minimise window", e);
        }
    }

    /** Puts the browser window into fullscreen mode. */
    @Override
    public void fullscreen() {
        try {
            driver.getBrowserDomain()
                    .fullscreenWindow(driver.getCurrentTargetId())
                    .join();
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter fullscreen", e);
        }
    }
}
