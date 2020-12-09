package ru.urfu;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws LoginException {
        //ConsoleBot consoleBot = new ConsoleBot();
       // var chatBot = new ChatBot();
        //consoleBot.chatToUser(chatBot);
        //JDA jda = new JDABuilder("Nzg1ODU2ODUwNjM2NTcwNjQ1.X898OQ.XQ0c1dQnPy_OLPbyo5GuiFbxNP4").build();
        JDA jda = JDABuilder.createDefault("Nzg1ODU2ODUwNjM2NTcwNjQ1.X898OQ.XQ0c1dQnPy_OLPbyo5GuiFbxNP4").build();
        jda.addEventListener(new DiscordBot());
            //Register our events
        jda.addEventListener(new DiscordBot());
        //jda.addEventListener(new AnotherEvent());
    }
}
