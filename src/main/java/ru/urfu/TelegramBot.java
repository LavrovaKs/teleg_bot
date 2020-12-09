package ru.urfu;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
    private final ChatBot chatBot = new ChatBot();

    private final InlineKeyboardMarkup menuMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardButton topButton = new InlineKeyboardButton();
    private final InlineKeyboardButton mistakeButton = new InlineKeyboardButton();
    private final InlineKeyboardButton timeButton = new InlineKeyboardButton();
    private final InlineKeyboardButton exButton = new InlineKeyboardButton();
    private final InlineKeyboardButton pointButton = new InlineKeyboardButton();
    private final InlineKeyboardMarkup nullMarkup = new InlineKeyboardMarkup();

    /**
     * Метод инициализирует все кнопки
     */
    private void initButtons() {
        topButton.setText("\uD83D\uDCCA");
        topButton.setCallbackData("/top");
        timeButton.setText("⏳");
        timeButton.setCallbackData("/time_ex");
        mistakeButton.setText("⛔");
        mistakeButton.setCallbackData("/mistake");
        exButton.setText("\uD83D\uDCDD");
        exButton.setCallbackData("/exercise");
        pointButton.setText("\uD83D\uDCCB");
        pointButton.setCallbackData("/my_point");
    }

    /**
     * Инициализация клавиатуры
     */
    TelegramBot() {
        initButtons();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(topButton);
        row.add(timeButton);
        row.add(exButton);
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(pointButton);
        row2.add(mistakeButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        rowList.add(row2);
        menuMarkup.setKeyboard(rowList);
    }

    /**
     * Основной метод, которыйполучает сообщение от пользователя и отправляет на него ответ
     *
     * @param update сообщение
     */
    @Override
    public void onUpdateReceived(Update update) {
        var answer = new SendMessage();
        if (update.hasMessage()) {
            try {
                answer = handleMessage(update);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (update.hasCallbackQuery()) {
            try {
                answer = handleCallback(update);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод обрабатывет сообщения, отправленные пользователем
     *
     * @param update сообщение
     * @return SendMessage
     */
    private SendMessage handleMessage(Update update) throws IOException {
        Message message = update.getMessage();
        var user = message.getChat().getLastName() + " " + message.getChat().getFirstName();
        var result = chatBot.analyzeCommand(message.getText(), message.getChatId().toString(), user);

        var answer = new SendMessage();
        answer.setChatId(update.getMessage().getChatId());
        answer.setText(result);

        if (message.getText().equals("/help") || message.getText().equals("/start")
                || message.getText().equals("/top") || message.getText().equals("/mistake"))
            answer.setReplyMarkup(menuMarkup);
        else answer.setReplyMarkup(nullMarkup);

        return answer;
    }

    /**
     * Метод обрабатывает сообщение, полученные при нажатии кнопки
     *
     * @param update сообщение
     * @return SendMessage
     */
    private SendMessage handleCallback(Update update) throws IOException {
        var command = update.getCallbackQuery().getData();
        var message = update.getCallbackQuery().getMessage();
        var user = message.getChat().getLastName() + " " + message.getChat().getFirstName();
        String result = chatBot.analyzeCommand(command, message.getChatId().toString(), user);

        SendMessage answer = new SendMessage();
        answer.setChatId(update.getCallbackQuery().getMessage().getChatId());
        answer.setText(result);

        if (command.equals("/help") || command.equals("/start") || command.equals("/top")
                || command.equals("/mistake"))
            answer.setReplyMarkup(menuMarkup);
        else answer.setReplyMarkup(nullMarkup);

        return answer;
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
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
