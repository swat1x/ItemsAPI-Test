package ru.swat1x.itemsapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.swat1x.itemsapi.bukkit.handler.UseListener;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Слушатели типо
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new UseListener(), this);

        // Тестирование как API
        getCommand("customitemstestcommand").setExecutor(new Test.Command());
        pm.registerEvents(new Test.Handler(), this);

        new Test.BookAnimation().runTaskTimer(this, 0, 1);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}