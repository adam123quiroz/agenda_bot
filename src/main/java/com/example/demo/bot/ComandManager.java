package com.example.demo.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ComandManager extends ReplyKeyboardMarkup {
    private List<String> options;
    private List<KeyboardRow> keyboard;
    KeyboardRow keyboardRow;

    public ComandManager() {
        initKeyboard();
    }

    public ComandManager(List<String> options) {
        this.options = options;
        initKeyboard();
    }

    private void initKeyboard() {
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboard(false);
        keyboard = new ArrayList<>();

        for (String option :options) {
            keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(option));
            keyboard.add(keyboardRow);
        }

        this.setKeyboard(keyboard);
    }

    public SendMessage showMenu(String messageText, Update update) {
        SendMessage sendMessageGreeting = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessageGreeting.setText(messageText);
        sendMessageGreeting.setReplyMarkup(this);
        return sendMessageGreeting;
    }
}