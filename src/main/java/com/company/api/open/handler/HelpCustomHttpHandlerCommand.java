package com.company.api.open.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HelpCustomHttpHandlerCommand implements CustomHttpHandlerCommand {
    public static final String API_PATH = "/";
    public static String DEFAULT_RESPONSE;

    public HelpCustomHttpHandlerCommand() {
        DEFAULT_RESPONSE =
                "<h3>"
                + "use like /api/search?q=your+request"
                + "</h3>"
                + "<br/>"
                + "<a href=\""
                + "/api/search?q=your+request"
                + "\">"
                + "/api/search?q=your+request"
                + "</a>"
                + "<br/>";

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(" -------- got help request from web");
        final StringBuilder responseBuilder = new StringBuilder();
        int responseCode = 405;

        System.out.println(" --- " + exchange.getRequestMethod());
        if (exchange.getRequestMethod().equals("GET")) {
            responseCode = 200;
            responseBuilder.append(DEFAULT_RESPONSE);
        }
        if (responseCode == 405) {
            responseBuilder.append("Wrong method usage. Use /help");
        }

        endResponse(exchange, responseBuilder.toString(), responseCode);
    }

}
