package ru.urfu;

public class ChatBot {

    private String start = "/start";
    private String help = "/help";
    private String exercise = "/exercise";

    public String command(String text){
        if (text.equals(start))
            return "start";
        else if (text.equals(help))
            return "help";
        else return "No command";
    }
}
