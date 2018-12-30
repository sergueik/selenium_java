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
package io.webfolder.cdp.type.serviceworker;

/**
 * ServiceWorker version
 */
public class ServiceWorkerVersion {
    private String versionId;

    private String registrationId;

    private String scriptURL;

    private ServiceWorkerVersionRunningStatus runningStatus;

    private ServiceWorkerVersionStatus status;

    private Double scriptLastModified;

    private Double scriptResponseTime;

    private String targetId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getScriptURL() {
        return scriptURL;
    }

    public void setScriptURL(String scriptURL) {
        this.scriptURL = scriptURL;
    }

    public ServiceWorkerVersionRunningStatus getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(ServiceWorkerVersionRunningStatus runningStatus) {
        this.runningStatus = runningStatus;
    }

    public ServiceWorkerVersionStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceWorkerVersionStatus status) {
        this.status = status;
    }

    /**
     * The Last-Modified header value of the main script.
     */
    public Double getScriptLastModified() {
        return scriptLastModified;
    }

    /**
     * The Last-Modified header value of the main script.
     */
    public void setScriptLastModified(Double scriptLastModified) {
        this.scriptLastModified = scriptLastModified;
    }

    /**
     * The time at which the response headers of the main script were received from the server.  For cached script it is the last time the cache entry was validated.
     */
    public Double getScriptResponseTime() {
        return scriptResponseTime;
    }

    /**
     * The time at which the response headers of the main script were received from the server.  For cached script it is the last time the cache entry was validated.
     */
    public void setScriptResponseTime(Double scriptResponseTime) {
        this.scriptResponseTime = scriptResponseTime;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
