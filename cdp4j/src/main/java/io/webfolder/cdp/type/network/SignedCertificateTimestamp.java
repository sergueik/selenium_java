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

/**
 * Details of a signed certificate timestamp (SCT)
 */
public class SignedCertificateTimestamp {
    private String status;

    private String origin;

    private String logDescription;

    private String logId;

    private Double timestamp;

    private String hashAlgorithm;

    private String signatureAlgorithm;

    private String signatureData;

    /**
     * Validation status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Validation status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Origin.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Origin.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Log name / description.
     */
    public String getLogDescription() {
        return logDescription;
    }

    /**
     * Log name / description.
     */
    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    /**
     * Log ID.
     */
    public String getLogId() {
        return logId;
    }

    /**
     * Log ID.
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }

    /**
     * Issuance date.
     */
    public Double getTimestamp() {
        return timestamp;
    }

    /**
     * Issuance date.
     */
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Hash algorithm.
     */
    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    /**
     * Hash algorithm.
     */
    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    /**
     * Signature algorithm.
     */
    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    /**
     * Signature algorithm.
     */
    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    /**
     * Signature data.
     */
    public String getSignatureData() {
        return signatureData;
    }

    /**
     * Signature data.
     */
    public void setSignatureData(String signatureData) {
        this.signatureData = signatureData;
    }
}
