package com.company.api.bot.telegramm;

import com.company.api.bot.telegramm.commands.*;
import com.company.api.search.SearchEngine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    final int RECONNECT_PAUSE = 10000;
    private final int MAX_CHILD_THREADS_NUMBER = 10;
    private List<TelegramBotCommand> botCommandsList;
    private TelegramBotCommand HELP_COMMAND;
    private ReportTelegramBotCommand REPORT_COMMAND;
    private Set<String> userSet = new HashSet<>();
    private AtomicInteger numberOfActiveChildThread;

    @Setter
    @Getter
    private String botName;

    @Setter
    private String botToken;

    public TelegramBot(String botName, String botToken, SearchEngine searchEngine) {
        this.botName = botName;
        this.botToken = botToken;

        numberOfActiveChildThread = new AtomicInteger(0);
        HELP_COMMAND = new HelpTelegramBotCommand(getOptions(), botToken);
        botCommandsList = new ArrayList<>();
        botCommandsList.add(new StartTelegramBotCommand(getOptions(), botToken));
        botCommandsList.add(new CustomSearchTelegramBotCommand(getOptions(), getBotToken()));
        botCommandsList.add(HELP_COMMAND);
        ///botCommandsList.add(new ExecuteSearchEngineTelegramBotCommand(getOptions(), botToken, searchEngine));
        REPORT_COMMAND = new ReportTelegramBotCommand(getOptions(), botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(" ### Got new request :");

        final Long chatId;
        final String inputText;
        final User user;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            inputText = update.getMessage().getText();
            user = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            inputText = update.getCallbackQuery().getData();
            user = update.getCallbackQuery().getFrom();
        } else {
            return;
        }
        System.out.println("    inputText : " + inputText);
        System.out.println("    User : "
                + user.getUserName() + " "
                + user.getFirstName() + " "
                + user.getLastName());
        System.out.println("    Number of uniq users : " + userSet.size());
        System.out.println("    Number of active child threads : " + numberOfActiveChildThread.get());

        TelegramBotCommand foundedBotCommand = HELP_COMMAND;
        for (TelegramBotCommand botCommand : botCommandsList) {
            if (botCommand.parseCommand(inputText)) {
                foundedBotCommand = botCommand;
            }
        }

        while (numberOfActiveChildThread.get() == MAX_CHILD_THREADS_NUMBER) {
            // waiting for free threads
        }

        try {
            // send report to admin chat
            REPORT_COMMAND.executeCommand(chatId, user);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        TelegramBotCommand botCommand = foundedBotCommand.copyThis();
        new Thread(() -> {
            numberOfActiveChildThread.incrementAndGet();
            try {
                botCommand.executeCommand(chatId, inputText);
            }
            catch (Exception e) {
                e.printStackTrace();
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Due to telegram limitations, we can send you files no larger than 50MB. \n" +
                        "We are currently working on this.\n\n" +
                        "Please select a another file resolution.");
                try {
                    execute(message);
                } catch (TelegramApiException ef) {
                    ef.printStackTrace();
                }
            }
            numberOfActiveChildThread.decrementAndGet();
        }).start();
    }

    @Override
    public String getBotUsername() {
        //log.debug("Bot name: " + botName);
        System.out.println(" ### Request for Bot name");
        return botName;
    }

    @Override
    public String getBotToken() {
        //log.debug("Bot token: " + botToken);
        System.out.println(" ### Request for token");
        return botToken;
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            System.out.println(" ### Bot connecting....");
            telegramBotsApi.registerBot(this);
            //log.info("TelegramAPI started. Bot connected and waiting for messages");
        } catch (TelegramApiRequestException e) {
            //log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}