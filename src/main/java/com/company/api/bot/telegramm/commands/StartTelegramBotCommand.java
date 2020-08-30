package com.company.api.bot.telegramm.commands;

import com.company.api.bot.telegramm.TelegramBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class StartTelegramBotCommand extends DefaultAbsSender implements TelegramBotCommand {
    private final String botToken;
    private String WELCOME = "Salam";

    public StartTelegramBotCommand(DefaultBotOptions options, final String botToken) {
        super(options);
        this.botToken = botToken;
    }

    @Override
    public boolean parseCommand(String command) {
        return command.equals("/start");
    }

    @Override
    public boolean executeCommand(Long chatId, String command) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(WELCOME);
        try {
            execute(message);
            return true;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TelegramBotCommand copyThis() {
        return new StartTelegramBotCommand(getOptions(), botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
