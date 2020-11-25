package ru.urfu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ConsoleBot consoleBot = new ConsoleBot();
        var chatBot = new ChatBot();
        consoleBot.chatToUser(chatBot);
    }
}
