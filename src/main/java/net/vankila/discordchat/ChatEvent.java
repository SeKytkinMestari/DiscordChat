package net.vankila.discordchat;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

import java.util.Locale;

public class ChatEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        String kokoviesti = e.getMessage().getContentRaw();


        String[] splitted = kokoviesti.split("\\s+");

        if(e.getChannel().getId().equals(Main.getInstance().getConfig().getString("bot.discord-channel"))) {
            try {
                for (String key : Main.getInstance().getPlayerDataConfig().getConfigurationSection("").getKeys(false)) {
                    if(Main.getInstance().getPlayerDataConfig().getString(key + ".discord").equals(e.getAuthor().getId())) {
                        String m = Main.getInstance().getConfig().getString("chatformat.minecraft-chat");
                        if(e.getMessage().getAttachments().equals(0) || !e.getMessage().toString().startsWith("!")) {
                            Bukkit.broadcastMessage(m.replace("%player%", key + ".nimi").replace("%message%", e.getMessage().toString()));
                        } else {
                            return;
                        }
                    }
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }

        } else if(e.getChannel().getId().equals(Main.getInstance().getConfig().getString("bot.spam-channel"))) {
            switch (splitted[0].toLowerCase()) {
                case "!vahvista":
                    if (splitted.length != 2) {
                        e.getChannel().sendMessage("Laitathan koodisi komentoon!").complete();
                        break;
                    }
                    for (String key : Main.getInstance().getPlayerDataConfig().getConfigurationSection("").getKeys(false)) {
                        if (Integer.valueOf(splitted[1]).equals(Main.getInstance().getPlayerDataConfig().getInt(key + ".koodi"))) {
                            if (Main.getInstance().getPlayerDataConfig().getString(key + ".discord") == null) {
                                Main.getInstance().getPlayerDataConfig().set(key + ".discord", e.getAuthor().getId());
                                Main.getInstance().savePlayerDataConfig();
                            } else {
                                e.getChannel().sendMessage("Sinulla on jo discord käyttäjä linkattuna.").complete();
                                break;
                            }

                            return;
                        }


                    }
                    e.getChannel().sendMessage("Koodi ei ole oikea.").complete();
            }

        }
    }
}
