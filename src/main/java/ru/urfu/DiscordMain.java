package ru.urfu;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DiscordMain {

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault("Nzg1ODU2ODUwNjM2NTcwNjQ1.X898OQ.XQ0c1dQnPy_OLPbyo5GuiFbxNP4").build();
        jda.addEventListener(new DiscordBot());

        jda.addEventListener(new DiscordBot());

    }
}
