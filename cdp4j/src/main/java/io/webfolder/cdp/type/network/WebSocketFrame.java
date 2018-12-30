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
package io.webfolder.cdp.type.network;

import io.webfolder.cdp.annotation.Experimental;

/**
 * WebSocket frame data
 */
@Experimental
public class WebSocketFrame {
    private Double opcode;

    private Boolean mask;

    private String payloadData;

    /**
     * WebSocket frame opcode.
     */
    public Double getOpcode() {
        return opcode;
    }

    /**
     * WebSocket frame opcode.
     */
    public void setOpcode(Double opcode) {
        this.opcode = opcode;
    }

    /**
     * WebSocke frame mask.
     */
    public Boolean isMask() {
        return mask;
    }

    /**
     * WebSocke frame mask.
     */
    public void setMask(Boolean mask) {
        this.mask = mask;
    }

    /**
     * WebSocke frame payload data.
     */
    public String getPayloadData() {
        return payloadData;
    }

    /**
     * WebSocke frame payload data.
     */
    public void setPayloadData(String payloadData) {
        this.payloadData = payloadData;
    }
}
