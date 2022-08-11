package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Connection implements Listener {

    @EventHandler
    public void Playerjoin(PlayerJoinEvent event) {
        Util.handlePlayerAttributes(event.getPlayer());
        Util.updatePlayerAttributes(event.getPlayer());
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event) {
        for (Attribute attribute : AttributesHandler.Attributes.values()) {
            attribute.getPlayerStats().remove(event.getPlayer());
        }
    }


}
