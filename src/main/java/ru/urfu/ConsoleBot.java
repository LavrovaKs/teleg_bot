package ru.urfu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleBot {

    public void chatToUser(ChatBot chatBot) throws IOException {
        System.out.println("Напиши /start, чтобы начать");
        while (true) {
            var text = new Scanner(System.in);
            String str = text.nextLine();
            System.out.println(sendMassage(str, chatBot));
        }
    }

    public String sendMassage(String com, ChatBot bot) throws IOException {
        if(Pattern.matches("\\d+", com)) {
            var mes = bot.sendExercise(Integer.parseInt(com));
            System.out.println(mes.getExercise());
            if (mes.getExercise().equals(" "))
                return mes.getAnswer();
            else if (bot.compareAnswer(mes.getAnswer()))
                return bot.trueAnswer;
            else
                return bot.falseAnswer + mes.getAnswer();
        }
        else if (com.equals(bot.start))
            return bot.startMessage;
        else  if (com.equals(bot.help))
            return bot.helpMessage;
        else if (com.equals(bot.exercise))
            return bot.exerciseMessage;
        return bot.noCommand;
    }
}
