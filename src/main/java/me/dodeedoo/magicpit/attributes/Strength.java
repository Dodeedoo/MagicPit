package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Strength implements Attribute {

    public String name = "Strength";
    public String colorcode = "&4";
    public String icon = "❀";

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerStrengthMap = map;
    }

    public HashMap<Player, Object> playerStrengthMap = new HashMap<>();
    public Integer defaultval = 0;


    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerStrengthMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerStrengthMap.get(player);
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
    public Object getDefaultValue() {
        return this.defaultval;
    }

}