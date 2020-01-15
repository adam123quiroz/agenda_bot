package com.example.demo.auxs;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public abstract class Sequence {
    private boolean running;
    private int numberStep;
    private int stepActually;

    public Sequence() {
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getNumberStep() {
        return numberStep;
    }

    public void setNumberStep(int numberStep) {
        this.numberStep = numberStep;
    }

    public int getStepActually() {
        return stepActually;
    }

    public void setStepActually(int stepActually) {
        this.stepActually = stepActually;
    }

    public abstract void runSequence(Update update, List<String > chatResponce) throws IOException, TelegramApiException;
}
