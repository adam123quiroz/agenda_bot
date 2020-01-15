package com.example.demo.auxs;

import com.example.demo.bl.BotBl;
import com.example.demo.bot.MainBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public class MultiUser implements Runnable {
    BotBl botBl;
    Update update;
    AdminChat adminChat;
    MainBot bot;

    public MultiUser(BotBl botBl, Update update, AdminChat adminChat, MainBot bot) {
        this.botBl = botBl;
        this.update = update;
        this.adminChat = adminChat;
        this.bot = bot;
    }

    @Override
    public void run() {
        if (update.hasMessage() && update.getMessage().hasText()) {
            List<String> messages = null;
            try {
                messages = botBl.processUpdate(update, bot);
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
            for(String messageText: messages) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(messageText);
                try {
                    bot.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        Sequence sequence = MainBot.getSequenceByChatId(update.getMessage().getChat().getId().toString());
        if (sequence != null) {
            if (update.getMessage().hasPhoto()) {
                if (sequence.isRunning() && (sequence.getStepActually() == 2 || sequence.getStepActually() == 5)){
                    try {
                        botBl.processUpdate(update, bot);
                    } catch (TelegramApiException | IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
