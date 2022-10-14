package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

public class Interact implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent event) {
        SkillHandler.handleClick(event);
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent event) {
        SkillHandler.handleAction(SkillExecuteAction.SNEAK, event.getPlayer());
    }

    @EventHandler
    public void lead(EntityDeathEvent event) {
        for (Entity entity : event.getEntity().getLocation().getNearbyEntities(5, 5, 5)) {
            if (entity instanceof Item) {
                if (((Item) entity).getItemStack().getType().equals(Material.LEAD)) {
                    entity.remove();
                }
            }
        }
    }

}
