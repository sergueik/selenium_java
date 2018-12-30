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

import static java.lang.Double.parseDouble;
import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import io.webfolder.cdp.event.Events;
import io.webfolder.cdp.exception.CommandException;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.logger.CdpLogger;

class WSAdapter extends WebSocketAdapter {

    private final Map<String, Events> events = listEvents();

    private final Gson gson;

    private final Map<Integer, WSContext> contextList;

    private final List<EventListener<?>> eventListeners;

    private final Executor executor;

    private final CdpLogger log;

    private Session session;

    private static class TerminateSession implements Runnable {

        private final Session session;

        private final JsonObject object;

        public TerminateSession(final Session session, final JsonObject object) {
            this.session = session;
            this.object = object;
        }

        @Override
        public void run() {
            if (session != null) {
                session.close();
                session.terminate(
                        object.get("params")
                        .getAsJsonObject()
                        .get("reason").getAsString());
            }
        }
    }

    WSAdapter(
            final Gson gson,
            final Map<Integer, WSContext> contextList,
            final List<EventListener<?>> eventListeners,
            final Executor executor,
            final CdpLogger log) {
        this.gson           = gson;
        this.contextList    = contextList;
        this.eventListeners = eventListeners;
        this.executor       = executor;
        this.log            = log; 
    }

    protected Map<String, Events> listEvents() {
        Map<String, Events> map = new HashMap<>();
        for (Events next : Events.values()) {
            map.put(next.domain + "." + next.name, next);
        }
        return unmodifiableMap(map);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void onTextMessage(
                            final WebSocket websocket,
                            final String data) throws Exception {
        executor.execute(() -> {
            log.debug(data);
            JsonElement  json = gson.fromJson(data, JsonElement.class);
            JsonObject object = json.getAsJsonObject();
            JsonElement idElement = object.get("id");
            if (idElement != null) {
                String id = idElement.getAsString();
                if (id != null) {
                    int valId = (int) parseDouble(id);
                    WSContext context = contextList.remove(valId);
                    if (context != null) {
                        JsonObject error = object.getAsJsonObject("error");
                        if (error != null) {
                            int code = (int) error.getAsJsonPrimitive("code").getAsDouble();
                            String message = error.getAsJsonPrimitive("message").getAsString();
                            JsonElement messageData = error.get("data");
                            context.setError(new CommandException(code, message +
                                                        (messageData != null && messageData.isJsonPrimitive() ? ". " +
                                                        messageData.getAsString() : "")));
                        } else {
                            context.setData(json);
                        }
                    }
                }
            } else {
                JsonElement method = object.get("method");
                if (method != null && method.isJsonPrimitive()) {
                    String eventName = method.getAsString();
                    if ("Inspector.detached".equals(eventName) && session != null) {
                        if (session != null) {
                            Thread thread = new Thread(new TerminateSession(session, object));
                            thread.setName("cdp4j-terminate");
                            thread.setDaemon(true);
                            thread.start();
                            session = null;
                        }
                    } else {
                        Events event = events.get(eventName);
                        if (event != null) {
                            JsonElement params = object.get("params");
                            Object value = gson.fromJson(params, event.klass);
                            for (EventListener next : eventListeners) {
                                executor.execute(() -> {
                                    next.onEvent(event, value);
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    void setSession(final Session session) {
        this.session = session;
    }
}
