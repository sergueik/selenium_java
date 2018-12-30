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
package io.webfolder.cdp.test;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;

@SuppressWarnings("rawtypes")
public class CdpAppender extends AppenderBase {

    private List<String> logEntries = new CopyOnWriteArrayList<>();

    private List<String> ignoredConsoleEntries = asList(
        "wait", "close", "waitDocumentReady", "SW registered"
    );

    @Override
    protected void append(Object eventObject) {
        if (eventObject == null) {
            return;
        }
        if ( ! eventObject.getClass().isAssignableFrom(LoggingEvent.class) ) {
            return;
        }
        LoggingEvent event = (LoggingEvent) eventObject;
        String logEntry = event.getFormattedMessage();
        if (logEntry == null || logEntry.isEmpty()) {
            return;
        }
        int end = logEntry.indexOf("(");
        if (end > 0) {
            String methodName = logEntry.substring(0, end);
            if (ignoredConsoleEntries.contains(methodName)) {
                return;
            }
        }
        logEntries.add(logEntry);
    }

    public void clearLogEntries() {
        logEntries.clear();
    }

    public List<String> getLogEntries() {
        return logEntries;
    }
}
