package ru.urfu;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class AppTest 
{
    ConsoleBot consoleBot = new ConsoleBot();
    ChatBot chatBot = new ChatBot();

    @Test
    public void startTest() throws IOException {
        var mes = consoleBot.sendMassage("/start", chatBot);
        Assert.assertEquals("Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
                "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания", mes);
    }

    @Test
    public void helpTest() throws IOException {
        var mes = consoleBot.sendMassage("/help", chatBot);
        Assert.assertEquals(mes, ChatBot.HELP_MESSAGE);
    }

    @Test
    public void noComTest() throws IOException {
        var mes = consoleBot.sendMassage("gdkt", chatBot);
        Assert.assertEquals("Не уверен, что такая команда мне по силам", mes);
    }

    @Test
    public void noExTest() throws IOException {
        var mes = consoleBot.sendMassage("25", chatBot);
        var mes1 = consoleBot.sendMassage("0", chatBot);
        Assert.assertEquals("Нет такого номера задания", mes);
        Assert.assertEquals("Нет такого номера задания", mes1);
    }

    @Test
    public void exTest() throws IOException {
        var mes = consoleBot.sendMassage("/exercise", chatBot);
        Assert.assertEquals("Введите номер задания", mes);
    }
}
