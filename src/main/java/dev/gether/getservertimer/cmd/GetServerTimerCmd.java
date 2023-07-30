package dev.gether.getservertimer.cmd;

import dev.gether.getservertimer.GetServerTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetServerTimerCmd implements CommandExecutor {

    private final GetServerTimer plugin;
    public GetServerTimerCmd(GetServerTimer plugin)
    {
        this.plugin = plugin;
        plugin.getCommand("getservertimer").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("getserver.admin"))
            return false;

        plugin.reloadPlugin(sender);
        return false;
    }
}
