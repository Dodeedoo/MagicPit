package me.dodeedoo.magicpit;

import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.attributes.Strength;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicPitCore extends JavaPlugin {

    private static MagicPitCore instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register Attributes
        AttributesHandler.addAttribute(new Strength(), "Strength");

        //Register Commands


        //Register Listeners


        //Periodical Loops
        Bukkit.getScheduler().runTaskTimer(this, () -> {

        }, 20, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static MagicPitCore getInstance() {
        return instance;
    }
}
