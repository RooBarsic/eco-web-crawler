package com.company.api.bot.telegramm.commands;

import com.company.api.bot.telegramm.TelegramBotCommand;
import com.company.api.search.DataEntity;
import com.company.api.search.DataTable;
import com.company.api.search.SearchEngine;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomSearchTelegramBotCommand extends DefaultAbsSender implements TelegramBotCommand {
    private final String WINDOWS_CHROME_DRIVER_PATH = "./chromedriver.exe";
    private final String LINUX_CHROME_DRIVER_PATH = "./chromedriver";
    private final String botToken;

    public CustomSearchTelegramBotCommand(DefaultBotOptions options, final String botToken) {
        super(options);
        this.botToken = botToken;
    }

    @Override
    public boolean parseCommand(String command) {
        return command.startsWith("/custom");
    }

    @Override
    public boolean executeCommand(Long chatId, String command) {
        if(!parseCommand(command)) {
            return false;
        }
        try {
            String buffer[] = command.split("/custom ", 2);
            command = buffer[1];

            System.setProperty("webdriver.chrome.driver", WINDOWS_CHROME_DRIVER_PATH);
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.google.com/search?q=" + command);
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(scrFile, new File("creen"));

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setNewPhoto(new File("creen"));
            sendPhoto(sendPhoto);

            return true;
        }
        catch (Exception ef){
            ef.printStackTrace();
            return false;
        }
    }

    @Override
    public TelegramBotCommand copyThis() {
        return new CustomSearchTelegramBotCommand(getOptions(), botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

