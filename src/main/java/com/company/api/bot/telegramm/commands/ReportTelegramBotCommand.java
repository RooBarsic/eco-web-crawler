package com.company.api.bot.telegramm.commands;

import com.company.api.bot.telegramm.TelegramBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class ReportTelegramBotCommand extends DefaultAbsSender implements TelegramBotCommand {
    private final String botToken;
    private final Long ADMIN_CHAT_ID = -357992578l;

    public ReportTelegramBotCommand(DefaultBotOptions options, final String botToken) {
        super(options);
        this.botToken = botToken;
    }

    @Override
    public boolean parseCommand(String command) {
        return false;
    }

    @Override
    public boolean executeCommand(Long chatId, String command) throws TelegramApiException {
        return true;
    }

    public boolean executeCommand(long chatId, User user) throws TelegramApiException {
        if (chatId != ADMIN_CHAT_ID) {
            SendMessage message = new SendMessage();
            message.setChatId(ADMIN_CHAT_ID);
            message.setText("#Report\n" +
                    "Request from: @" + user.getUserName() + " "
                    + user.getFirstName() + " " + user.getLastName());
            try {
                execute(message);
                return true;
            } catch (TelegramApiException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    @Override
    public TelegramBotCommand copyThis() {
        return new ReportTelegramBotCommand(getOptions(), botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
