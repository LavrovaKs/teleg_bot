package ru.urfu;

import java.util.HashMap;

/**
 * Класс ведет подсчет ошибок
 */
public class ListTopic {

    private static int info = 0;
    private static int systems = 0;
    private static int logics = 0;
    private static int user = 0;
    private static int algo = 0;
    private static int game = 0;
    private static int program = 0;

    private final HashMap<Integer, String> topics;

    /**
     * Метод анализирует ошибки
     *
     * @param ex номер задания
     */
    public void analyzeMistake(String ex) {
        var topic = topics.get(Integer.parseInt(ex));
        if (topic.equals("algo"))
            algo = algo + 1;
        if (topic.equals("user"))
            user = user + 1;
        if (topic.equals("logics"))
            logics = logics + 1;
        if (topic.equals("game"))
            game = game + 1;
        if (topic.equals("info"))
            info = info + 1;
        if (topic.equals("systems"))
            systems = systems + 1;
        if (topic.equals("program"))
            program = program + 1;
    }

    /**
     * Разобъем номера задач по темам
     */
    ListTopic() {
        topics = new HashMap<>();
        topics.put(1, "user");
        topics.put(2, "logics");
        topics.put(3, "user");
        topics.put(4, "info");
        topics.put(5, "algo");
        topics.put(6, "program");
        topics.put(7, "info");
        topics.put(8, "info");
        topics.put(9, "user");
        topics.put(10, "user");
        topics.put(11, "info");
        topics.put(12, "algo");
        topics.put(13, "user");
        topics.put(14, "systems");
        topics.put(15, "logics");
        topics.put(16, "algo");
        topics.put(17, "program");
        topics.put(18, "algo");
        topics.put(19, "game");
        topics.put(20, "game");
        topics.put(21, "game");
        topics.put(22, "program");
        topics.put(23, "program");
    }

    /**
     * Метод выводит список всех ошибок
     *
     * @return список ошибок
     */

    public String getMistake() {
        var message = new StringBuilder();
        message.append("\nВам нужно повторить:");
        if (info > 0) {
            message.append(" ");
            message.append(Topic.valueOf("INFO"));
        }
        if (systems > 0) {
            message.append(" ");
            message.append(Topic.valueOf("SYSTEMS"));
        }
        if (logics > 0) {
            message.append(" ");
            message.append(Topic.valueOf("LOGICS"));
        }
        if (user > 0) {
            message.append(" ");
            message.append(Topic.valueOf("USER"));
        }
        if (algo > 0) {
            message.append(" ");
            message.append(Topic.valueOf("ALGO"));
        }
        if (game > 0) {
            message.append(" ");
            message.append(Topic.valueOf("GAME"));
        }
        if (program > 0) {
            message.append(" ");
            message.append(Topic.valueOf("PROGRAM"));
        }
        return "Ваши ошибки:" +
                "\n" + Topic.valueOf("INFO") + " - " + info +
                "\n" + Topic.valueOf("SYSTEMS") + " - " + systems +
                "\n" + Topic.valueOf("LOGICS") + " - " + logics +
                "\n" + Topic.valueOf("USER") + " - " + user +
                "\n" + Topic.valueOf("ALGO") + " - " + algo +
                "\n" + Topic.valueOf("GAME") + " - " + game +
                "\n" + Topic.valueOf("PROGRAM") + " - " + program + message.toString();
    }
}
