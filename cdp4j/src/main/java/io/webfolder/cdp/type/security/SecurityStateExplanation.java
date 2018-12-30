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
package io.webfolder.cdp.type.security;

import java.util.ArrayList;
import java.util.List;

/**
 * An explanation of an factor contributing to the security state
 */
public class SecurityStateExplanation {
    private SecurityState securityState;

    private String summary;

    private String description;

    private MixedContentType mixedContentType;

    private List<String> certificate = new ArrayList<>();

    /**
     * Security state representing the severity of the factor being explained.
     */
    public SecurityState getSecurityState() {
        return securityState;
    }

    /**
     * Security state representing the severity of the factor being explained.
     */
    public void setSecurityState(SecurityState securityState) {
        this.securityState = securityState;
    }

    /**
     * Short phrase describing the type of factor.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Short phrase describing the type of factor.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Full text explanation of the factor.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Full text explanation of the factor.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The type of mixed content described by the explanation.
     */
    public MixedContentType getMixedContentType() {
        return mixedContentType;
    }

    /**
     * The type of mixed content described by the explanation.
     */
    public void setMixedContentType(MixedContentType mixedContentType) {
        this.mixedContentType = mixedContentType;
    }

    /**
     * Page certificate.
     */
    public List<String> getCertificate() {
        return certificate;
    }

    /**
     * Page certificate.
     */
    public void setCertificate(List<String> certificate) {
        this.certificate = certificate;
    }
}
