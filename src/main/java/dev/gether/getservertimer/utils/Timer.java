package dev.gether.getservertimer.utils;

import dev.gether.getservertimer.GetServerTimer;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;

public class Timer {



    private String format;
    private String day;
    private String hour;
    private String min;
    private String sec;

    private DecimalFormat df = new DecimalFormat("00");

    public Timer()
    {
        FileConfiguration config = GetServerTimer.getInstance().getConfig();
        this.format = config.getString("format");
        this.day = config.getString("day");
        this.hour = config.getString("hour");
        this.min = config.getString("min");
        this.sec = config.getString("sec");
    }

    public String getTimer(LocalDateTime startDate)
    {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startDate, now);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        boolean isDay = false;
        boolean isHour = false;
        boolean isMin = false;

        long secondsDur = duration.getSeconds();
        if(secondsDur>=86400)
            isDay = true;

        if(secondsDur>=3600)
            isHour = true;

        if(secondsDur>=60)
            isMin = true;

        String format = this.format;
        format = format
                .replace("{d}", isDay ? day.replace("{day}", df.format(days)) : "")
                .replace("{h}", isHour ? hour.replace("{hour}", df.format(hours)) : "")
                .replace("{m}", isMin ? min.replace("{min}", df.format(minutes)) : "")
                .replace("{s}", sec.replace("{sec}", df.format(seconds)));


        return ColorFixer.addColors(format);
    }

}
