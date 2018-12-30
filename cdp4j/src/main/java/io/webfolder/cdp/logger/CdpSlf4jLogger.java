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
package io.webfolder.cdp.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CdpSlf4jLogger implements CdpLogger {

    private final Logger log;

    public CdpSlf4jLogger(final String name) {
        log = LoggerFactory.getLogger(name);
    }

    @Override
    public void info(final String message, final Object ...args) {
        if (log.isInfoEnabled()) {
            log.info(message, args);
        }
    }

    @Override
    public void debug(final String message, final Object ...args) {
        if (log.isDebugEnabled()) {
            log.debug(message, args);
        }
    }

    @Override
    public void error(final String message, final Object ...args) {
        if (log.isErrorEnabled()) {
            log.error(message, args);
        }
    }

    @Override
    public void warning(final String message, final Object ...args) {
        if (log.isWarnEnabled()) {
            log.warn(message, args);
        }
    }

    @Override
    public void error(String message, Throwable t) {
        if (log.isErrorEnabled()) {
            log.error(message, t);
        }
    }
}
