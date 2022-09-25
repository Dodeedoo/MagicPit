package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Interact implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent event) {
        SkillHandler.handleClick(event);
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent event) {
        SkillHandler.handleAction(SkillExecuteAction.SNEAK, event.getPlayer());
    }

}
