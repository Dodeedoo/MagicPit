package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Weight implements Attribute {

    public String name = "Weight";
    public String colorcode = "&8";
    public String icon = "";
    public HashMap<Player, Integer> playerWeightMap = new HashMap<>();

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
        return null;
    }

    @Override
    public Object getPlayer(Player player) {
        return null;
    }

    @Override
    public void secondModifier() {

    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {

    }

    @Override
    public Integer getDefaultValue() {
        return null;
    }

}
