package ru.urfu;

public class ChatBot {

    protected String start = "/start";
    protected String help = "/help";
    protected String exercise = "/exercise";
    protected String startMessage = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:\n/help - открыть справку";
    protected String helpMessage = "Список доступных команд: \n/help - открыть справку \n/exercise - выбор задания";
    protected String exerciseMessage = "Введите номер задания";

    public String sendExercise(int number) {
        if (number == 1)
            return "Задание №1";
        return "no number";
    }
}
