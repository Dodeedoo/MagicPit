package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Crit implements Attribute {

    public String name = "Critical Hit%";
    public String colorcode = "&9";
    public String icon = "";
    public HashMap<Player, Integer> playerCritMap = new HashMap<>();

    @Override
    public HashMap<Player, Object> getPlayerStats(Player player) {
        return null;
    }

    @Override
    public void deathModifier(EntityDeathEvent event) {

    }

    @Override
    public void attackModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public void damagedModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public void secondModifier() {

    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }
}
