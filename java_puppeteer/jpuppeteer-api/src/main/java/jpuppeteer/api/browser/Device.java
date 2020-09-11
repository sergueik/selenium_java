package jpuppeteer.api.browser;

import lombok.Builder;

@Builder
public class Device {

    private final int width;

    private final int height;

    private double deviceScaleFactor;

    private final boolean isMobile;

    private final boolean hasTouch;

    private final boolean isLandscape;

    public Device(int width, int height, double deviceScaleFactor, boolean isMobile, boolean hasTouch, boolean isLandscape) {
        this.width = width;
        this.height = height;
        this.deviceScaleFactor = deviceScaleFactor;
        this.isMobile = isMobile;
        this.hasTouch = hasTouch;
        this.isLandscape = isLandscape;
    }

    public Device(int width, int height) {
        this(width, height, 1.0, false, false, false);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getDeviceScaleFactor() {
        return deviceScaleFactor;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public boolean isHasTouch() {
        return hasTouch;
    }

    public boolean isLandscape() {
        return isLandscape;
    }

}
