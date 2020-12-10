package ru.urfu;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.IOException;


public class DiscordBot extends ListenerAdapter {
    final ChatBot chatBot = new ChatBot();
    public static String pref = "/";

    /**
     * Тут вообщем DiscordBot просыпается и готов к работе
     */
    public static void main(String[] args) throws LoginException {
        JDABuilder jdaBuilder = JDABuilder.createDefault("Nzg1ODU2ODUwNjM2NTcwNjQ1.X898OQ.FACsIzlR3hB6uo4NK91IR9xrAMw");
        JDA jda = null;
        DiscordBot discordBot = new DiscordBot();
        jdaBuilder.addEventListeners(discordBot);
        try{
            jda = jdaBuilder.build();
        } catch (LoginException e){
            e.printStackTrace();
        }

        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing("ЕГЭ по информатике"));
    }

    /**
     * тут получаем сообщение и передаем в чат бота чтоб все работало как надо
     * @param event
     */

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        var message = event.getMessage().getContentRaw();
        String id = event.getMessage().getId();
        String userId = event.getAuthor().getId();
        String user = event.getAuthor().toString();
        var answer = " ";
        try {
            answer = chatBot.analyzeCommand(message, userId, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var channel = event.getChannel();
        channel.sendMessage(answer).queue();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args[0].equalsIgnoreCase(DiscordBot.pref + "info")) {
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("HelpInfo");
            info.setDescription("Привет, я твой помощник в подготовке к ЕГЭ по информатике");
            info.setColor(0xa83299);
            info.addField("Чтобы начать напишите /exercise", "commands \n " +
                    "/help - открыть справку \n" +
                    "/exercise - выбор задания \n" +
                    "/time_ex - выполнение задания на время \n" +
                    "/my_point - посмотреть количество набранных баллов \n" +
                    "/mistake - анализ частых ошибок по темам \n" +
                    "/top - вывод лидеров рейтинга", false);

            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }
}