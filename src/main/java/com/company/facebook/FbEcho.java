package com.company.facebook;

import org.jetbrains.annotations.NotNull;

public class FbEcho extends Command {

    public boolean parseCommand(@NotNull final String text) {
        return true;
    }
    public void doCommand(@NotNull final Message message) {
        String jsonResponseText = "{\n" +
                "  \"recipient\":{\n" +
                "    \"id\":\"" + message.getSenderId() + "\"\n" +
                "  },\n" +
                "  \"messaging_type\": \"RESPONSE\",\n" +
                "  \"message\":{\n" +
                "    \"text\": \"" + "Salam bro. You writed : " + message.getMessageText() + "\",\n" +
                "    \"quick_replies\":[\n" +
                "      {\n" +
                "        \"content_type\":\"text\",\n" +
                "        \"title\":\"Red\"\n" +
                "      },{\n" +
                "        \"content_type\":\"text\",\n" +
                "        \"title\":\"Green\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        sendMessage(jsonResponseText);
    }
}
