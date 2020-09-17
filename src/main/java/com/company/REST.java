package com.company;

import com.company.api.open.handler.HelpCustomHttpHandlerCommand;
import com.company.api.open.handler.SearchCustomHttpHandlerCommand;
import com.company.api.search.SearchEngine;
import com.company.api.search.custom.CustomSearcherEngine;
import com.company.api.search.google.GoogleSearchEngine;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class REST {
    private static String GOOGLE_SEARCH_ENGINE_TOKEN;
    private static int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException {
        System.out.println(" Hello web. ");
        initTokens();
        final List<SearchEngine> searchEngines = initSearchEngines();

        System.out.println(" port : " + SERVER_PORT);
        runOpenRestHandler(searchEngines);

        //Test.run();
        //ScreenShot.run();
        System.out.println(" All systems up");
    }

    public static void initTokens() {
        GOOGLE_SEARCH_ENGINE_TOKEN = System.getenv("GOOGLE_SEARCH_ENGINE_TOKEN");
        SERVER_PORT = Integer.parseInt(System.getenv("PORT"));
    }

    public static List<SearchEngine> initSearchEngines() {
        List<SearchEngine> searchEngineList = new ArrayList<>();
        searchEngineList.add(new CustomSearcherEngine());
        searchEngineList.add(new GoogleSearchEngine(GOOGLE_SEARCH_ENGINE_TOKEN));
        return searchEngineList;
    }

    public static void runOpenRestHandler(final @NotNull List<SearchEngine> searchEngineList) {
        new Thread(() -> {
            try {
                HttpServer httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
                httpServer.createContext(SearchCustomHttpHandlerCommand.API_PATH, new SearchCustomHttpHandlerCommand(searchEngineList));
                httpServer.createContext(HelpCustomHttpHandlerCommand.API_PATH, new HelpCustomHttpHandlerCommand());

                httpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
