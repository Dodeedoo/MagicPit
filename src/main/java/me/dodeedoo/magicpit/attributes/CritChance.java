package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class CritChance implements Attribute {

    public String name = "Critical Hit Chance";
    public String colorcode = "&3";
    public String icon = "";
    public HashMap<Player, Object> playerCritChanceMap = new HashMap<>();
    public Integer defaultval = 10;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerCritChanceMap = map;
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

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerCritChanceMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerCritChanceMap.get(player);
    }


    @Override
    public Object getDefaultValue() {

        return this.defaultval;
    }
}
