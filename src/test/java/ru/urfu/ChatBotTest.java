package ru.urfu;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class ChatBotTest {

    ChatBot chatBot = new ChatBot();
    String chatId = "859";

    @Test
    public void startTest() throws IOException {
        var mes = chatBot.analizeCommand("/start", chatId);
        Assert.assertEquals("Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
                "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания" +
                "\n/time_ex - выполнение задания на время", mes);
    }

    @Test
    public void helpTest() throws IOException {
        var mes = chatBot.analizeCommand("/help", chatId);
        Assert.assertEquals("Список доступных команд: \n/help - открыть справку " +
                "\n/exercise - выбор задания \n/time_ex - выполнение задания на время", mes);
    }

    @Test
    public void noComTest() throws IOException {
        var mes = chatBot.analizeCommand("gdkt", chatId);
        Assert.assertEquals("Не уверен, что такая команда мне по силам", mes);
    }

    @Test
    public void noExTest() throws IOException {
        chatBot.analizeCommand("/exercise", chatId);
        var mes = chatBot.analizeCommand("25", chatId);
        Assert.assertEquals("Нет такого номера задания", mes);
    }

    @Test
    public void exTest() throws IOException {
        var mes = chatBot.analizeCommand("/exercise", chatId);
        Assert.assertEquals("Введите номер задания", mes);
    }

    @Test
    public void timeTest() throws IOException {
        var mes = chatBot.analizeCommand("/time_ex", chatId);
        Assert.assertEquals("Введите номер задания", mes);
    }

    @Test
    public void methodSendExTest() throws IOException {
        chatBot.analizeCommand("/time_ex", chatId);
        var mes = chatBot.analizeCommand("-1", chatId);

        Assert.assertEquals("Нет такого номера задания", mes);
    }
}
