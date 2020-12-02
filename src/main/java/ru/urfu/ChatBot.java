package ru.urfu;

import ru.state.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    private static final String MY_NAME = "/my_name"; //только для отладки
    private static final String MY_POINT = "/my_point";
    private static final String MISTAKE = "/mistake";
    private static final String TOP = "/top";

    private static final String START_MESSAGE = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:" +
            "\n/help - открыть справку" +
            "\n/exercise - выбор задания" +
            "\n/time_ex - выполнение задания на время" +
            "\n/user_name - зарегистрировать свое имя" +
            "\n/my_point - посмотреть количество набранных баллов";
    private static final String HELP_MESSAGE = "Список доступных команд: " +
            "\n/help - открыть справку " +
            "\n/exercise - выбор задания " +
            "\n/time_ex - выполнение задания на время" +
            "\n/user_name - зарегистрировать свое имя" +
            "\n/my_point - посмотреть количество набранных баллов";

    private static final String EXERCISE_MESSAGE = "Введите номер задания";
    private static final String NO_COMMAND = "Не уверен, что такая команда мне по силам";
    private static final String NO_EXERCISE = "Нет такого номера задания";
    private static final String NO_NAME = "Вы еще не зарегистрировались";
    private static final String TRUE_ANSWER = "Правильный ответ!";
    private static final String FALSE_ANSWER = "Правильный ответ: ";
    private static final String TIME_MESSAGE = "Время выполнения: ";
    private static final String NAME_MESSAGE = "Введите ваше имя";
    private static final String WELCOME_MESSAGE = "Приятно познакомиться";


    private final HashMap<String, String> answers = new HashMap<>();//ключ - chatId, значение - ответ

    private final HashMap<String, StateManager> statesOfBot = new HashMap<>();
    //ключ - chatId, значение - переключение состояния

    private final HashMap<String, Date> dates = new HashMap<>();
    private final HashMap<String, String> exercises = new HashMap<>();
    private final HashMap<String, String> userNames = new HashMap<>(); //ключ - chatId, значение - имя пользователя
    private final HashMap<String, Integer> points = new HashMap<>(); //ключ - chatId, значение - количество баллов
    private final HashMap<String, ListTopic> mistakes = new HashMap<>(); //ключ - chatId, значение - список тем и ошибок

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
            points.put(chatId, 0);
            mistakes.put(chatId, new ListTopic());
        }
        if (statesOfBot.get(chatId).getCurrentState() instanceof WaitingName){
            statesOfBot.get(chatId).switchState();
            userNames.put(chatId, command);
            return WELCOME_MESSAGE;
        }
        if (command.equals(MY_POINT))
            return Integer.toString(points.get(chatId));
        if (command.equals(START))
            return START_MESSAGE;
        if (command.equals(HELP))
            return HELP_MESSAGE;
        if (command.equals(USER_NAME)) {
            var waiting = new WaitingName();
            waiting.setNext();
            statesOfBot.get(chatId).setCurrentState(waiting);
            return NAME_MESSAGE;
        }
        if (command.equals(MY_NAME)){
            if (!userNames.containsKey(chatId))
                return NO_NAME;
            return userNames.get(chatId);
        }
        if (command.equals(EXERCISE)) {
            statesOfBot.get(chatId).switchState();
            return EXERCISE_MESSAGE;
        }
        if (command.equals(TIME_EX)) {
            statesOfBot.get(chatId).setCurrentState(new Time());
            return EXERCISE_MESSAGE;
        }
        if (command.equals(TOP))
            return getTop();
        if (command.equals(MISTAKE))
            return getMistake(chatId);
        if ((Pattern.matches("-?\\d+", command)) && (statesOfBot.get(chatId).getCurrentState()
                instanceof WaitingEx || statesOfBot.get(chatId).getCurrentState() instanceof Time)) {
            exercises.put(chatId, command);
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
                    points.put(chatId ,points.get(chatId) + 1);
                    exercises.remove(chatId);
                    return TRUE_ANSWER + "\n" + TIME_MESSAGE + time + " секунд";
                }
                else{
                    analyzeMistake(chatId, exercises.remove(chatId));
                    return FALSE_ANSWER + answer + "\n" + TIME_MESSAGE + time + " секунд";
                }
            }
            if (answer.equals(command)) {
                points.put(chatId ,points.get(chatId) + 1);
                exercises.remove(chatId);
                return TRUE_ANSWER;
            }
            else{
                analyzeMistake(chatId, exercises.remove(chatId));
                return FALSE_ANSWER + answer;
            }
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

    private void analyzeMistake(String chatId, String ex){
        if (ex.equals("1") || ex.equals("3") || ex.equals("9") || ex.equals("10") || ex.equals("13")){
            mistakes.get(chatId).user = mistakes.get(chatId).user + 1;
            mistakes.put(chatId, mistakes.get(chatId));
        }
        if (ex.equals("4") || ex.equals("7") || ex.equals("8") || ex.equals("11")){
            mistakes.get(chatId).info = mistakes.get(chatId).info + 1;
            //mistakes.put(chatId, mistakes.get(chatId));
        }
        if (ex.equals("14")){
            mistakes.get(chatId).systems = mistakes.get(chatId).systems + 1;
        }
        if (ex.equals("2") || ex.equals("15"))
            mistakes.get(chatId).logics = mistakes.get(chatId).logics + 1;
        if (ex.equals("5") || ex.equals("12") || ex.equals("16") || ex.equals("18"))
            mistakes.get(chatId).algo = mistakes.get(chatId).algo + 1;
        if (ex.equals("19") || ex.equals("20") || ex.equals("21"))
            mistakes.get(chatId).game = mistakes.get(chatId).game + 1;
        if (ex.equals("6") || ex.equals("17") || ex.equals("22") || ex.equals("23"))
            mistakes.get(chatId).program = mistakes.get(chatId).program + 1;
    }
    private String getMistake(String chatId){
        return "Ваши ошибки:" +
                "\n" + Topic.valueOf("INFO") + " - " + mistakes.get(chatId).info +
                "\n" + Topic.valueOf("SYSTEMS") + " - " + mistakes.get(chatId).systems +
                "\n" + Topic.valueOf("LOGICS") + " - " + mistakes.get(chatId).logics +
                "\n" + Topic.valueOf("USER") + " - " + mistakes.get(chatId).user +
                "\n" + Topic.valueOf("ALGO") + " - " + mistakes.get(chatId).algo +
                "\n" + Topic.valueOf("GAME") + " - " + mistakes.get(chatId).game +
                "\n" + Topic.valueOf("PROGRAM") + " - " + mistakes.get(chatId).program;
    }

    /**
     //     * Метод составляет топ пользователей
     //     * @return топ
     //     */
    private String getTop(){
        var maxValue1 = 0;
        var maxKey1 = " ";
        var maxValue2 = 0;
        var maxKey2 = " ";
        var maxValue3 = 0;
        var maxKey3 = " ";
        for (Map.Entry<String, Integer> point : points.entrySet()) {
            if (point.getValue() > maxValue1){
                maxValue1 = point.getValue();
                maxKey1 = point.getKey();
            }
            if (point.getValue() <= maxValue1 && point.getValue() > maxValue2){
                maxValue2 = point.getValue();
                maxKey2 = point.getKey();
            }
            if (point.getValue() <= maxValue2 && point.getValue() > maxValue3){
                maxValue3 = point.getValue();
                maxKey3 = point.getKey();
            }
        }
        return "1." + userNames.get(maxKey1) + "-" + maxValue1 +
                "\n2." + userNames.get(maxKey2) + "-" + maxValue2 +
                "\n3." + userNames.get(maxKey3) + "-" + maxValue3;
    }
}
