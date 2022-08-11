package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Crit implements Attribute {

    public String name = "Critical Hit%";
    public String colorcode = "&9";
    public String icon = "";
    public HashMap<Player, Object> playerCritMap = new HashMap<>();
    public Integer defaultval = 200;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerCritMap = map;
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
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerCritMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerCritMap.get(player);
    }


    @Override
    public void secondModifier() {

    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getDefaultValue() {

        return this.defaultval;
    }
}