package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Homework2DemoBot";
    }

    @Override
    public String getBotToken() {
        return "6028851178:AAHasGpSOnsA9yTg-QQvM47bMlrrpNwKTyo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            UserInputUtils userInputUtils = new UserInputUtils();

            if (userInputUtils.checkUserInput(messageText)) {
                Rate rate = new Rate(userInputUtils.getPath(messageText));
                String typeOfRate = userInputUtils.getTypeOfRate(messageText);
                sendMessage(chatId, rate.rateValues(typeOfRate));
            } else sendMessage(chatId, "Я не знаю такой команды");
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}