package com.company.api.bot.telegramm.commands;

import com.company.api.bot.telegramm.TelegramBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class HelpTelegramBotCommand extends DefaultAbsSender implements TelegramBotCommand {
    private final String botToken;
    private String HELP = "write a request to get an answer";


    public HelpTelegramBotCommand(DefaultBotOptions options, final String botToken) {
        super(options);
        this.botToken = botToken;
    }

    @Override
    public boolean parseCommand(String command) {
        return command.startsWith("/help");
    }

    @Override
    public boolean executeCommand(Long chatId, String command) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(HELP);
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
        return new HelpTelegramBotCommand(getOptions(), botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
