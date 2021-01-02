package com.company;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FBRest {

    private static int SERVER_PORT = 8080;

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
                httpServer.createContext("/hello", exchange -> {
                    System.out.println(" ---- got /hello request");
                });

                httpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
