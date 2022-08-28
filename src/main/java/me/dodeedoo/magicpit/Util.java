package me.dodeedoo.magicpit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
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

    public static void updateHealth(Player player, Integer health) {
        if ((player.getHealth() + health) > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }else{
            player.setHealth(player.getHealth() + health);
        }
        //credit https://www.spigotmc.org/threads/make-players-hearts-flash-like-when-they-naturally-heal.484328/
        //THANK YOU ESOPHOSE!!!!! you saved me half an hour
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.UPDATE_HEALTH);

        packet.getFloat().write(0, (float) player.getHealth()).write(1, player.getSaturation());
        packet.getIntegers().write(0, player.getFoodLevel());

        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
