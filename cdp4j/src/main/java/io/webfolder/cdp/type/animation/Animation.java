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
package io.webfolder.cdp.type.animation;

import io.webfolder.cdp.annotation.Experimental;
import io.webfolder.cdp.type.constant.AnimationType;

/**
 * Animation instance
 */
@Experimental
public class Animation {
    private String id;

    private String name;

    private Boolean pausedState;

    private String playState;

    private Double playbackRate;

    private Double startTime;

    private Double currentTime;

    private AnimationType type;

    private AnimationEffect source;

    private String cssId;

    /**
     * <code>Animation</code>'s id.
     */
    public String getId() {
        return id;
    }

    /**
     * <code>Animation</code>'s id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <code>Animation</code>'s name.
     */
    public String getName() {
        return name;
    }

    /**
     * <code>Animation</code>'s name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <code>Animation</code>'s internal paused state.
     */
    public Boolean isPausedState() {
        return pausedState;
    }

    /**
     * <code>Animation</code>'s internal paused state.
     */
    public void setPausedState(Boolean pausedState) {
        this.pausedState = pausedState;
    }

    /**
     * <code>Animation</code>'s play state.
     */
    public String getPlayState() {
        return playState;
    }

    /**
     * <code>Animation</code>'s play state.
     */
    public void setPlayState(String playState) {
        this.playState = playState;
    }

    /**
     * <code>Animation</code>'s playback rate.
     */
    public Double getPlaybackRate() {
        return playbackRate;
    }

    /**
     * <code>Animation</code>'s playback rate.
     */
    public void setPlaybackRate(Double playbackRate) {
        this.playbackRate = playbackRate;
    }

    /**
     * <code>Animation</code>'s start time.
     */
    public Double getStartTime() {
        return startTime;
    }

    /**
     * <code>Animation</code>'s start time.
     */
    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    /**
     * <code>Animation</code>'s current time.
     */
    public Double getCurrentTime() {
        return currentTime;
    }

    /**
     * <code>Animation</code>'s current time.
     */
    public void setCurrentTime(Double currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Animation type of <code>Animation</code>.
     */
    public AnimationType getType() {
        return type;
    }

    /**
     * Animation type of <code>Animation</code>.
     */
    public void setType(AnimationType type) {
        this.type = type;
    }

    /**
     * <code>Animation</code>'s source animation node.
     */
    public AnimationEffect getSource() {
        return source;
    }

    /**
     * <code>Animation</code>'s source animation node.
     */
    public void setSource(AnimationEffect source) {
        this.source = source;
    }

    /**
     * A unique ID for <code>Animation</code> representing the sources that triggered this CSS animation/transition.
     */
    public String getCssId() {
        return cssId;
    }

    /**
     * A unique ID for <code>Animation</code> representing the sources that triggered this CSS animation/transition.
     */
    public void setCssId(String cssId) {
        this.cssId = cssId;
    }
}
