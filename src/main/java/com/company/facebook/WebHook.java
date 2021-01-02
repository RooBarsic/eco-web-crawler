package com.company.facebook;

import com.company.api.open.handler.CustomHttpHandlerCommand;
import com.sun.net.httpserver.HttpExchange;
import org.apache.http.client.ResponseHandler;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
        Scanner scanner = new Scanner(exchange.getRequestBody());
        String dataJson = scanner.nextLine();
        System.out.println("data : " + dataJson);
        String jsonText = dataJson; //"{\"object\":\"page\",\"entry\":[{\"id\":\"103106818399407\",\"time\":1609604901020,\"messaging\":[{\"sender\":{\"id\":\"4080977395248736\"},\"recipient\":{\"id\":\"103106818399407\"},\"timestamp\":1609604889627,\"message\":{\"mid\":\"m_ToXAQ1xk80K3qQSS2GT1IIYG6QdfzHmKjpsV-VXrfhKiBN_T8AMe1bpqzssrbv1pAPFz-zqft72D5L3RXvIceQ\",\"text\":\"Sabina\"}}]}]}"; //scanner.nextLine();

        Message message = new Message(jsonText);
        System.out.println("params ::");
        System.out.println(message.messageInfo());
        System.out.println("JsonText = " + jsonText);


        System.out.println("MAP params ::");
        paramByKey.forEach((a, b) -> {
            System.out.println(" key = " + a + " val = " + b);
        });
        System.out.println("-----------------");

        responseCode = 200;
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


        String curlReques = "curl -X POST -H \"Content-Type: application/json\" -d '{\n" +
                "  \"recipient\":{\n" +
                "    \"id\":\"" + message.getSenderId() + "\"\n" +
                "  },\n" +
                "  \"messaging_type\": \"RESPONSE\",\n" +
                "  \"message\":{\n" +
                "    \"text\": \"Pick a color:\",\n" +
                "    \"quick_replies\":[\n" +
                "      {\n" +
                "        \"content_type\":\"text\",\n" +
                "        \"title\":\"Red\",\n" +
                "        \"payload\":\"<POSTBACK_PAYLOAD>\",\n" +
                "        \"image_url\":\"http://example.com/img/red.png\"\n" +
                "      },{\n" +
                "        \"content_type\":\"text\",\n" +
                "        \"title\":\"Green\",\n" +
                "        \"payload\":\"<POSTBACK_PAYLOAD>\",\n" +
                "        \"image_url\":\"http://example.com/img/green.png\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}' \"https://graph.facebook.com/v9.0/me/messages?access_token=" + PAGE_ACCESS_TOKEN + "\"";

        //sendMessage(curlReques);
        System.out.println("  sended responce ");

        String response = "{\n" +
                "  \"recipient\":{\n" +
                "    \"id\":\"<" + message.getRecipientId() + ">\"\n" +
                "  },\n" +
                "  \"messaging_type\": \"RESPONSE\",\n" +
                "  \"message\":{\n" +
                "    \"text\": \"" + "Salam bro." + "\",\n" +
                "  }\n" +
                "}";

        endResponse(exchange, response, responseCode);
    }

    public void sendMessage(String message) {
        String jsonStr = "{\"recipient\":{\"id\":\"4080977395248736\"},\"messaging_type\": \"RESPONSE\",\"message\":{\"text\": \"Pick a color:\",\"quick_replies\":[{\"content_type\":\"text\",\"title\":\"Red\",\"payload\":\"<POSTBACK_PAYLOAD>\",\"image_url\":\"http://example.com/img/red.png\"},{\"content_type\":\"text\",\"title\":\"Green\",\"payload\":\"<POSTBACK_PAYLOAD>\",\"image_url\":\"http://example.com/img/green.png\"}]}}";

        try {
            way3();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        way4();

    }

    public static String getParamsString(final Map<String, String> params) {
        final StringBuilder result = new StringBuilder();

        params.forEach((name, value) -> {
            try {
                result.append(URLEncoder.encode(name, "UTF-8"));
                result.append('=');
                result.append(URLEncoder.encode(value, "UTF-8"));
                result.append('&');
            } catch (final UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        final String resultString = result.toString();
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public void way3() throws IOException {
        System.out.println("\n-----------  way-3  -------------------\n");
        String url = "https://fb-bot-java.herokuapp.com/hello";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("name", "fara");

        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }

    public void way4() {
        System.out.println("\n-----------  way-4  -------------------\n");
        String url = "https://fb-bot-java.herokuapp.com/hello";
        String urlParameters = "data={\"snippet\":{\"channelId\":\"UCLGnG6SffG60QKqw-PuCjng\",\"videoId\":\"dTUPnrAZESU\",\"topLevelComment\":{\"snippet\":{\"textOriginal\":\"Test comment\"}}}}";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con;

        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer ");
            con.setRequestProperty("Accept", "application/json'");
            con.setRequestProperty("Content-Type", "application/json'");
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            StringBuilder content;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println(content.toString());
            con.disconnect();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}

