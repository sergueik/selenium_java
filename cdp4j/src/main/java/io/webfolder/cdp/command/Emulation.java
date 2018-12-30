/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.command;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.annotation.Optional;
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.type.constant.Platform;
import io.webfolder.cdp.type.dom.RGBA;
import io.webfolder.cdp.type.emulation.ScreenOrientation;
import io.webfolder.cdp.type.emulation.VirtualTimePolicy;
import io.webfolder.cdp.type.page.Viewport;

/**
 * This domain emulates different environments for the page
 */
@Domain("Emulation")
public interface Emulation {
    /**
     * Overrides the values of device screen dimensions (window.screen.width, window.screen.height, window.innerWidth, window.innerHeight, and "device-width"/"device-height"-related CSS media query results).
     * 
     * @param width Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.
     * @param height Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.
     * @param deviceScaleFactor Overriding device scale factor value. 0 disables the override.
     * @param mobile Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text autosizing and more.
     * @param scale Scale to apply to resulting view image.
     * @param screenWidth Overriding screen width value in pixels (minimum 0, maximum 10000000).
     * @param screenHeight Overriding screen height value in pixels (minimum 0, maximum 10000000).
     * @param positionX Overriding view X position on screen in pixels (minimum 0, maximum 10000000).
     * @param positionY Overriding view Y position on screen in pixels (minimum 0, maximum 10000000).
     * @param dontSetVisibleSize Do not set visible view size, rely upon explicit setVisibleSize call.
     * @param screenOrientation Screen orientation override.
     * @param viewport If set, the visible area of the page will be overridden to this viewport. This viewport change is not observed by the page, e.g. viewport-relative elements do not change positions.
     */
    void setDeviceMetricsOverride(Integer width, Integer height, Double deviceScaleFactor,
            Boolean mobile, @Optional Double scale, @Experimental @Optional Integer screenWidth,
            @Experimental @Optional Integer screenHeight, @Experimental @Optional Integer positionX,
            @Experimental @Optional Integer positionY,
            @Experimental @Optional Boolean dontSetVisibleSize,
            @Optional ScreenOrientation screenOrientation,
            @Experimental @Optional Viewport viewport);

    /**
     * Clears the overriden device metrics.
     */
    void clearDeviceMetricsOverride();

    /**
     * Requests that page scale factor is reset to initial values.
     */
    @Experimental
    void resetPageScaleFactor();

    /**
     * Sets a specified page scale factor.
     * 
     * @param pageScaleFactor Page scale factor.
     */
    @Experimental
    void setPageScaleFactor(Double pageScaleFactor);

    /**
     * Resizes the frame/viewport of the page. Note that this does not affect the frame's container (e.g. browser window). Can be used to produce screenshots of the specified size. Not supported on Android.
     * 
     * @param width Frame width (DIP).
     * @param height Frame height (DIP).
     */
    @Experimental
    void setVisibleSize(Integer width, Integer height);

    /**
     * Switches script execution in the page.
     * 
     * @param value Whether script execution should be disabled in the page.
     */
    @Experimental
    void setScriptExecutionDisabled(Boolean value);

    /**
     * Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position unavailable.
     * 
     * @param latitude Mock latitude
     * @param longitude Mock longitude
     * @param accuracy Mock accuracy
     */
    @Experimental
    void setGeolocationOverride(@Optional Double latitude, @Optional Double longitude,
            @Optional Double accuracy);

    /**
     * Clears the overriden Geolocation Position and Error.
     */
    @Experimental
    void clearGeolocationOverride();

    /**
     * Enables touch on platforms which do not support them.
     * 
     * @param enabled Whether the touch event emulation should be enabled.
     * @param maxTouchPoints Maximum touch points supported. Defaults to one.
     */
    void setTouchEmulationEnabled(Boolean enabled, @Optional Integer maxTouchPoints);

    @Experimental
    void setEmitTouchEventsForMouse(Boolean enabled, @Optional Platform configuration);

    /**
     * Emulates the given media for CSS media queries.
     * 
     * @param media Media type to emulate. Empty string disables the override.
     */
    void setEmulatedMedia(String media);

    /**
     * Enables CPU throttling to emulate slow CPUs.
     * 
     * @param rate Throttling rate as a slowdown factor (1 is no throttle, 2 is 2x slowdown, etc).
     */
    @Experimental
    void setCPUThrottlingRate(Double rate);

    /**
     * Tells whether emulation is supported.
     * 
     * @return True if emulation is supported.
     */
    @Experimental
    @Returns("result")
    Boolean canEmulate();

    /**
     * Turns on virtual time for all frames (replacing real-time with a synthetic time source) and sets the current virtual time policy.  Note this supersedes any previous time budget.
     * 
     * @param budget If set, after this many virtual milliseconds have elapsed virtual time will be paused and a virtualTimeBudgetExpired event is sent.
     * @param maxVirtualTimeTaskStarvationCount If set this specifies the maximum number of tasks that can be run before virtual is forced forwards to prevent deadlock.
     */
    @Experimental
    void setVirtualTimePolicy(VirtualTimePolicy policy, @Optional Integer budget,
            @Optional Integer maxVirtualTimeTaskStarvationCount);

    /**
     * Overrides value returned by the javascript navigator object.
     * 
     * @param platform The platform navigator.platform should return.
     */
    @Experimental
    void setNavigatorOverrides(String platform);

    /**
     * Sets or clears an override of the default background color of the frame. This override is used if the content does not specify one.
     * 
     * @param color RGBA of the default background color. If not specified, any existing override will be cleared.
     */
    @Experimental
    void setDefaultBackgroundColorOverride(@Optional RGBA color);

    /**
     * Overrides the values of device screen dimensions (window.screen.width, window.screen.height, window.innerWidth, window.innerHeight, and "device-width"/"device-height"-related CSS media query results).
     * 
     * @param width Overriding width value in pixels (minimum 0, maximum 10000000). 0 disables the override.
     * @param height Overriding height value in pixels (minimum 0, maximum 10000000). 0 disables the override.
     * @param deviceScaleFactor Overriding device scale factor value. 0 disables the override.
     * @param mobile Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text autosizing and more.
     */
    void setDeviceMetricsOverride(Integer width, Integer height, Double deviceScaleFactor,
            Boolean mobile);

    /**
     * Overrides the Geolocation Position or Error. Omitting any of the parameters emulates position unavailable.
     */
    @Experimental
    void setGeolocationOverride();

    /**
     * Enables touch on platforms which do not support them.
     * 
     * @param enabled Whether the touch event emulation should be enabled.
     */
    void setTouchEmulationEnabled(Boolean enabled);

    @Experimental
    void setEmitTouchEventsForMouse(Boolean enabled);

    /**
     * Turns on virtual time for all frames (replacing real-time with a synthetic time source) and sets the current virtual time policy.  Note this supersedes any previous time budget.
     * 
     */
    @Experimental
    void setVirtualTimePolicy(VirtualTimePolicy policy);

    /**
     * Sets or clears an override of the default background color of the frame. This override is used if the content does not specify one.
     */
    @Experimental
    void setDefaultBackgroundColorOverride();
}
