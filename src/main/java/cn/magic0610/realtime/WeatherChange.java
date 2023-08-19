package cn.magic0610.realtime;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

public class WeatherChange implements Listener {
    @EventHandler
    public void Weather (WeatherChangeEvent e) {
        Plugin plugin = Realtime.getPlugin(Realtime.class);
        if (plugin.getConfig().getBoolean("no-weather-change")) {
            e.setCancelled(true);
        }
    }
}
