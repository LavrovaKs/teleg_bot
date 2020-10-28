package ru.urfu;

import java.util.Scanner;
public class Main{

    public static void main(String[] args) {
        var chatBot = new ChatBot();
        while (true) {
            var text = new Scanner(System.in);
            String str = text.nextLine();

            System.out.println(chatBot.command(str));

        }
    }
}
