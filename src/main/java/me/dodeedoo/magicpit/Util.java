package me.dodeedoo.magicpit;

import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Util {
    public static void updatePlayerAttributes(Player player) {

    }

    public static void handlePlayerAttributes(Player player) {
        for (Attribute attribute : AttributesHandler.Attributes.values()) {
            if (attribute.getPlayer(player) == null) {
                HashMap<Player, Object> map = attribute.getPlayerStats();
                map.put(player, attribute.getDefaultValue());
                attribute.setPlayerStat(map);
            }
        }
    }

    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
