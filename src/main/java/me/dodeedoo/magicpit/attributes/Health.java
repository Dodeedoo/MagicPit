package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Health implements Attribute {

    public String name = "Health";
    public String colorcode = "&c";
    public String icon = "";
    public HashMap<Player, Object> playerHealthMap = new HashMap<>();
    public Integer defaultval = 0;

    @Override
    public void deathModifier(EntityDeathEvent event) {

    }

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerHealthMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerHealthMap.get(player);
    }

    @Override
    public void attackModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public void damagedModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerHealthMap = map;
    }

    @Override
    public void secondModifier() {

    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getDefaultValue() {
        return defaultval;
    }
}
