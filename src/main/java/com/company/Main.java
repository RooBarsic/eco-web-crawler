package com.company;

import com.company.api.bot.telegramm.TelegramBot;
import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import com.company.api.search.custom.CustomSearcherEngine;
import com.company.api.search.google.GoogleSearchEngine;
import org.telegram.telegrambots.ApiContextInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PRODUCTION_BOT_NAME = "EcoWebCrawler_bot";
    private static String PRODUCTION_BOT_TOKEN;
    private static final String TESTING_BOT_NAME = "DManager_test_bot";
    private static String TESTING_BOT_TOKEN;
    private static String GOOGLE_SEARCH_ENGINE_TOKEN;

    public static void main(String[] args) throws IOException {
        System.out.println(" Hello web. ");
        initTokens();

        runBot();

        //Test.run();
        //ScreenShot.run();
    }

    public static void initTokens() {
        PRODUCTION_BOT_TOKEN = System.getenv("PRODUCTION_BOT_TOKEN");
        GOOGLE_SEARCH_ENGINE_TOKEN = System.getenv("GOOGLE_SEARCH_ENGINE_TOKEN");
        TESTING_BOT_TOKEN = System.getenv("TESTING_BOT_TOKEN");
    }

    public static void runBot() {
        List<SearchEngine> searchEngineList = new ArrayList<>();
        searchEngineList.add(new CustomSearcherEngine());
        searchEngineList.add(new GoogleSearchEngine(GOOGLE_SEARCH_ENGINE_TOKEN));

        ApiContextInitializer.init();
//        TelegramBot productionBot = new TelegramBot(PRODUCTION_BOT_NAME, PRODUCTION_BOT_TOKEN, searchEngineList);
//        productionBot.botConnect();
        TelegramBot testBot = new TelegramBot(TESTING_BOT_NAME, TESTING_BOT_TOKEN, searchEngineList);
        testBot.botConnect();
    }

    public static void exampleGoogle() {
        String key = "AIzaSyCxPAxCwgtOfWbsew51B74O07VJKGkJRpY";
        GoogleSearchEngine googleSearchEngine = new GoogleSearchEngine(key);
        DataTable dataTable = googleSearchEngine.search("Moana");

        for(int i = 0; i < dataTable.getEntityNumber(); i++){
            DataEntity entity = dataTable.getEntity(i);
            System.out.println("i = " + i + "\n" +
                    "title = " + entity.getTitle() + "\n" +
                    "overview = " + entity.getOverview() + "\n" +
                    "url = " + entity.getUrl() + "\n");
        }

    }
}
