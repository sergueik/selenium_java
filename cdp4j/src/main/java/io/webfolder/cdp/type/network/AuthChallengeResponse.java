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
import io.webfolder.cdp.type.constant.AuthResponse;

/**
 * Response to an AuthChallenge
 */
@Experimental
public class AuthChallengeResponse {
    private AuthResponse response;

    private String username;

    private String password;

    /**
     * The decision on what to do in response to the authorization challenge.  Default means deferring to the default behavior of the net stack, which will likely either the Cancel authentication or display a popup dialog box.
     */
    public AuthResponse getResponse() {
        return response;
    }

    /**
     * The decision on what to do in response to the authorization challenge.  Default means deferring to the default behavior of the net stack, which will likely either the Cancel authentication or display a popup dialog box.
     */
    public void setResponse(AuthResponse response) {
        this.response = response;
    }

    /**
     * The username to provide, possibly empty. Should only be set if response is ProvideCredentials.
     */
    public String getUsername() {
        return username;
    }

    /**
     * The username to provide, possibly empty. Should only be set if response is ProvideCredentials.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The password to provide, possibly empty. Should only be set if response is ProvideCredentials.
     */
    public String getPassword() {
        return password;
    }

    /**
     * The password to provide, possibly empty. Should only be set if response is ProvideCredentials.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
