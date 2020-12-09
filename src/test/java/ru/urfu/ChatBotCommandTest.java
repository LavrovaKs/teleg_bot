package ru.urfu;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Тестирует правильность работы всех команд бота
 *
 * @author Ксения
 */
public class ChatBotCommandTest {

    private ChatBot chatBot = new ChatBot();
    private static final String CHAT_ID = "859";
    private static final String USER_NAME = "Telegram_Bot";

    /**
     * Проверка, что при старте бот присылает соответствующее сообщение
     */
    @Test
    public void startTest() throws IOException {
        var mes = chatBot.analyzeCommand("/start", CHAT_ID, USER_NAME);
        Assert.assertEquals("Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
                "\nсписок доступных команд:" +
                "\n/help - открыть справку" +
                "\n/exercise - \uD83D\uDCDD выбор задания" +
                "\n/time_ex - ⏳ выполнение задания на время" +
                "\n/my_point - \uD83D\uDCCB посмотреть количество набранных баллов" +
                "\n/mistake - ⛔ анализ частых ошибок по темам" +
                "\n/top - \uD83D\uDCCA вывод лидеров рейтинга", mes);
    }

    /**
     * Проверка, что выводится красткая справка
     */
    @Test
    public void helpTest() throws IOException {
        var mes = chatBot.analyzeCommand("/help", CHAT_ID, USER_NAME);
        Assert.assertEquals("Список доступных команд: " +
                "\n/help - открыть справку" +
                "\n/exercise - \uD83D\uDCDD выбор задания" +
                "\n/time_ex - ⏳ выполнение задания на время" +
                "\n/my_point - \uD83D\uDCCB посмотреть количество набранных баллов" +
                "\n/mistake - ⛔ анализ частых ошибок по темам" +
                "\n/top - \uD83D\uDCCA вывод лидеров рейтинга", mes);
    }

    /**
     * Проверка неверной команды
     */
    @Test
    public void noComTest() throws IOException {
        var mes = chatBot.analyzeCommand("gdkt", CHAT_ID, USER_NAME);
        Assert.assertEquals("Не уверен, что такая команда мне по силам", mes);
    }

    /**
     * Проверка, что при команде /exercise бот выводит соответствующее собщение
     */
    @Test
    public void exTest() throws IOException {
        var mes = chatBot.analyzeCommand("/exercise", CHAT_ID, USER_NAME);
        Assert.assertEquals("Введите номер задания", mes);
    }

    /**
     * Проверка, что при команде /time_ex бот выводит соответствующее собщение
     */
    @Test
    public void timeTest() throws IOException {
        var mes = chatBot.analyzeCommand("/time_ex", CHAT_ID, USER_NAME);
        Assert.assertEquals("Введите номер задания", mes);
    }
}
