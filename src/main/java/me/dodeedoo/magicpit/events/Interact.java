package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.skills.SkillHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent event) {
        SkillHandler.handleClick(event);
    }

}
