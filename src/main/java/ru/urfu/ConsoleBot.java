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
}
