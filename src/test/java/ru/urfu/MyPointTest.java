package ru.urfu;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Тестирует команды /top, /my_point, /mistake
 */
public class MyPointTest {

    private ChatBot chatBot = new ChatBot();
    private static final String USER_NAME = "Telegram_Bot";
    private String chatId = "859";

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
        var mes = chatBot.analyzeCommand("/my_point", chatId, USER_NAME);
        Assert.assertEquals("0", mes);
    }

    /**
     * Проверка команды при неправильном ответе на задание
     */
    @Test
    public void falseAnswer() throws IOException {
        chatBot.analyzeCommand("/exercise", chatId, USER_NAME);
        chatBot.analyzeCommand("5", chatId, USER_NAME);
        chatBot.analyzeCommand("as", chatId, USER_NAME);
        var actual = chatBot.analyzeCommand("/my_point", chatId, USER_NAME);
        Assert.assertEquals("0", actual);
    }

    /**
     * Проверка при правильном ответе на задание
     */
    @Test
    public void trueAnswer() throws IOException {
        chatBot.analyzeCommand("/exercise", chatId, USER_NAME);
        chatBot.analyzeCommand("5", chatId, USER_NAME);
        chatBot.analyzeCommand(sendExercise().getAnswer(), chatId, USER_NAME);
        var actual = chatBot.analyzeCommand("/my_point", chatId, USER_NAME);
        Assert.assertEquals("1", actual);
    }

    /**
     * Проверка команды /top
     */
    @Test
    public void topTest() throws IOException {
        chatBot.analyzeCommand("/user_name", chatId, "third");
        for (int i = 0; i < 5; i++) {
            chatBot.analyzeCommand("/exercise", chatId, "third");
            chatBot.analyzeCommand("5", chatId, "third");
            chatBot.analyzeCommand(sendExercise().getAnswer(), chatId, "third");
        }
        chatBot.analyzeCommand("/user_name", "777", "first");
        for (int i = 0; i < 15; i++) {
            chatBot.analyzeCommand("/exercise", "777", "first");
            chatBot.analyzeCommand("5", "777", "first");
            chatBot.analyzeCommand(sendExercise().getAnswer(), "777", "first");
        }
        chatBot.analyzeCommand("/user_name", "222", "second");
        for (int i = 0; i < 10; i++) {
            chatBot.analyzeCommand("/exercise", "222", "second");
            chatBot.analyzeCommand("5", "222", "second");
            chatBot.analyzeCommand(sendExercise().getAnswer(), "222", "second");
        }
        var actual = chatBot.analyzeCommand("/top", chatId, "second");
        Assert.assertEquals("1.first - 15" + "\n2.second - 10" +
                "\n3.third - 5", actual);
    }

    /**
     * Тест на то, что команда /top не выводит null
     */
    @Test
    public void nullTopTest() throws IOException {
        chatBot.analyzeCommand("/user_name", chatId, "third");
        for (int i = 0; i < 5; i++) {
            chatBot.analyzeCommand("/exercise", chatId, "third");
            chatBot.analyzeCommand("5", chatId, "third");
            chatBot.analyzeCommand(sendExercise().getAnswer(), chatId, "third");
        }
        var actual = chatBot.analyzeCommand("/top", chatId, "second");
        Assert.assertEquals("1.third - 5", actual);
    }

    /**
     * Проверка команды /mistake
     */
    @Test
    public void mistakeTest() throws IOException {
        chatBot.analyzeCommand("/exercise", chatId, USER_NAME);
        chatBot.analyzeCommand("5", chatId, USER_NAME);
        chatBot.analyzeCommand("as", chatId, USER_NAME);
        chatBot.analyzeCommand("/exercise", chatId, USER_NAME);
        chatBot.analyzeCommand("22", chatId, USER_NAME);
        chatBot.analyzeCommand("bf", chatId, USER_NAME);
        var actual = chatBot.analyzeCommand("/mistake", chatId, USER_NAME);
        var str = "\nВам нужно повторить: " + Topic.valueOf("ALGO") + " " + Topic.valueOf("PROGRAM");
        Assert.assertEquals("Ваши ошибки:" +
                "\n" + Topic.valueOf("INFO") + " - 0" + "\n" + Topic.valueOf("SYSTEMS") + " - 0" +
                "\n" + Topic.valueOf("LOGICS") + " - 0" + "\n" + Topic.valueOf("USER") + " - 0" +
                "\n" + Topic.valueOf("ALGO") + " - 1" + "\n" + Topic.valueOf("GAME") + " - 0" +
                "\n" + Topic.valueOf("PROGRAM") + " - 1" + str, actual);
    }
}

