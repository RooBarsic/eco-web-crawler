package com.company;

import com.company.api.bot.telegramm.TelegramBot;
import com.company.api.db.AzureDB;
import com.company.api.db.UsersDataBaseTable;
import com.company.api.search.SearchEngine;
import com.company.api.search.custom.CustomSearcherEngine;
import com.company.api.search.google.GoogleSearchEngine;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.ApiContextInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final String PRODUCTION_BOT_NAME = "EcoWebCrawler_bot";
    private static String PRODUCTION_BOT_TOKEN;
    private static final String TESTING_BOT_NAME = "DManager_test_bot";
    private static String TESTING_BOT_TOKEN;
    private static String GOOGLE_SEARCH_ENGINE_TOKEN;
    private static int SERVER_PORT = 8080;
    private static Properties DATA_BASE_PROPERTIES = new Properties();

    public static void main(String[] args) throws IOException {
        System.out.println(" Hello web. ");
        initTokens();
        final List<SearchEngine> searchEngines = initSearchEngines();
        AzureDB azureDB = new AzureDB(DATA_BASE_PROPERTIES);
        UsersDataBaseTable usersDataBaseTable = new UsersDataBaseTable(azureDB);

        runBot(searchEngines, usersDataBaseTable);

        System.out.println(" port : " + SERVER_PORT);

        //Test.run();
        //ScreenShot.run();
        System.out.println(" All systems up");
    }

    public static void initTokens() {
        PRODUCTION_BOT_TOKEN = System.getenv("PRODUCTION_BOT_TOKEN");
        GOOGLE_SEARCH_ENGINE_TOKEN = System.getenv("GOOGLE_SEARCH_ENGINE_TOKEN");
        TESTING_BOT_TOKEN = System.getenv("TESTING_BOT_TOKEN");
        SERVER_PORT = Integer.parseInt(System.getenv("PORT"));

        DATA_BASE_PROPERTIES.setProperty("url", System.getenv("url"));
        DATA_BASE_PROPERTIES.setProperty("user", System.getenv("user"));
        DATA_BASE_PROPERTIES.setProperty("password", System.getenv("password"));
    }

    public static List<SearchEngine> initSearchEngines() {
        List<SearchEngine> searchEngineList = new ArrayList<>();
        searchEngineList.add(new CustomSearcherEngine());
        searchEngineList.add(new GoogleSearchEngine(GOOGLE_SEARCH_ENGINE_TOKEN));
        return searchEngineList;
    }

    public static void runBot(final @NotNull List<SearchEngine> searchEngineList, final @NotNull UsersDataBaseTable usersDataBaseTable) {
        ApiContextInitializer.init();
        TelegramBot productionBot = new TelegramBot(PRODUCTION_BOT_NAME, PRODUCTION_BOT_TOKEN, searchEngineList, usersDataBaseTable);
        productionBot.botConnect();
//        TelegramBot testBot = new TelegramBot(TESTING_BOT_NAME, TESTING_BOT_TOKEN, searchEngineList);
//        testBot.botConnect();
    }
}
