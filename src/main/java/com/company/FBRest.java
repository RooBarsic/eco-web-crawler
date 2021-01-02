package com.company;

import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import com.company.facebook.WebHook;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class FBRest {

    private static int SERVER_PORT = 8080;
    private static Integer counter = 0;

    public static void main(String[] args) throws IOException {
        System.out.println(" Hello web. ");
        initTokens();

        System.out.println(" port : " + SERVER_PORT);
        runOpenRestHandler();

        //Test.run();
        //ScreenShot.run();
        System.out.println(" All systems up");
    }

    public static void initTokens() {
        SERVER_PORT = Integer.parseInt(System.getenv("PORT"));
    }

    public static void runOpenRestHandler() {
        new Thread(() -> {
            try {
                HttpServer httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
                httpServer.createContext("/hello", new WebHook());

                httpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
