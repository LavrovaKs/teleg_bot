package ru.urfu;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.regex.Pattern;


public class TelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "1360870382:AAHQiNwIXLcctU6Qb2gUhoja5UWD79lT0K8";
    private static final String BOT_NAME = "ImformaticBot";

    @Override
    public void onUpdateReceived(Update update) {
        ChatBot chatBot = new ChatBot();
        Message message = update.getMessage();
        String txt = message.getText();
        if (txt.equals(chatBot.start))
            sendMsg(message, chatBot.startMessage);
        else if (txt.equals(chatBot.help))
              sendMsg(message, chatBot.helpMessage);
        else if (txt.equals(chatBot.exercise))
            sendMsg(message, chatBot.exerciseMessage);
        else if(Pattern.matches("\\d+", txt)){
            var ex = " ";
            try {
                  ex = chatBot.sendExTeleg(Integer.parseInt(txt));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendMsg(message, ex);
        }
    }

    private void sendMsg(Message msg, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(msg.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

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
