package com.example.demo.bot;

import com.example.demo.auxs.AdminChat;
import com.example.demo.auxs.MultiUser;
import com.example.demo.auxs.Sequence;
import com.example.demo.bl.BotBl;
import com.example.demo.dao.ContactFileRepository;
import com.example.demo.dao.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class MainBot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainBot.class);

    BotBl botBl;
    FileRepository fileRepository;
    ContactFileRepository contactFileRepository;

    public static List<AdminChat> chats;

    public MainBot(BotBl customerBl, FileRepository fileRepository, ContactFileRepository contactFileRepository) {
        this.botBl = customerBl;
        this.fileRepository = fileRepository;
        this.contactFileRepository = contactFileRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (chats == null) {
            chats = new ArrayList<>();
        }
        AdminChat chat = addChatToTheList(update);
        MultiUser multiUser = new MultiUser(botBl, update, chat, this);
        Thread thread = new Thread(multiUser);
        thread.start();
    }

    private AdminChat addChatToTheList(Update update) {
        AdminChat chat = null;
        String chatId = update.getMessage().getChat().getId().toString();
        if (! idIsAtTheList(chatId)) {
            AdminChat adminChat = new AdminChat();
            adminChat.setChatId(chatId);
            adminChat.setInSequence(false);
            chats.add(adminChat);
        } else {
            chat = getChatByChatId(chatId);
        }
        return chat;
    }

    private AdminChat getChatByChatId(String chatId) {
        for (AdminChat chat :
                chats) {
            if (chat.getChatId().equalsIgnoreCase(chatId)) {
                return chat;
            }
        }
        return null;
    }

    private boolean idIsAtTheList(String chatId) {
        for (AdminChat chat :
                chats) {
            if (chat.getChatId().equalsIgnoreCase(chatId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getBotUsername() {
        return "Agendafinal_bot";
    }

    @Override
    public String getBotToken() {
        return "1041844175:AAF83u1AxL4YN-zKT39ry-9MJ-WwdIEpHT4";
    }

    public static void addSequenceToList(String chatId, Sequence sequence){
        for (AdminChat chat :
                chats) {
            if (chat.getChatId().equalsIgnoreCase(chatId)) {
                chat.setInSequence(true);
                chat.setSequence(sequence);
                break;
            }
        }
    }

    public static Sequence getSequenceByChatId(String chatId){
        for (AdminChat chat :
                chats) {
            if (chat.getChatId().equalsIgnoreCase(chatId)) {
                return chat.getSequence();
            }
        }
        return null;
    }
}
