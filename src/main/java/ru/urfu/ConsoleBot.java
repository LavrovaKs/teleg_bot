package ru.urfu;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleBot {

    String chatId = "123";

    public void chatToUser(ChatBot chatBot) throws IOException {
        System.out.println("Напиши /start, чтобы начать");
        while (true) {
            var text = new Scanner(System.in);
            String str = text.nextLine();
            System.out.println(chatBot.sendMessage(str, chatId));
        }
    }

//    String sendMassage(String command, ChatBot bot) throws IOException {
//        if(Pattern.matches("-?\\d+", command)) {
//            var mes = bot.sendExercise(Integer.parseInt(command));
//            System.out.println(mes.getExercise());
//            if (mes.getExercise().equals(" "))
//                return mes.getAnswer();
//            else if (bot.compareAnswer(mes.getAnswer()))
//                return ChatBot.TRUE_ANSWER;
//            else
//                return ChatBot.FALSE_ANSWER + mes.getAnswer();
//        }
//        else if (command.equals(ChatBot.START))
//            return ChatBot.START_MESSAGE;
//        else  if (command.equals(ChatBot.HELP))
//            return ChatBot.HELP_MESSAGE;
//        else if (command.equals(ChatBot.EXERCISE))
//            return ChatBot.EXERCISE_MESSAGE;
//        return ChatBot.NO_COMMAND;
//    }
}
