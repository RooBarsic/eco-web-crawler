package com.company.api.open.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface CustomHttpHandlerCommand extends HttpHandler {
    default void endResponse(HttpExchange exchange, String response, int respCode) throws IOException {
        String encoding = "UTF-8";

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);
        exchange.sendResponseHeaders(respCode, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();
    }

    default Map<String, String> splitQuery(String query) {
        Map<String, String> paramByKey = new HashMap<>();

        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }
        String[] keysAndValues = query.split("&");
        for (String s : keysAndValues) {
            String[] kv = s.split("=");
            paramByKey.put(kv[0], kv[1]);
        }
        return paramByKey;
    }
}
