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

import static io.webfolder.cdp.logger.CdpLoggerType.Console;
import static io.webfolder.cdp.logger.CdpLoggerType.Slf4j;

import java.util.logging.Logger;

public class CdpLoggerFactory {

    private final CdpLoggerType loggerType;

    private final Logger log = Logger.getLogger(CdpLoggerFactory.class.getName());

    private static CdpLogger NULL_LOGGER = new CdpLogger() {

        @Override
        public void info(String message, Object ...args) { }

        @Override
        public void debug(String message, Object ...args) { }

        @Override
        public void error(String message, Object ...args) { }

        @Override
        public void warning(String message, Object ...args) { }

        @Override
        public void error(String message, Throwable t) { }
    };

    public CdpLoggerFactory() {
        this(getDefaultLoggerType());
    }

    public CdpLoggerFactory(final CdpLoggerType loggerType) {
        this.loggerType = loggerType;
    }

    public CdpLogger getLogger(String name) {
        try {
            switch (loggerType) {
                case Slf4j  : return new CdpSlf4jLogger(name);
                case Console: return new CdpConsoleLogger();
                default     : return NULL_LOGGER;
            }
        } catch (Throwable e) {
            log.warning(e.getMessage());
            return NULL_LOGGER;
        }
    }

    public static CdpLoggerType getDefaultLoggerType() {
        CdpLoggerType cdpLoggerType = Console;
        try {
            CdpLoggerFactory.class.getClassLoader().loadClass("org.slf4j.Logger");
            cdpLoggerType = Slf4j;
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return cdpLoggerType;
    }
}
