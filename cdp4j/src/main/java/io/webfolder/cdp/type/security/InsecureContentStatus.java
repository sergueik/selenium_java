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

/**
 * Information about insecure content on the page
 */
public class InsecureContentStatus {
    private Boolean ranMixedContent;

    private Boolean displayedMixedContent;

    private Boolean containedMixedForm;

    private Boolean ranContentWithCertErrors;

    private Boolean displayedContentWithCertErrors;

    private SecurityState ranInsecureContentStyle;

    private SecurityState displayedInsecureContentStyle;

    /**
     * True if the page was loaded over HTTPS and ran mixed (HTTP) content such as scripts.
     */
    public Boolean isRanMixedContent() {
        return ranMixedContent;
    }

    /**
     * True if the page was loaded over HTTPS and ran mixed (HTTP) content such as scripts.
     */
    public void setRanMixedContent(Boolean ranMixedContent) {
        this.ranMixedContent = ranMixedContent;
    }

    /**
     * True if the page was loaded over HTTPS and displayed mixed (HTTP) content such as images.
     */
    public Boolean isDisplayedMixedContent() {
        return displayedMixedContent;
    }

    /**
     * True if the page was loaded over HTTPS and displayed mixed (HTTP) content such as images.
     */
    public void setDisplayedMixedContent(Boolean displayedMixedContent) {
        this.displayedMixedContent = displayedMixedContent;
    }

    /**
     * True if the page was loaded over HTTPS and contained a form targeting an insecure url.
     */
    public Boolean isContainedMixedForm() {
        return containedMixedForm;
    }

    /**
     * True if the page was loaded over HTTPS and contained a form targeting an insecure url.
     */
    public void setContainedMixedForm(Boolean containedMixedForm) {
        this.containedMixedForm = containedMixedForm;
    }

    /**
     * True if the page was loaded over HTTPS without certificate errors, and ran content such as scripts that were loaded with certificate errors.
     */
    public Boolean isRanContentWithCertErrors() {
        return ranContentWithCertErrors;
    }

    /**
     * True if the page was loaded over HTTPS without certificate errors, and ran content such as scripts that were loaded with certificate errors.
     */
    public void setRanContentWithCertErrors(Boolean ranContentWithCertErrors) {
        this.ranContentWithCertErrors = ranContentWithCertErrors;
    }

    /**
     * True if the page was loaded over HTTPS without certificate errors, and displayed content such as images that were loaded with certificate errors.
     */
    public Boolean isDisplayedContentWithCertErrors() {
        return displayedContentWithCertErrors;
    }

    /**
     * True if the page was loaded over HTTPS without certificate errors, and displayed content such as images that were loaded with certificate errors.
     */
    public void setDisplayedContentWithCertErrors(Boolean displayedContentWithCertErrors) {
        this.displayedContentWithCertErrors = displayedContentWithCertErrors;
    }

    /**
     * Security state representing a page that ran insecure content.
     */
    public SecurityState getRanInsecureContentStyle() {
        return ranInsecureContentStyle;
    }

    /**
     * Security state representing a page that ran insecure content.
     */
    public void setRanInsecureContentStyle(SecurityState ranInsecureContentStyle) {
        this.ranInsecureContentStyle = ranInsecureContentStyle;
    }

    /**
     * Security state representing a page that displayed insecure content.
     */
    public SecurityState getDisplayedInsecureContentStyle() {
        return displayedInsecureContentStyle;
    }

    /**
     * Security state representing a page that displayed insecure content.
     */
    public void setDisplayedInsecureContentStyle(SecurityState displayedInsecureContentStyle) {
        this.displayedInsecureContentStyle = displayedInsecureContentStyle;
    }
}
