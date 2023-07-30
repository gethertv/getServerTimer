package dev.gether.getservertimer;

import dev.gether.getservertimer.cmd.GetServerTimerCmd;
import dev.gether.getservertimer.data.ServerTimerData;
import dev.gether.getservertimer.placeholder.TaskTimePlaceholder;
import dev.gether.getservertimer.utils.ColorFixer;
import dev.gether.getservertimer.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class GetServerTimer extends JavaPlugin {

    private HashMap<String, ServerTimerData> data = new HashMap<>();
    private static GetServerTimer instance;
    private Timer timer;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            TaskTimePlaceholder taskTimePlaceholder = new TaskTimePlaceholder();
            taskTimePlaceholder.register();
        }

        this.timer = new Timer();

        implementServer();

        new GetServerTimerCmd(this);
    }

    private void implementServer() {
        for(String server : getConfig().getConfigurationSection("start").getKeys(false))
        {
            data.put(server, new ServerTimerData(server, this));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            (new TaskTimePlaceholder()).unregister();
        }
    }

    public void reloadPlugin(CommandSender sender)
    {
        reloadConfig();
        this.timer = new Timer();
        if(!data.isEmpty())
            data.clear();

        implementServer();
        sender.sendMessage(ColorFixer.addColors("&aPrzeladowano plugin!"));
    }

    public Timer getTimer() {
        return timer;
    }

    public static GetServerTimer getInstance() {
        return instance;
    }

    public HashMap<String, ServerTimerData> getData() {
        return data;
    }
}
