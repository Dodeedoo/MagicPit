package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Regeneration implements Attribute {

    public String name = "Regeneration";
    public String colorcode = "&d";
    public String icon = "";
    public HashMap<Player, Object> playerRegenerationMap = new HashMap<>();
    public Integer defaultval = 3;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerRegenerationMap = map;
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
        return this.playerRegenerationMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerRegenerationMap.get(player);
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultval;
    }
}
