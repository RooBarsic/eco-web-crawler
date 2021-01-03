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
                "        \"title\":\"Red\",\n" +
                "        \"payload\":\"<POSTBACK_PAYLOAD>\",\n" +
                "        \"image_url\":\"" + "https://www.soyuz.ru/public/uploads/files/2/7439415/2020051814170096742ace98.jpg" + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        sendMessage(jsonResponseText);
    }
}
