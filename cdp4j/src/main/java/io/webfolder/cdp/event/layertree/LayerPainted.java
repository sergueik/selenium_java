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
package io.webfolder.cdp.event.layertree;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.EventName;
import io.webfolder.cdp.type.dom.Rect;

@Domain("LayerTree")
@EventName("layerPainted")
public class LayerPainted {
    private String layerId;

    private Rect clip;

    /**
     * The id of the painted layer.
     */
    public String getLayerId() {
        return layerId;
    }

    /**
     * The id of the painted layer.
     */
    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    /**
     * Clip rectangle.
     */
    public Rect getClip() {
        return clip;
    }

    /**
     * Clip rectangle.
     */
    public void setClip(Rect clip) {
        this.clip = clip;
    }
}
