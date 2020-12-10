/*
package ru.urfu;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.io.IOException;


public class DiscordBot extends ListenerAdapter {
    final ChatBot chatBot = new ChatBot();

    */
/**
     * Тут вообщем DiscordBot просыпается и готов к работе
     * @param args
     * @throws LoginException
     *//*

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "Nzg1ODU2ODUwNjM2NTcwNjQ1.X898OQ.552SVaQIFste5HJn1PAKcNveH4Q";
        builder.setToken(token);
        builder.build();
        builder.setStatus(OnlineStatus.IDLE);

    }

    */
/*public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event){
        //String[] args = event.getMessage().getContentRaw().split(" ");
        String mes = event.getMessage().getContentRaw();
        event.getChannel().sendMessage(mes);

        DIscMessage dIscMessage = event.getMessage();
    }*//*


    */
/**
     * Тут должна происходить магия и бот должен начать обрабатывать команды
     * @param event
     * @throws IOException
     *//*

    */
/*public void onGuildMessageReceived(MessageReceivedEvent event) throws IOException {
        *//*
*/
/*
         * Получаем объект пришедшего сообщения из объекта события.
         *//*
*/
/*
        Message message = event.getMessage();
        *//*
*/
/*
         * Получаем объект канала пришедшего сообщения.
         *//*
*/
/*
        MessageChannel channel = message.getChannel();
        *//*
*/
/*
         * Достаем содержание сообщения.
         *//*
*/
/*
        String inputMsgStr = message.getContentDisplay();
        String text_bot = message.getContentRaw();

        try {
            inputMsgStr = chatBot.analyzeCommand(text_bot, message.getId());
            *//*
*/
/*new MessageBuilder(this.dscordClient).getChannel(channel).getContent(inputMsgStr).build();*//*
*/
/*
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*//*


    public void onMessageReceived(MessageReceivedEvent event) {
        String arg = event.getMessage().getContentRaw();
        String name = event.getMember().getUser().getName();
        String id = event.getMessage().getId();
        String massageDisc = event.getMessage().getContentRaw();
        Message message = event.getMessage();
        MessageChannel channel = message.getChannel();
        String inputMsgStr = message.getContentDisplay();
        String text_bot = message.getContentRaw();

        try {
            massageDisc = chatBot.analyzeCommand(arg,id);
            event.getChannel().sendMessage(massageDisc).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
 package ru.urfu;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class DiscordBot extends ListenerAdapter {

    public ChatBot chatBot = new ChatBot();
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        var message = event.getMessage().getContentRaw();
        String id = event.getMessage().getId();
        String user = event.getAuthor().toString();
        var answer = " ";
        try {
            answer = chatBot.analyzeCommand(message, id, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var channel = event.getChannel();
        channel.sendMessage(answer).queue();

        System.out.println(event.getChannel());
    }
}