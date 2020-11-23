package ru.urfu;

import java.io.IOException;
import java.util.Scanner;

/**
 * Пример использования функционала класса ChatBot в консоли
 */
public class ConsoleBot {

    String chatId = "123";

    /**
     * Описание работы бота в консоли
     * @param chatBot экземпляр класса ChatBot
     * @throws IOException исключение
     */
    public void chatToUser(ChatBot chatBot) throws IOException {
        System.out.println("Напиши /start, чтобы начать");
        while (true) {
            var text = new Scanner(System.in);
            String str = text.nextLine();
            System.out.println(chatBot.analizeCommand(str, chatId));
        }
    }
}
