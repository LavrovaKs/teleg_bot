package ru.urfu;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class MyPointTest {

    ChatBot chatBot = new ChatBot();
    String chatId = "859";
    String userName = "Telegram_Bot";

    /**
     * Найдем текст задания и ответ
     */
    public Pair sendExercise() throws IOException {
        var path = String.valueOf(Paths.get("").toAbsolutePath().resolve("src/main/exercises/ex"
                + "5" + ".txt"));
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String line;
            StringBuilder ex = new StringBuilder();
            ArrayList<String> exercise = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                exercise.add(line);
            }
            var answer = exercise.remove(exercise.size() - 1);
            for (String s : exercise) {
                ex.append(s);
                ex.append('\n');
            }
            return new Pair(answer, ex.toString());
        }
    }

    /**
     * Проверка на то, что при страте у пользователя 0 баллов
     */
    @Test
    public void startPoint() throws IOException {
        var mes = chatBot.analyzeCommand("/my_point", chatId);
        Assert.assertEquals("0", mes);
    }

    /**
     * Проверка команды при неправильном ответе на задание
     */
    @Test
    public void falseAnswer() throws IOException {
        chatBot.analyzeCommand("/exercise", chatId);
        chatBot.analyzeCommand("5", chatId);
        chatBot.analyzeCommand("as", chatId);
        var actual = chatBot.analyzeCommand("/my_point", chatId);
        Assert.assertEquals("0", actual);
    }

    /**
     * Проверка при правильном ответе на задание
     */
    @Test
    public void trueAnswer() throws IOException {
        chatBot.analyzeCommand("/exercise", chatId);
        chatBot.analyzeCommand("5", chatId);
        chatBot.analyzeCommand(sendExercise().getAnswer(), chatId);
        var actual = chatBot.analyzeCommand("/my_point", chatId);
        Assert.assertEquals("1", actual);
    }
}

