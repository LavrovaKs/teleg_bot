package ru.urfu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.Thread;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

/**
 * Тестирование корректности работы команды /time_ex
 *
 * @author Ксения
 */
public class TimeExTest {

    ChatBot chatBot = new ChatBot();
    String chatId = "879";

    @Before
    public void doCommand() throws IOException {
        chatBot.analyzeCommand("/time_ex", chatId);
    }

    /**
     * Найдем текст задания и ответ на него
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
        var mes = chatBot.analyzeCommand("26", chatId);
        Assert.assertEquals("Нет такого номера задания", mes);
    }

    /**
     * Номер задания меньше 0
     */
    @Test
    public void noExerciseTest() throws IOException {
        var mes = chatBot.analyzeCommand("0", chatId);
        Assert.assertEquals("Нет такого номера задания", mes);

    }

    /**
     * Отрицательный номер задания
     */
    @Test
    public void negativeExTest() throws IOException {
        var mes = chatBot.analyzeCommand("-7", chatId);
        Assert.assertEquals("Нет такого номера задания", mes);

    }

    /**
     * Проверка того, что высылается нужное задание
     */
    @Test
    public void findExTest() throws IOException {
        var actual = chatBot.analyzeCommand("5", chatId);
        Assert.assertEquals(sendExercise().getExercise(), actual);
    }

    /**
     * Проверка выводимого сообщения при верном ответе
     */
    @Test
    public void trueAnswerTest() throws IOException {
        chatBot.analyzeCommand("5", chatId);
        var start = new Date();
        var actual = chatBot.analyzeCommand(sendExercise().getAnswer(), chatId);
        var end = new Date();
        var dif = (int) ((end.getTime() - start.getTime()) / 1000);
        var expected = "Правильный ответ!" + "\n" + "Время выполнения: " + dif + " секунд";
        Assert.assertEquals(expected, actual);
    }

    /**
     * Проверка выводимого сообщения при неверном ответе
     */
    @Test
    public void falseAnswerTest() throws IOException, InterruptedException {
        chatBot.analyzeCommand("5", chatId);
        var start = new Date();
        var actual = chatBot.analyzeCommand("135", chatId);
        var end = new Date();
        var dif = (int) ((end.getTime() - start.getTime()) / 1000);
        var expected = "Правильный ответ: " + sendExercise().getAnswer() +
                "\n" + "Время выполнения: " + dif + " секунд";
        Assert.assertEquals(expected, actual);
    }

    /**
     * Проверка правильного подсчета времени
     */
    @Test
    public void timeTest() throws IOException, InterruptedException {
        chatBot.analyzeCommand("5", chatId);
        var start = new Date();
        Thread.sleep(5000);
        var actual = chatBot.analyzeCommand(sendExercise().getAnswer(), chatId);
        var end = new Date();
        var dif = (int) ((end.getTime() - start.getTime()) / 1000);
        var expected = "Правильный ответ!" + "\n" + "Время выполнения: " + dif + " секунд";
        Assert.assertEquals(expected, actual);
    }
}
