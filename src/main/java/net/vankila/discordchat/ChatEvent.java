package net.vankila.discordchat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getChannel().getId().equals(Main.getInstance().getConfig().getString("bot.discord-channel"))) {

        }
    }
}
