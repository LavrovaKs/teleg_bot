package ru.urfu;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Тестирует правильность работы всех команд бота
 * @author Ксения
 */
public class ChatBotCommandTest {

    ChatBot chatBot = new ChatBot();
    String chatId = "859";

    /**
     * Проверка, что при старте бот присылает соответствующее сообщение
     */
    @Test
    public void startTest() throws IOException {
        var mes = chatBot.analyzeCommand("/start", chatId);
        Assert.assertEquals("Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
                "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания" +
                "\n/time_ex - выполнение задания на время", mes);
    }

    /**
     * Проверка, что выводится красткая справка
     */
    @Test
    public void helpTest() throws IOException {
        var mes = chatBot.analyzeCommand("/help", chatId);
        Assert.assertEquals("Список доступных команд: \n/help - открыть справку " +
                "\n/exercise - выбор задания \n/time_ex - выполнение задания на время", mes);
    }

    /**
     * Проверка неверной команды
     */
    @Test
    public void noComTest() throws IOException {
        var mes = chatBot.analyzeCommand("gdkt", chatId);
        Assert.assertEquals("Не уверен, что такая команда мне по силам", mes);
    }

    /**
     * Проверка, что при команде /exercise бот выводит соответствующее собщение
     */
    @Test
    public void exTest() throws IOException {
        var mes = chatBot.analyzeCommand("/exercise", chatId);
        Assert.assertEquals("Введите номер задания", mes);
    }

    /**
     * Проверка, что при команде /time_ex бот выводит соответствующее собщение
     */
    @Test
    public void timeTest() throws IOException {
        var mes = chatBot.analyzeCommand("/time_ex", chatId);
        Assert.assertEquals("Введите номер задания", mes);
    }


}