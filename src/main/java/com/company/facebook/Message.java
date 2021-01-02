package com.company.facebook;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class Message {
    private String senderId = "";
    private String recipientId = "";
    private String messageText = "";
    private String messageId = "";
    private String time = "";

    public Message(@NotNull final String messageJsonText) {
        JSONObject jsonObject = new JSONObject(messageJsonText);
        messageText = jsonObject
                .getJSONArray("entry")
                .getJSONObject(0)
                .getJSONArray("messaging")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("text");

        JSONArray entry = jsonObject.getJSONArray("entry");
        JSONObject jsonObject1 = entry.getJSONObject(0);
        JSONArray messaging = jsonObject1.getJSONArray("messaging");
        JSONObject jsonObject2 = messaging.getJSONObject(0);

        senderId = jsonObject
                .getJSONArray("entry")
                .getJSONObject(0)
                .getJSONArray("messaging")
                .getJSONObject(0)
                .getJSONObject("sender")
                .getString("id");

        recipientId = jsonObject
                .getJSONArray("entry")
                .getJSONObject(0)
                .getJSONArray("messaging")
                .getJSONObject(0)
                .getJSONObject("recipient")
                .getString("id");

        messageId = jsonObject
                .getJSONArray("entry")
                .getJSONObject(0)
                .getString("id");

        time = Integer.toString(jsonObject
                .getJSONArray("entry")
                .getJSONObject(0)
                .getInt("time"));


    }

    public String getMessageText() {
        return messageText;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getTime() {
        return time;
    }

    public String messageInfo() {
        return "sender_id = " + senderId + "\n" +
                "recipient_id = " + recipientId + "\n" +
                "message_id = " + messageId + "\n" +
                "time = " + time + "\n" +
                "text = " + messageText + "\n";
    }
}
