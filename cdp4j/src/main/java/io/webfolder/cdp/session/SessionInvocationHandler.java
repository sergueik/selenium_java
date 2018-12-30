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

import static java.lang.String.format;
import static java.util.Base64.getDecoder;
import static java.util.Collections.emptyMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.neovisionaries.ws.client.WebSocket;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.logger.CdpLogger;

class SessionInvocationHandler implements InvocationHandler {

    private final Gson gson;

    private final WebSocket webSocket;

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, WSContext> contextList;

    private final CdpLogger log;

    private final Session session;

    public SessionInvocationHandler(
                    final Gson gson,
                    final WebSocket webSocket,
                    final Map<Integer, WSContext> contextList,
                    final Session session,
                    final CdpLogger log) {
        this.gson        = gson;
        this.webSocket   = webSocket;
        this.contextList = contextList;
        this.session     = session;
        this.log         = log;
    }

    @Override
    public Object invoke(
                final Object proxy,
                final Method method,
                final Object[] args) throws Throwable {

        final Class<?> klass = method.getDeclaringClass();
        final String  domain = klass.getAnnotation(Domain.class).value();
        final String command = method.getName();

        boolean hasArgs = args != null && args.length > 0;

        Map<String, Object> params = hasArgs ?
                                        new HashMap<>(args.length) :
                                        emptyMap();

        if (hasArgs) {
            int argIndex = 0;
            Parameter[] parameters = method.getParameters();
            for (Object argValue : args) {
                String argName = parameters[argIndex++].getName();
                params.put(argName, argValue);
            }
        }

        int id = counter.incrementAndGet();
        Map<String, Object> map = new HashMap<>(3);
        map.put("id"    , id);
        map.put("method", format("%s.%s", domain, command));
        map.put("params", params);

        String json = gson.toJson(map);

        log.debug(json);

        WSContext context = null;

        if (session.isConnected()) {
            context = new WSContext();
            contextList.put(id, context);
            webSocket.sendText(json);
            context.await();
        } else {
            throw new CdpException("WebSocket connection is not alive");
        }

        if ( context.getError() != null ) {
            throw context.getError();
        }

        Class<?> retType = method.getReturnType();

        if (retType.equals(void.class) || retType.equals(Void.class)) {
            return null;
        }

        JsonElement data = context.getData();

        String returns = method.isAnnotationPresent(Returns.class) ?
                    method.getAnnotation(Returns.class).value() : null;

        if (data == null) {
            return null;
        }

        if ( ! data.isJsonObject() ) {
            throw new CdpException("invalid response");
        }

        JsonObject object = data.getAsJsonObject();
        JsonElement result = object.get("result");

        if ( result == null || ! result.isJsonObject() ) {
            throw new CdpException("invalid result");   
        }

        JsonObject resultObject = result.getAsJsonObject();

        Object ret = null;
        Type genericReturnType = method.getGenericReturnType();
        
        if (returns != null) {

            JsonElement jsonElement = resultObject.get(returns);

            if ( jsonElement != null && jsonElement.isJsonPrimitive() ) {
                if (String.class.equals(retType)) {
                    return resultObject.get(returns).getAsString();
                } else if (Boolean.class.equals(retType)) {
                    return resultObject.get(returns).getAsBoolean() ? Boolean.TRUE : Boolean.FALSE;
                } else if (Integer.class.equals(retType)) {
                    return resultObject.get(returns).getAsInt();
                } else if (Double.class.equals(retType)) {
                    return resultObject.get(returns).getAsDouble();
                }
            }

            if (jsonElement != null && byte[].class.equals(genericReturnType)) {
                String encoded = gson.fromJson(jsonElement, String.class);
                if (encoded == null || encoded.trim().isEmpty()) {
                    return null;
                } else {
                    return getDecoder().decode(encoded);
                }
            }

            if (List.class.equals(retType)) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                ret = gson.fromJson(jsonArray, genericReturnType);
            } else {
                ret = gson.fromJson(jsonElement, genericReturnType);
            }
        } else {
            ret = gson.fromJson(resultObject, genericReturnType);
        }

        return ret;
    }

    void dispose() {
        for (WSContext context : contextList.values()) {
            try {
                context.setData(null);
            } catch (Throwable t) {
            }
        }
    }
}
