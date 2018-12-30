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
package io.webfolder.cdp.session;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.CountDownLatch;

import com.google.gson.JsonElement;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.exception.CommandException;

class WSContext {

    private CountDownLatch latch = new CountDownLatch(1);

    private JsonElement data;

    private CommandException error;

    private static final int WS_TIMEOUT = 10; // 10 seconds

    void await() {
        try {
            latch.await(WS_TIMEOUT, SECONDS);
        } catch (InterruptedException e) {
            throw new CdpException(e);
        }
    }

    void setData(final JsonElement data) {
        this.data = data;
        latch.countDown();
    }

    JsonElement getData() {
        return data;
    }

    void setError(CommandException error) {
        this.error = error;
        latch.countDown();
    }

    CommandException getError() {
        return error;
    }
}
