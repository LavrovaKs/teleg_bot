package ru.urfu;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleBot {

    public static void main(String[] args) {
        var chatBot = new ChatBot();
        System.out.println("Напиши /start, чтобы начать");
        while (true) {
            var text = new Scanner(System.in);
            String str = text.nextLine();
            System.out.println(getCommand(str, chatBot));
        }
    }
    public static String getCommand(String com, ChatBot bot){
        if(com.equals(bot.start))
            return bot.startMessage;
        else  if (com.equals(bot.help))
            return bot.helpMessage;
        else if (com.equals(bot.exercise))
            return bot.exerciseMessage;
        else if(Pattern.matches("\\d", com))
            return bot.sendExercise(Integer.parseInt(com));
        return "no command";
    }
}
