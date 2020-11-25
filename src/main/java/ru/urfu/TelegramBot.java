package ru.urfu;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Класс отвечает за работу telegram-бота
 */
public class TelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "1360870382:AAHQiNwIXLcctU6Qb2gUhoja5UWD79lT0K8";
    private static final String BOT_NAME = "ImformaticBot";
    private ChatBot chatBot = new ChatBot();


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String txt = message.getText();
        var text = "";
        try {
            text = chatBot.analyzeCommand(txt, message.getChatId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMessage(message, text);
    }

    /**
     * Метода отправляет сообщение в диалоге с telegram-ботом
     *
     * @param msg  сообщение пользователя
     * @param text текст сообщения
     */
    private void sendMessage(Message msg, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(msg.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
