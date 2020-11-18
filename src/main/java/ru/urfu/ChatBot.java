package ru.urfu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ChatBot {

    protected static final String START = "/start";
    protected static final String HELP = "/help";
    protected static final String EXERCISE = "/exercise";
    protected static final String START_MESSAGE = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания";
    protected static final String HELP_MESSAGE = "Список доступных команд: \n/help - открыть справку \n/exercise - выбор задания";
    protected static final String EXERCISE_MESSAGE = "Введите номер задания";
    protected static final String NO_COMMAND = "Не уверен, что такая команда мне по силам";
    protected static final String TRUE_ANSWER = "Правильный ответ!";
    protected static final String FALSE_ANSWER = "Правильный ответ: ";
    protected static final String NO_EXERCISE = "Нет такого номера задания";

    protected HashMap<String, String> state = new HashMap<>();//ключ - chatId, значение - ответ

    public Context context = new Context();

    /**
     *
     * @param number - номер задания
     * @return ответ, задание
     */
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
                var answer = exercise.remove(exercise.size() - 1);
                for (String s : exercise) {
                    ex.append(s);
                    ex.append('\n');
                }
                return new Pair(answer, ex.toString());
            }
        }
        else return new Pair(NO_EXERCISE, " ");
    }

    /**
     *
     * @param answer - ответ
     * @return верен ли ответ
     */
    public Boolean compareAnswer(String answer){
        var text = new Scanner(System.in);
        String str = text.nextLine();
        return str.equals(answer);
    }

    /**
     *
     * @param number - номер задания
     * @return текст задания
     */
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


    public String sendMessage(String command) {
        if (command.equals(START))
            return START_MESSAGE;
        if (command.equals(HELP))
            return HELP_MESSAGE;
        if (command.equals(EXERCISE)) {
            context.setCurrentState(new WaitingEx());
            return EXERCISE_MESSAGE;
        }
//        if(Pattern.matches("-?\\d+", command)) {
//            var mes = sendExercise(Integer.parseInt(command));
//        }
        else return NO_COMMAND;
    }
}
