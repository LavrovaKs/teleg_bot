package ru.urfu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Тестировние корректности работы команды /exercise
 *
 * @author Ксения
 */
public class SendExTest {

    private ChatBot chatBot = new ChatBot();
    private static final String CHAT_ID = "859";
    private static final String USER_NAME = "Telegram_Bot";


    @Before
    public void doCommand() throws IOException {
        chatBot.analyzeCommand("/exercise", CHAT_ID, USER_NAME);
    }

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
     * Номер задания больше 24
     */
    @Test
    public void toMuchNumberTest() throws IOException {
        var mes = chatBot.analyzeCommand("26", CHAT_ID, USER_NAME);
        Assert.assertEquals("Нет такого номера задания", mes);
    }

    /**
     * Номер задания меньше 0
     */
    @Test
    public void noExerciseTest() throws IOException {
        var mes = chatBot.analyzeCommand("0", CHAT_ID, USER_NAME);
        Assert.assertEquals("Нет такого номера задания", mes);

    }

    /**
     * Отрицательный номер задания
     */
    @Test
    public void negativeExTest() throws IOException {
        var mes = chatBot.analyzeCommand("-7", CHAT_ID, USER_NAME);
        Assert.assertEquals("Нет такого номера задания", mes);

    }

    /**
     * Проверка того, что высылается нужное задание
     */
    @Test
    public void findExTest() throws IOException {
        var actual = chatBot.analyzeCommand("5", CHAT_ID, USER_NAME);
        Assert.assertEquals(sendExercise().getExercise(), actual);
    }

    /**
     * Проверка выводимого сообщения при верном ответе
     */
    @Test
    public void trueAnswerTest() throws IOException {
        chatBot.analyzeCommand("5", CHAT_ID, USER_NAME);
        var actual = chatBot.analyzeCommand(sendExercise().getAnswer(), CHAT_ID, USER_NAME);
        Assert.assertEquals("Правильный ответ!", actual);
    }

    /**
     * Проверка выводимого сообщения при неверном ответе
     */
    @Test
    public void falseAnswerTest() throws IOException {
        chatBot.analyzeCommand("5", CHAT_ID, USER_NAME);
        var actual = chatBot.analyzeCommand("352f", CHAT_ID, USER_NAME);
        var expected = "Правильный ответ: " + sendExercise().getAnswer();
        Assert.assertEquals(expected, actual);
    }

}



