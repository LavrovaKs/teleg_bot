package ru.urfu;

import java.util.Arrays;

public class ListTopic {

    private static int info = 0;
    private static int systems = 0;
    private static int logics = 0;
    private static int user = 0;
    private static int algo = 0;
    private static int game = 0;
    private static int program = 0;

    private static final String[] infoList = {"4", "7", "8", "11"};
    private static final String[] systemsList = {"14"};
    private static final String[] logicsList = {"2", "15"};
    private static final String[] userList = {"1", "3", "9", "10", "13"};
    private static final String[] algoList = {"5", "12", "16", "18"};
    private static final String[] gameList = {"19", "20", "21"};
    private static final String[] programList = {"6", "17", "22", "23"};

    /**
     * Метод анализирует ошибки
     *
     * @param ex номер задания
     */
    public void analyzeMistake(String ex) {
        sortList();
        if (Arrays.binarySearch(userList, ex) >= 0)
            user = user + 1;
        if (Arrays.binarySearch(infoList, ex) >= 0)
            info = info + 1;
        if (Arrays.binarySearch(systemsList, ex) >= 0)
            systems = systems + 1;
        if (Arrays.binarySearch(logicsList, ex) >= 0)
            logics = logics + 1;
        if (Arrays.binarySearch(algoList, ex) >= 0)
            algo = algo + 1;
        if (Arrays.binarySearch(gameList, ex) >= 0)
            game = game + 1;
        if (Arrays.binarySearch(programList, ex) >= 0)
            program = program + 1;
    }

    /**
     * Отсортируем массивы
     */
    private void sortList(){
        Arrays.sort(infoList);
        Arrays.sort(logicsList);
        Arrays.sort(systemsList);
        Arrays.sort(userList);
        Arrays.sort(algoList);
        Arrays.sort(gameList);
        Arrays.sort(programList);
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
