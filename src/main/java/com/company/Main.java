package com.company;

import com.company.api.bot.telegramm.TelegramBot;
import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.google.GoogleSearchEngine;
import org.telegram.telegrambots.ApiContextInitializer;

import java.io.IOException;

public class Main {
    private static final String PRODUCTION_BOT_NAME = "EcoWebCrawler_bot";
    private static final String PRODUCTION_BOT_TOKEN = "1346750236:AAGZQMFv0TKp4ssMUubVcTLXIlF1Q2ETrS8";
    private static final String TESTING_BOT_NAME = "DManager_test_bot";
    private static final String TESTING_BOT_TOKEN = "1114508757:AAHckjMgGFrO2wHw-deNPD0yZGf4KqTSTHU";
    private static final String GOOGLE_SEARCH_ENGINE_TOKEN = "AIzaSyCxPAxCwgtOfWbsew51B74O07VJKGkJRpY";

    public static void main(String[] args) throws IOException {
        System.out.println(" Hello web. ");

        runBot();

        //Test.run();
        //ScreenShot.run();
    }

    public static void runBot() {
        ApiContextInitializer.init();
        //TelegramBot testBot = new TelegramBot(PRODUCTION_BOT_NAME, PRODUCTION_BOT_TOKEN, new GoogleSearchEngine(GOOGLE_SEARCH_ENGINE_TOKEN));
        TelegramBot testBot = new TelegramBot(TESTING_BOT_NAME, TESTING_BOT_TOKEN, new GoogleSearchEngine(GOOGLE_SEARCH_ENGINE_TOKEN));
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
