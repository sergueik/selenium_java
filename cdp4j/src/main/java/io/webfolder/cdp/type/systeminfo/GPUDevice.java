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
package io.webfolder.cdp.type.systeminfo;

/**
 * Describes a single graphics processor (GPU)
 */
public class GPUDevice {
    private Double vendorId;

    private Double deviceId;

    private String vendorString;

    private String deviceString;

    /**
     * PCI ID of the GPU vendor, if available; 0 otherwise.
     */
    public Double getVendorId() {
        return vendorId;
    }

    /**
     * PCI ID of the GPU vendor, if available; 0 otherwise.
     */
    public void setVendorId(Double vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * PCI ID of the GPU device, if available; 0 otherwise.
     */
    public Double getDeviceId() {
        return deviceId;
    }

    /**
     * PCI ID of the GPU device, if available; 0 otherwise.
     */
    public void setDeviceId(Double deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * String description of the GPU vendor, if the PCI ID is not available.
     */
    public String getVendorString() {
        return vendorString;
    }

    /**
     * String description of the GPU vendor, if the PCI ID is not available.
     */
    public void setVendorString(String vendorString) {
        this.vendorString = vendorString;
    }

    /**
     * String description of the GPU device, if the PCI ID is not available.
     */
    public String getDeviceString() {
        return deviceString;
    }

    /**
     * String description of the GPU device, if the PCI ID is not available.
     */
    public void setDeviceString(String deviceString) {
        this.deviceString = deviceString;
    }
}
