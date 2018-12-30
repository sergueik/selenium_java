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
 * Cookie object
 */
@Experimental
public class Cookie {
    private String name;

    private String value;

    private String domain;

    private String path;

    private Double expires;

    private Integer size;

    private Boolean httpOnly;

    private Boolean secure;

    private Boolean session;

    private CookieSameSite sameSite;

    /**
     * Cookie name.
     */
    public String getName() {
        return name;
    }

    /**
     * Cookie name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Cookie value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Cookie value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Cookie domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Cookie domain.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Cookie path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Cookie path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Cookie expiration date as the number of seconds since the UNIX epoch.
     */
    public Double getExpires() {
        return expires;
    }

    /**
     * Cookie expiration date as the number of seconds since the UNIX epoch.
     */
    public void setExpires(Double expires) {
        this.expires = expires;
    }

    /**
     * Cookie size.
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Cookie size.
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * True if cookie is http-only.
     */
    public Boolean isHttpOnly() {
        return httpOnly;
    }

    /**
     * True if cookie is http-only.
     */
    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    /**
     * True if cookie is secure.
     */
    public Boolean isSecure() {
        return secure;
    }

    /**
     * True if cookie is secure.
     */
    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    /**
     * True in case of session cookie.
     */
    public Boolean isSession() {
        return session;
    }

    /**
     * True in case of session cookie.
     */
    public void setSession(Boolean session) {
        this.session = session;
    }

    /**
     * Cookie SameSite type.
     */
    public CookieSameSite getSameSite() {
        return sameSite;
    }

    /**
     * Cookie SameSite type.
     */
    public void setSameSite(CookieSameSite sameSite) {
        this.sameSite = sameSite;
    }
}
