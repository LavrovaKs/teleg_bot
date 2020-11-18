package ru.urfu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ChatBot {

    protected static final String START = "/start";
    protected static final String HELP = "/help";
    protected static final String EXERCISE = "/exercise";
    protected static final String START_MESSAGE = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:\n/help - открыть справку\n/exercise - выбор задания";
    protected static final String HELP_MESSAGE = "Список доступных команд: \n/help - открыть справку " +
            "\n/exercise - выбор задания";
    protected static final String EXERCISE_MESSAGE = "Введите номер задания";
    protected static final String NO_COMMAND = "Не уверен, что такая команда мне по силам";
    protected static final String TRUE_ANSWER = "Правильный ответ!";
    protected static final String FALSE_ANSWER = "Правильный ответ: ";
    protected static final String NO_EXERCISE = "Нет такого номера задания";

    protected HashMap<String, String> state = new HashMap<>();//ключ - chatId, значение - ответ

    protected HashMap<String, Context> contexts = new HashMap<>();//ключ - chatId, значение - сщстояние


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
        else return new Pair(" ", NO_COMMAND);
    }


    public String sendMessage(String command, String chatId) throws IOException {
        if(!contexts.containsKey(chatId)){
            var context = new Context();
            var waiting = new WaitingMessage();
            waiting.setNext();
            context.setCurrentState(waiting);
            contexts.put(chatId, context);
        }
        if (command.equals(START))
            return START_MESSAGE;
        if (command.equals(HELP))
            return HELP_MESSAGE;
        if (command.equals(EXERCISE)) {
            contexts.get(chatId).switchState();
            return EXERCISE_MESSAGE;
        }
        if((Pattern.matches("-?\\d+", command)) && (contexts.get(chatId).getCurrentState()
                instanceof WaitingEx)) {
            contexts.get(chatId).switchState();
            var mes = sendExercise(Integer.parseInt(command));
            if (!mes.getAnswer().equals(" "))
                state.put(chatId, mes.getAnswer());
            return mes.getExercise();
        }
        if (((contexts.get(chatId)).getCurrentState() instanceof WaitingAnswer) &&
                (Pattern.matches("-?[0-9a-zA-Z]*", command))){
            contexts.get(chatId).switchState();
            var answer = state.remove(chatId);
            if (answer.equals(command))
                return TRUE_ANSWER;
            else return FALSE_ANSWER + answer;
        }
        else return NO_COMMAND;
    }
}
