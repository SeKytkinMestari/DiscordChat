package net.vankila.discordchat;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;

public class Discord {
    public static JDA jda;

    public Discord() {
        BotBuilder();
    }

    private void BotBuilder() {

        try {
            if(Main.getInstance().getConfig().getString("bot.token").equals("your-token-here")) {
                Main.getInstance().disablePlugin("bot-token-untouched");
                return;
            } if (Main.getInstance().getConfig().getString("bot.discord-channel").equals("your-log-channel-id-here")) {
                Main.getInstance().disablePlugin("channel-id-untouched");
                return;
            } if (Main.getInstance().getConfig().getString("bot.spam-channel").equals("your-discord-command-channel-id-here")) {
                Main.getInstance().disablePlugin("channel-id-untouched");
            }

            jda = JDABuilder.createDefault(Main.getInstance().getConfig().getString("bot.token")).enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS).build();


            jda.awaitReady();

            if(!Main.getInstance().getConfig().getString("bot.playing-status").equals("")) {
                jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing(Main.getInstance().getConfig().getString("bot.playing-status")));
            } else if(Main.getInstance().getConfig().getString("bot.playing-status").equals("")) {
                jda.getPresence().setStatus(OnlineStatus.ONLINE);
            }

        } catch(LoginException | InterruptedException e){
            Bukkit.getLogger().severe("An error occurred while trying to log into the discord bot. (Token maybe wrong?)");
            e.printStackTrace();
        }
    }
}
