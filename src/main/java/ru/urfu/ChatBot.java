package ru.urfu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class ChatBot {

    protected String start = "/start";
    protected String help = "/help";
    protected String exercise = "/exercise";
    protected String startMessage = "Привет, я твой помощник в подготовке к ЕГЭ по информатике." +
            "\nсписок доступных команд:\n/help - открыть справку";
    protected String helpMessage = "Список доступных команд: \n/help - открыть справку \n/exercise - выбор задания";
    protected String exerciseMessage = "Введите номер задания";

    public void sendExercise(int number) throws IOException {
        if (number == 5){
            try (BufferedReader in = new BufferedReader(new FileReader(String.valueOf(Paths.get("").toAbsolutePath().resolve("scr/main/exercises/ex5.txt"))))) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
        else System.out.println("no text");
    }
}
