package dev.gether.autotask.placeholder;

import dev.gether.autotask.AutoTask;
import dev.gether.autotask.data.EventData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class TaskTimePlaceholder extends PlaceholderExpansion {

    DecimalFormat formatter = new DecimalFormat("00");
    @Override
    public @NotNull String getIdentifier() {
        return "autotask";
    }

    @Override
    public @NotNull String getAuthor() {
        return "gethertv";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    private LocalDateTime startDate;
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        if (offlinePlayer.getPlayer() == null) return null;
        Player player = offlinePlayer.getPlayer();
        if (identifier.equalsIgnoreCase("next"))
        {
            return getTimeToNextEvent();
        }
        if (identifier.equalsIgnoreCase("now"))
        {
            return getCurrentTime();
        }
        if (identifier.equalsIgnoreCase("startserver"))
        {
            return startServer();
        }
        for(EventData eventData : AutoTask.getInstance().getEventData())
        {
            if(identifier.equalsIgnoreCase(eventData.getId()))
            {
                return getTimeToNextEvent(eventData);
            }
        }
        return null;
    }

    private String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getTimeToNextEvent() {
        Collections.sort(AutoTask.getInstance().getEventData(), Comparator.comparing(EventData::getEventTimes));
        LocalTime currentTime = LocalTime.now();
        LocalTime nextEventTime = null;
        for (EventData event : AutoTask.getInstance().getEventData()) {
            if (event.getEventTimes().isAfter(currentTime)) {
                nextEventTime = event.getEventTimes();
                break;
            }
        }
        if (nextEventTime != null) {
            long seconds = ChronoUnit.SECONDS.between(currentTime, nextEventTime);
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long remainingSeconds = seconds % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
        }
        for (EventData event : AutoTask.getInstance().getEventData()) {
            if (event.getEventTimes().isBefore(currentTime)) {
                nextEventTime = event.getEventTimes();
                break;
            }
        }
        long seconds = 86400 + ChronoUnit.SECONDS.between(currentTime, nextEventTime);
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        String temp = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
        return temp;

    }

    public String getTimeToNextEvent(EventData eventData) {
        LocalTime currentTime = LocalTime.now();
        LocalTime nextEventTime = null;
        if (eventData.getEventTimes().isAfter(currentTime)) {
            nextEventTime = eventData.getEventTimes();
        }
        if (nextEventTime != null) {
            long seconds = ChronoUnit.SECONDS.between(currentTime, nextEventTime);
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long remainingSeconds = seconds % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
        }
        nextEventTime = eventData.getEventTimes();
        long seconds = 86400 + ChronoUnit.SECONDS.between(currentTime, nextEventTime);
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        String temp = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
        return temp;
    }

    public String startServer()
    {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(startDate, now);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
    }

}
