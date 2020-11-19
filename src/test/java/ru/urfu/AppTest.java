package ru.urfu;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class AppTest 
{

    ChatBot chatBot = new ChatBot();
    String chatId = "859";

    @Test
    public void startTest() throws IOException {
        var mes = chatBot.sendMessage("/start", chatId);
        Assert.assertEquals("Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
                "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания", mes);
    }

    @Test
    public void helpTest() throws IOException {
        var mes = chatBot.sendMessage("/help", chatId);
        Assert.assertEquals(mes, ChatBot.HELP_MESSAGE);
    }

    @Test
    public void noComTest() throws IOException {
        var mes = chatBot.sendMessage("gdkt", chatId);
        Assert.assertEquals("Не уверен, что такая команда мне по силам", mes);
    }

    @Test
    public void noExTest() throws IOException {
        chatBot.sendMessage("/exercise", chatId);
        var mes = chatBot.sendMessage("25", chatId);
        Assert.assertEquals("Нет такого номера задания", mes);
    }

    @Test
    public void exTest() throws IOException {
        var mes = chatBot.sendMessage("/exercise", chatId);
        Assert.assertEquals("Введите номер задания", mes);
    }
}
