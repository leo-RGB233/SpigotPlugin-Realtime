package cn.magic0610.realtime;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public final class Realtime extends JavaPlugin {
    @Override
    public void onEnable() {
        //插件加载时
        getLogger().info("Realtime enabled!");
        File config_file = new File(getDataFolder(), "config.yml");
        try {
            if (getDataFolder().mkdir() | config_file.createNewFile()) {
                getLogger().info("Creating config.yml!");
            } else {
                getLogger().info("Founded config.yml, skip create!");
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(config_file);
            config.addDefault("sync-time-enable", true);
            config.addDefault("sync-time-sec", 10);
            config.addDefault("no-weather-change", true);
            config.options().copyDefaults(true);
            config.save(config_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (getConfig().getBoolean("sync-time-enable")) {
            Time(getConfig().getInt("sync-time-sec") * 20);
        }
        Bukkit.getPluginManager().registerEvents(new WeatherChange(), this);
    }

    @Override
    public void onDisable() {
        //插件卸载时
        getLogger().info("Realtime disabled!");
    }

    public void Time(int time) {
        new BukkitRunnable() {
            @Override
            public void run() {
                GregorianCalendar calender = new GregorianCalendar();
                List<World> worldList = Bukkit.getWorlds();
                int timeTick = ((calender.get(Calendar.HOUR_OF_DAY) - 6 ) * 1000) + (calender.get(Calendar.MINUTE) * 16);
                if (timeTick < 0) {
                    int time = 24000 + timeTick;
                    for (World w : worldList) {
                        w.setTime(time);
                    }
                } else {
                    for (World w : worldList) {
                        w.setTime(timeTick);
                    }
                }
            }
        }.runTaskTimer(this, 0, time);
    }
}
