package ru.urfu;

public class ListTopic {

    private static int info = 0;
    private static int systems = 0;
    private static int logics = 0;
    private static int user = 0;
    private static int algo = 0;
    private static int game = 0;
    private static int program = 0;

    /**
     * Метод анализирует ошибки
     *
     * @param ex     номер задания
     */
    public void analyzeMistake(String ex){
        if (ex.equals("1") || ex.equals("3") || ex.equals("9") || ex.equals("10") || ex.equals("13"))
            user = user + 1;
        if (ex.equals("4") || ex.equals("7") || ex.equals("8") || ex.equals("11"))
            info = info + 1;
        if (ex.equals("14"))
            systems = systems + 1;
        if (ex.equals("2") || ex.equals("15"))
            logics = logics + 1;
        if (ex.equals("5") || ex.equals("12") || ex.equals("16") || ex.equals("18"))
            algo = algo + 1;
        if (ex.equals("19") || ex.equals("20") || ex.equals("21"))
            game = game + 1;
        if (ex.equals("6") || ex.equals("17") || ex.equals("22") || ex.equals("23"))
            program = program + 1;
    }
    /**
     * Метод выводит список всех ошибок
     *
     * @return список ошибок
     */

    public String getMistake(){
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
