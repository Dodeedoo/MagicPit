package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Damage implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            AttributesHandler.handleDamaged(event);
        }
        if (event.getDamager() instanceof Player) {
            AttributesHandler.handleHit(event);
        }
    }

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            AttributesHandler.handleDeath(event);
        }
        if (event.getEntity().getKiller() != null) {
            AttributesHandler.handleKill(event);
        }
    }
}
