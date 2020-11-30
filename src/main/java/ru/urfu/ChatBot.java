package ru.urfu;

import ru.state.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Основная логика и функции чат-бота
 */
public class ChatBot {

    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String EXERCISE = "/exercise";
    private static final String TIME_EX = "/time_ex";
    private static final String USER_NAME = "/user_name";
    private static final String MY_NAME = "/my_name";
    private static final String MY_POINT = "/my_point";
    private static final String START_MESSAGE = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:" +
            "\n/help - открыть справку" +
            "\n/exercise - выбор задания" +
            "\n/time_ex - выполнение задания на время";
    private static final String HELP_MESSAGE = "Список доступных команд: " +
            "\n/help - открыть справку " +
            "\n/exercise - выбор задания " +
            "\n/time_ex - выполнение задания на время";
    private static final String EXERCISE_MESSAGE = "Введите номер задания";
    private static final String NO_COMMAND = "Не уверен, что такая команда мне по силам";
    private static final String TRUE_ANSWER = "Правильный ответ!";
    private static final String FALSE_ANSWER = "Правильный ответ: ";
    private static final String NO_EXERCISE = "Нет такого номера задания";

    private static final String TIME_MESSAGE = "Время выполнения: ";
    private static final String NAME_MESSAGE = "Введите ваше имя";


    private final HashMap<String, String> answers = new HashMap<>();//ключ - chatId, значение - ответ

    private final HashMap<String, StateManager> statesOfBot = new HashMap<>();
    //ключ - chatId, значение - переключение состояния

    private final HashMap<String, Date> dates = new HashMap<>();

    private final HashMap<String, String> userNames = new HashMap<>(); //ключ - chatId, значение - имя пользователя
    private final HashMap<String, Integer> points = new HashMap<>(); //ключ - chatId, значение - количество баллов


    /**
     * Метод по номеру задания находит файл с текстом задания
     *
     * @param number номер задания
     * @return пара (ответ, задание)
     */
    public Pair findExercise(int number) throws IOException {
        if (number > 0 && number < 24) {
            var path = String.valueOf(Paths.get("").toAbsolutePath().resolve("src/main/exercises/ex"
                    + number + ".txt"));
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
        } else return new Pair(" ", NO_EXERCISE);
    }

    /**
     * Метод обрабатывает все поступающие команды
     *
     * @param command сообщение пользователя
     * @param chatId  ID чата
     * @return ответ
     * @throws IOException exception
     */

    public String analyzeCommand(String command, String chatId) throws IOException {
        if (!statesOfBot.containsKey(chatId)) {
            var stateManager = new StateManager();
            var waiting = new WaitingMessage();
            waiting.setNext();
            stateManager.setCurrentState(waiting);
            statesOfBot.put(chatId, stateManager);
        }
        if (statesOfBot.get(chatId).getCurrentState() instanceof WaitingName){
            statesOfBot.get(chatId).switchState();
            userNames.put(chatId, command);
            return "Приятно познакомиться";
        }
        if (command.equals(MY_NAME))
            return userNames.get(chatId);
        if (command.equals(MY_POINT))
            return Integer.toString(points.get(chatId));
        if (command.equals(START))
            return START_MESSAGE;
        if (command.equals(USER_NAME)) {
            //здесь плохо, нужно подумать
            var stateManager = new StateManager();
            var waiting = new WaitingName();
            waiting.setNext();
            stateManager.setCurrentState(waiting);
            statesOfBot.remove(chatId);
            statesOfBot.put(chatId, stateManager);
            return NAME_MESSAGE;
        }
        if (command.equals(HELP))
            return HELP_MESSAGE;
        if (command.equals(EXERCISE)) {
            statesOfBot.get(chatId).switchState();
            return EXERCISE_MESSAGE;
        }
        if (command.equals(TIME_EX)) {
            statesOfBot.get(chatId).setCurrentState(new Time());
            return EXERCISE_MESSAGE;
        }
        if ((Pattern.matches("-?\\d+", command)) && (statesOfBot.get(chatId).getCurrentState()
                instanceof WaitingEx || statesOfBot.get(chatId).getCurrentState() instanceof Time)) {
            if (!points.containsKey(chatId))
                points.put(chatId, 0);
            if (statesOfBot.get(chatId).getCurrentState() instanceof Time) {
                Date date = new Date();
                dates.put(chatId, date);
            }
            statesOfBot.get(chatId).switchState();
            var mes = findExercise(Integer.parseInt(command));
            if (!mes.getAnswer().equals(" "))
                answers.put(chatId, mes.getAnswer());
            return mes.getExercise();
        }
        if (((statesOfBot.get(chatId)).getCurrentState() instanceof WaitingAnswer) &&
                (Pattern.matches("-?[0-9a-zA-Z]*", command))) {
            statesOfBot.get(chatId).switchState();
            var answer = answers.remove(chatId);
            if (dates.containsKey(chatId)) {
                var time = getTime(chatId);
                if (answer.equals(command)){
                    //здесь очень плохо
                    var point = points.remove(chatId) + 1;
                    points.put(chatId, point);
                    return TRUE_ANSWER + "\n" + TIME_MESSAGE + time + " секунд";
                }
                else return FALSE_ANSWER + answer + "\n" + TIME_MESSAGE + time + " секунд";
            }
            if (answer.equals(command)) {
                //здесь очень плохо
                var point = points.remove(chatId) + 1;
                points.put(chatId, point);
                return TRUE_ANSWER;
            }
            else return FALSE_ANSWER + answer;
        } else {
            if (statesOfBot.get(chatId).getCurrentState() instanceof WaitingAnswer)
                statesOfBot.get(chatId).switchState();
            if (statesOfBot.get(chatId).getCurrentState() instanceof WaitingEx
                    || statesOfBot.get(chatId).getCurrentState() instanceof Time) {
                statesOfBot.get(chatId).switchState();
                statesOfBot.get(chatId).switchState();
            }
            return NO_COMMAND;
        }
    }

    /**
     * Метод расчитывает время, затраченное на решение задачи
     *
     * @param chatId ID чата
     * @return время затраченное на решение задачи
     */
    private String getTime(String chatId) {
        var start = dates.remove(chatId);
        var end = new Date();
        var dif = (int) ((end.getTime() - start.getTime()) / 1000);
        return Integer.toString(dif);
    }
}
