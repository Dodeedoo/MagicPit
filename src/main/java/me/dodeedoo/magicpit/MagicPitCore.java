package me.dodeedoo.magicpit;

import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.attributes.Strength;
import me.dodeedoo.magicpit.commands.setStrength;
import me.dodeedoo.magicpit.events.Connection;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicPitCore extends JavaPlugin {

    private static MagicPitCore instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register Attributes
        AttributesHandler.addAttribute(new Strength(), "Strength");

        //Register Commands
        this.getCommand("setStrength").setExecutor(new setStrength());

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(new Connection(), this);

        //Periodical Loops
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Attribute strength = AttributesHandler.Attributes.get("Strength");
                player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Util.colorize("&4❀Strength " + strength.getPlayer(player).toString())));
            }
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
