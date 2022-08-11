package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class MaxMana implements Attribute {

    public String name = "Max Mana";
    public String colorcode = "&b";
    public String icon = "";
    public HashMap<Player, Object> playerMaxManaMap = new HashMap<>();
    public Integer defaultval = 200;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerMaxManaMap = map;
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
        return this.playerMaxManaMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerMaxManaMap.get(player);
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultval;
    }
}
