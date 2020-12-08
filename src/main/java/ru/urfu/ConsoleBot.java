package ru.urfu;

import java.io.IOException;
import java.util.Scanner;

/**
 * Пример использования функционала класса ChatBot в консоли
 */
public class ConsoleBot {

    private String chatId = "123";
    private String user_name = "First";

    /**
     * Описание работы бота в консоли
     *
     * @param chatBot экземпляр класса ChatBot
     * @throws IOException исключение
     */
    public void chatToUser(ChatBot chatBot) throws IOException {
        System.out.println("Напиши /start, чтобы начать");
        while (true) {
            var text = new Scanner(System.in);
            String str = text.nextLine();
            if (str.equals("/chat")) {
                var text1 = new Scanner(System.in);
                chatId = text1.nextLine();
                System.out.println("Введите ваше имя");
                var text2 = new Scanner(System.in);
                user_name = text2.nextLine();
            }
            else
                System.out.println(chatBot.analyzeCommand(str, chatId, user_name));
        }
    }
}
