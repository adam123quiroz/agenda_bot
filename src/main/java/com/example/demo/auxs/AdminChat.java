package com.example.demo.auxs;

public class AdminChat {
    private String chatId;
    private Sequence sequence;
    private boolean isInSequence;

    public AdminChat() {
    }

    public AdminChat(String chatId, Sequence sequence, boolean isInSequence) {
        this.chatId = chatId;
        this.sequence = sequence;
        this.isInSequence = isInSequence;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public boolean isInSequence() {
        return isInSequence;
    }

    public void setInSequence(boolean inSequence) {
        isInSequence = inSequence;
    }

    @Override
    public String toString() {
        return "AdminChat{" +
                "chatId='" + chatId + '\'' +
                ", sequence=" + sequence +
                ", isInSequence=" + isInSequence +
                '}';
    }
}
