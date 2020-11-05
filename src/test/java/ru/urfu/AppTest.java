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
        Assert.assertEquals(mes, chatBot.startMessage);
    }

    @Test
    public void helpTest() throws IOException {
        var mes = consoleBot.sendMassage("/help", chatBot);
        Assert.assertEquals(mes, chatBot.helpMessage);
    }

    @Test
    public void noComTest() throws IOException {
        var mes = consoleBot.sendMassage("gdkt", chatBot);
        Assert.assertEquals(mes, chatBot.noCommand);
    }

    @Test
    public void noExTest() throws IOException {
        var mes = consoleBot.sendMassage("25", chatBot);
        var mes1 = consoleBot.sendMassage("0", chatBot);
        Assert.assertEquals(mes, chatBot.noExercise);
        Assert.assertEquals(mes1, chatBot.noExercise);
    }

    @Test
    public void exTest() throws IOException {
        var mes = consoleBot.sendMassage("/exercise", chatBot);
        Assert.assertEquals(mes, chatBot.exerciseMessage);
    }
}
