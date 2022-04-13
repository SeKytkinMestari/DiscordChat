package net.vankila.discordchat.Commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.vankila.discordchat.Discord;
import net.vankila.discordchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ConfirmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            Random rand = new Random();

            int koodi = rand.nextInt(999999);

            Main.getInstance().getPlayerDataConfig().set(p.getUniqueId().toString() + ".koodi", koodi);
            Main.getInstance().getPlayerDataConfig().set(p.getUniqueId().toString() + ".nimi", p.getName());
            Main.getInstance().savePlayerDataConfig();

            try {
                TextChannel spam = Discord.jda.getTextChannelById(Main.getInstance().getConfig().getString("bot.spam-channel"));

                p.sendMessage(ChatColor.DARK_AQUA + "Syötä komento !vahvista " + koodi + " kanavalle " + spam.getName() +".");
            } catch (Exception e) {
                p.sendMessage(ChatColor.RED + "Virhe tapahtui.");
                Bukkit.getLogger().severe("Kanava ID ei löydetty");
            }



        }
        return false;
    }
}
