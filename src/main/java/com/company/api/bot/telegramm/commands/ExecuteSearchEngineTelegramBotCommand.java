package com.company.api.bot.telegramm.commands;

import com.company.api.bot.telegramm.TelegramBotCommand;
import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.ArrayList;
import java.util.List;

public class ExecuteSearchEngineTelegramBotCommand extends DefaultAbsSender implements TelegramBotCommand {
    private final String botToken;
    private final SearchEngine searchEngine;

    public ExecuteSearchEngineTelegramBotCommand(DefaultBotOptions options, final String botToken, final SearchEngine searchEngine) {
        super(options);
        this.botToken = botToken;
        this.searchEngine = searchEngine;
    }

    @Override
    public boolean parseCommand(String command) {
        return true;
    }

    @Override
    public boolean executeCommand(Long chatId, String command) {
        if(!parseCommand(command)) {
            return false;
        }
        try {
            DataTable dataTable = searchEngine.search(command);


            execute(new SendMessage()
                    .setChatId(chatId)
                    .setText("from " + searchEngine.getSearchEngineInfo())
            );

            for (int i = 0; i < dataTable.getEntityNumber(); i++) {
                List<List<InlineKeyboardButton>> keyboardMarkup = new ArrayList<>();
                keyboardMarkup.add(new ArrayList<>());

                DataEntity entity = dataTable.getEntity(i);
                String text = entity.getTitle() + "\n\n" + entity.getOverview();
                keyboardMarkup
                        .get(0)
                        .add(new InlineKeyboardButton()
                                .setText("Link")
                                .setUrl(entity.getUrl())
                        );

                execute(new SendMessage()
                        .setChatId(chatId)
                        .setText(text)
                        .setReplyMarkup(
                                new InlineKeyboardMarkup()
                                        .setKeyboard(keyboardMarkup)
                        )
                );

                System.out.println(" i = " + i + "\n" +
                        " : " + entity.getTitle() + "\n" +
                        " : " + entity.getOverview() + "\n" +
                        " : " + entity.getUrl() + '\n');
            }


            return true;
        }
        catch (Exception ef){
            ef.printStackTrace();
            return false;
        }
    }

    @Override
    public TelegramBotCommand copyThis() {
        return new ExecuteSearchEngineTelegramBotCommand(getOptions(), botToken, searchEngine);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

