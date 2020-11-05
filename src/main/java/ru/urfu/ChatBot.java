package ru.urfu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatBot {

    protected String start = "/start";
    protected String help = "/help";
    protected String exercise = "/exercise";
    protected String startMessage = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания";
    protected String helpMessage = "Список доступных команд: \n/help - открыть справку \n/exercise - выбор задания";
    protected String exerciseMessage = "Введите номер задания";
    protected String noCommand = "Не уверен, что такая команда мне по силам";
    protected String trueAnswer = "Правильный ответ!";
    protected String falseAnswer = "Правильный ответ: ";
    protected String noExercise = "Нет такого номера задания";

    public Pair sendExercise(int number) throws IOException {
        if (number > 0 && number < 24) {
            var path = String.valueOf(Paths.get("").toAbsolutePath().resolve("src/main/exercises/ex"
                    + number + ".txt"));
            try (BufferedReader in = new BufferedReader(new FileReader(path))) {
                String line;
                StringBuilder ex = new StringBuilder();
                ArrayList<String> exercise = new ArrayList<>();
                while ((line = in.readLine()) != null){
                    exercise.add(line);
                }
                in.close();
                var answer = exercise.remove(exercise.size() - 1);
                for (String s : exercise) {
                    ex.append(s);
                    ex.append('\n');
                }
                return new Pair(answer, ex.toString());
            }
        }
        else return new Pair(noExercise, " ");
    }

    public Boolean compareAnswer(String answer){
        var text = new Scanner(System.in);
        String str = text.nextLine();
        return str.equals(answer);
    }

    public String sendExTeleg(int number) throws IOException {
        if (number > 0 && number < 24) {
            var path = String.valueOf(Paths.get("").toAbsolutePath().resolve("src/main/exercises/ex"
                    + number + ".txt"));
            try (BufferedReader in = new BufferedReader(new FileReader(path))) {
                String line;
                StringBuilder ex = new StringBuilder();
                while ((line = in.readLine()) != null){
                    ex.append(line);
                    ex.append('\n');
                }
                in.close();
                return ex.toString();
            }
        }
        else return null;
    }
}
