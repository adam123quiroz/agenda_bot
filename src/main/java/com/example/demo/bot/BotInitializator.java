package com.example.demo.bot;

import com.example.demo.bl.BotBl;
import com.example.demo.dao.ContactFileRepository;
import com.example.demo.dao.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class BotInitializator {

    BotBl botBl;
    FileRepository fileRepository;
    ContactFileRepository contactFileRepository;

    @Autowired
    public BotInitializator(BotBl botBl, FileRepository fileRepository, ContactFileRepository contactFileRepository) {
        this.botBl = botBl;
        this.fileRepository = fileRepository;
        this.contactFileRepository = contactFileRepository;
    }

    public BotInitializator() {
    }

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MainBot(botBl, fileRepository, contactFileRepository));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
