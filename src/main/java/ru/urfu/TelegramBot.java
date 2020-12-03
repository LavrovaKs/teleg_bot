package ru.urfu;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * Метод отвечает за работу кнопок в самом Telegramm
     */
    public void setButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow_1 = new KeyboardRow();
        KeyboardRow keyboardRow_2 = new KeyboardRow();

        keyboardRow_1.add(new KeyboardButton("/help"));
        keyboardRow_1.add(new KeyboardButton("/start"));
        keyboardRow_1.add(new KeyboardButton("/exercise"));
        keyboardRow_2.add(new KeyboardButton("/time_ex"));
        keyboardRow_2.add(new KeyboardButton("/my_point"));
        keyboardRow_2.add(new KeyboardButton("/top"));
        keyboardRow_2.add(new KeyboardButton("/mistake"));

        keyboardRowList.add(keyboardRow_1);
        keyboardRowList.add(keyboardRow_2);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

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
            setButton(sendMessage);
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
