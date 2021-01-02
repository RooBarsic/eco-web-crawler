package com.company.facebook;

import com.company.api.open.handler.CustomHttpHandlerCommand;
import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class WebHook implements CustomHttpHandlerCommand {
    private int counter = 0;
    private final String VERIFY_TOKEN = "app_2";
    private static final String PAGE_ACCESS_TOKEN = "EAAPIHznf4dgBAOw9R5YdnsPxX0frtpMc9hhGXXLnzZAgXPwWazNTUT3bc5A7EEcBt9iR3ZBRK3eDBnuZBhP7C5enCYUtblGgDLonFrbbv7CApYLlruNqw7X19btDELLvpld6CVEY9RDK7c46MTGP1WozXOa0Dd9UHZA7NYr7vAZDZD";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        counter++;
        System.out.println(" ---- got /hello request    counter = " + counter);
        final StringBuilder responseBuilder = new StringBuilder();
        int responseCode = 405;
        Map<String, String> paramByKey = splitQuery(exchange.getRequestURI().getRawQuery());

        System.out.println(" host = " + exchange.getRequestURI().getHost());
        System.out.println("params ::");
        Scanner scanner = new Scanner(exchange.getRequestBody());
        String jsonText = scanner.nextLine();

        Message message = new Message(jsonText);
        System.out.println(message.messageInfo());
        System.out.println("JsonText = " + jsonText);


        System.out.println("params ::");
        paramByKey.forEach((a, b) -> {
            System.out.println(" key = " + a + " val = " + b);
        });

        if (paramByKey.containsKey("hub.mode") && paramByKey.containsKey("hub.verify_token") && paramByKey.containsKey("hub.challenge")) {
            if (paramByKey.get("hub.mode").equals("subscribe") && paramByKey.get("hub.verify_token").equals(VERIFY_TOKEN)) {
                responseCode = 200;
                responseBuilder.append(paramByKey.get("hub.challenge"));
            }
        }
        else {
            responseBuilder.append("Hello Man");
        }
        if (responseCode == 405) {
            responseBuilder.append("Wrong method usage. Use /help");
        }

        String response = "{\n" +
                "  \"recipient\":{\n" +
                "    \"id\":\"<" + message.getRecipientId() + ">\"\n" +
                "  },\n" +
                "  \"messaging_type\": \"RESPONSE\",\n" +
                "  \"message\":{\n" +
                "    \"text\": \"" + "Salam bro." + "\",\n" +
                "  }\n" +
                "}";

        exchange.getResponseHeaders().set("access_token", PAGE_ACCESS_TOKEN);

        endResponse(exchange, response, responseCode);
    }
}
