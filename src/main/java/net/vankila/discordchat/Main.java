package net.vankila.discordchat;

import lombok.Getter;
import net.vankila.discordchat.Commands.ConfirmCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.event.Level;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    @Getter
    public static Main instance;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("DiscordChat starting");
        createPlayerData();
        instance = this;
        saveDefaultConfig();
        new Discord();
        getCommand("vahvista").setExecutor(new ConfirmCommand());
    }

    @Override
    public void onDisable() {
        Discord.jda.shutdown();
    }



    public void disablePlugin(String Cause) {
        if(Cause.equals("bot-token-untouched")) {
            Bukkit.getLogger().severe("Bot token hasnt been changed, stopping plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else if(Cause.equals("channel-id-untouched")) {
            Bukkit.getLogger().severe("A channel ID hasnt been changed, stopping plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private File PlayerdataFile;
    @Getter
    private FileConfiguration PlayerDataConfig;

    public void createPlayerData() {
        PlayerdataFile = new File(getDataFolder(), "ConfirmedPlayerdata.yml");
        if(!PlayerdataFile.exists()) {
            PlayerdataFile.getParentFile().mkdirs();
            saveResource("ConfirmedPlayerdata.yml", false);
        }

        PlayerDataConfig= new YamlConfiguration();
        try {
            PlayerDataConfig.load(PlayerdataFile);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerDataConfig() {
        try {
            PlayerDataConfig.save(PlayerdataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
