package com.company.api.open.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HelpCustomHttpHandlerCommand implements CustomHttpHandlerCommand {
    public static final String API_PATH = "/";

    public HelpCustomHttpHandlerCommand() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(" -------- got help request from web");
        final StringBuilder responseBuilder = new StringBuilder();
        int responseCode = 405;

        System.out.println(" --- " + exchange.getRequestMethod());
        if (exchange.getRequestMethod().equals("GET")) {
            responseCode = 200;
            responseBuilder
                    .append("<h3>")
                    .append("use like <br/> /api/search?q=your+request")
                    .append("</h3>")
                    .append("<br/>")
                    .append("<a href=\"")
                    .append("/api/search?q=your+request")
                    .append("\">")
                    .append("/api/search?q=your+request")
                    .append("</a>")
                    .append("<br/>");
        }
        if (responseCode == 405) {
            responseBuilder.append("Wrong method usage. Use /help");
        }

        endResponse(exchange, responseBuilder.toString(), responseCode);
    }

}
