package com.company;

import com.company.api.bot.telegramm.TelegramBot;
import com.company.api.open.handler.HelpCustomHttpHandlerCommand;
import com.company.api.open.handler.SearchCustomHttpHandlerCommand;
import com.company.api.search.SearchEngine;
import com.company.api.search.custom.CustomSearcherEngine;
import com.company.api.search.google.GoogleSearchEngine;
import com.sun.net.httpserver.HttpServer;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.ApiContextInitializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PRODUCTION_BOT_NAME = "EcoWebCrawler_bot";
    private static String PRODUCTION_BOT_TOKEN;
    private static final String TESTING_BOT_NAME = "DManager_test_bot";
    private static String TESTING_BOT_TOKEN;
    private static String GOOGLE_SEARCH_ENGINE_TOKEN;
    private static int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException {
        System.out.println(" Hello web. ");
        initTokens();
        final List<SearchEngine> searchEngines = initSearchEngines();

        runBot(searchEngines);

        runOpenRestHandler(searchEngines);

        //Test.run();
        //ScreenShot.run();
        System.out.println(" All systems up");
    }

    public static void initTokens() {
        PRODUCTION_BOT_TOKEN = System.getenv("PRODUCTION_BOT_TOKEN");
        GOOGLE_SEARCH_ENGINE_TOKEN = System.getenv("GOOGLE_SEARCH_ENGINE_TOKEN");
        TESTING_BOT_TOKEN = System.getenv("TESTING_BOT_TOKEN");
    }

    public static List<SearchEngine> initSearchEngines() {
        List<SearchEngine> searchEngineList = new ArrayList<>();
        searchEngineList.add(new CustomSearcherEngine());
        searchEngineList.add(new GoogleSearchEngine(GOOGLE_SEARCH_ENGINE_TOKEN));
        return searchEngineList;
    }

    public static void runBot(final @NotNull List<SearchEngine> searchEngineList) {
        ApiContextInitializer.init();
        TelegramBot productionBot = new TelegramBot(PRODUCTION_BOT_NAME, PRODUCTION_BOT_TOKEN, searchEngineList);
        productionBot.botConnect();
//        TelegramBot testBot = new TelegramBot(TESTING_BOT_NAME, TESTING_BOT_TOKEN, searchEngineList);
//        testBot.botConnect();
    }

    public static void runOpenRestHandler(final @NotNull List<SearchEngine> searchEngineList) {
            try {
                HttpServer httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
                httpServer.createContext(SearchCustomHttpHandlerCommand.API_PATH, new SearchCustomHttpHandlerCommand(searchEngineList));
                httpServer.createContext(HelpCustomHttpHandlerCommand.API_PATH, new HelpCustomHttpHandlerCommand());

                httpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
