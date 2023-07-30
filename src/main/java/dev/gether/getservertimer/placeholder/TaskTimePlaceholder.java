package dev.gether.getservertimer.placeholder;

import dev.gether.getservertimer.GetServerTimer;
import dev.gether.getservertimer.data.ServerTimerData;
import dev.gether.getservertimer.utils.ColorFixer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;


public class TaskTimePlaceholder extends PlaceholderExpansion {

    DecimalFormat formatter = new DecimalFormat("00");
    @Override
    public @NotNull String getIdentifier() {
        return "getserver";
    }

    @Override
    public @NotNull String getAuthor() {
        return "gethertv";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        if(identifier.startsWith("timer")) {
            String[] args = identifier.split("_");
            ServerTimerData server = GetServerTimer.getInstance().getData().get(args[1]);
            Bukkit.broadcastMessage("#"+server.getName());
            Bukkit.broadcastMessage("#"+args[1]);
            if(server!=null)
            {
                return GetServerTimer.getInstance().getTimer().getTimer(server.getStartTime());
            }
        }
        return null;
    }


}
