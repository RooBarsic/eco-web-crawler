package com.company.api.bot.telegramm;

import org.telegram.telegrambots.exceptions.TelegramApiException;

public interface TelegramBotCommand {
    boolean parseCommand(final String command);
    boolean executeCommand(final Long chatId, String command) throws TelegramApiException;
    TelegramBotCommand copyThis();
}
