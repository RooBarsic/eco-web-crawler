package com.company.facebook;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public abstract class Command {
    private static final String PAGE_ACCESS_TOKEN = "EAAPIHznf4dgBAOw9R5YdnsPxX0frtpMc9hhGXXLnzZAgXPwWazNTUT3bc5A7EEcBt9iR3ZBRK3eDBnuZBhP7C5enCYUtblGgDLonFrbbv7CApYLlruNqw7X19btDELLvpld6CVEY9RDK7c46MTGP1WozXOa0Dd9UHZA7NYr7vAZDZD";

    public boolean sendMessage(@NotNull final String messageJson) {
        System.out.println("\n-----------  Sending response message  -------------------\n");
        String url = "https://graph.facebook.com/v9.0/me/messages?access_token=" + PAGE_ACCESS_TOKEN;

        HttpURLConnection con;

        try {
            URL myUrl = new URL(url);
            con = (HttpURLConnection) myUrl.openConnection();

            con.setRequestMethod("POST");
            //con.setRequestProperty("Authorization", "Bearer ");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = messageJson.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            }

            StringBuilder content = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.print("Facebook response for our message : " + content.toString());
            con.disconnect();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        System.out.println("----- finished");
        return true;
    }
}
