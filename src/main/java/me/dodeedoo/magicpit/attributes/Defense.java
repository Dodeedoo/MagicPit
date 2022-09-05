package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Defense implements Attribute {

    public String name = "Defense";
    public String colorcode = "&2";
    public String icon = "";
    public HashMap<Player, Object> playerDefenseMap = new HashMap<>();
    public Integer defaultval = 50;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerDefenseMap = map;
    }

    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.HIGH;
    }

    @Override
    public void deathModifier(EntityDeathEvent event) {

    }

    @Override
    public void attackModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public void damagedModifier(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() - ((Float) playerDefenseMap.get((Player) event.getEntity()) / 5));
    }

    @Override
    public void secondModifier(Player player) {

    }

    @Override
    public void threeSecondModifier(Player player) {

    }

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerDefenseMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerDefenseMap.get(player);
    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getDefaultValue() {

        return this.defaultval;
    }
}
