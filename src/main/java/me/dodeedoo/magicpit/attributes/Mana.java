package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Mana implements Attribute {

    public String name = "Mana";
    public String colorcode = "&b";
    public String icon = "";
    public HashMap<Player, Integer> playerManaMap = new HashMap<>();

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
