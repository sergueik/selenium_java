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

/**
 * Keyframe Style
 */
public class KeyframeStyle {
    private String offset;

    private String easing;

    /**
     * Keyframe's time offset.
     */
    public String getOffset() {
        return offset;
    }

    /**
     * Keyframe's time offset.
     */
    public void setOffset(String offset) {
        this.offset = offset;
    }

    /**
     * <code>AnimationEffect</code>'s timing function.
     */
    public String getEasing() {
        return easing;
    }

    /**
     * <code>AnimationEffect</code>'s timing function.
     */
    public void setEasing(String easing) {
        this.easing = easing;
    }
}
