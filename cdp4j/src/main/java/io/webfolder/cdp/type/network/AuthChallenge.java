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
import io.webfolder.cdp.type.constant.AuthChallengeSource;

/**
 * Authorization challenge for HTTP status code 401 or 407
 */
@Experimental
public class AuthChallenge {
    private AuthChallengeSource source;

    private String origin;

    private String scheme;

    private String realm;

    /**
     * Source of the authentication challenge.
     */
    public AuthChallengeSource getSource() {
        return source;
    }

    /**
     * Source of the authentication challenge.
     */
    public void setSource(AuthChallengeSource source) {
        this.source = source;
    }

    /**
     * Origin of the challenger.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Origin of the challenger.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * The authentication scheme used, such as basic or digest
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * The authentication scheme used, such as basic or digest
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * The realm of the challenge. May be empty.
     */
    public String getRealm() {
        return realm;
    }

    /**
     * The realm of the challenge. May be empty.
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }
}
