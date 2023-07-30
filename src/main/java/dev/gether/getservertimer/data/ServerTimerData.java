package dev.gether.getservertimer.data;

import dev.gether.getservertimer.GetServerTimer;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;

public class ServerTimerData {

    private String name;
    private LocalDateTime startTime;

    public ServerTimerData(String name, GetServerTimer plugin) {
        this.name = name;
        Bukkit.broadcastMessage("#"+name);
        this.startTime = LocalDateTime.of
                (plugin.getConfig().getInt("start."+name+".rok"),
                    plugin.getConfig().getInt("start."+name+".miesiac"),
                    plugin.getConfig().getInt("start."+name+".dzien"),
                    plugin.getConfig().getInt("start."+name+".godzina"),
                    plugin.getConfig().getInt("start."+name+".minuta")
                );
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
