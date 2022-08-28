package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public interface Attribute {
    HashMap<Player, Object> getPlayerStats();
    void setPlayerStat(HashMap<Player, Object> map);
    void deathModifier(EntityDeathEvent event);
    void attackModifier(EntityDamageByEntityEvent event);
    void damagedModifier(EntityDamageByEntityEvent event);
    void secondModifier(Player player);
    void threeSecondModifier(Player player);
    void killModifier(EntityDeathEvent event);
    Object getPlayer(Player player);
    Object getDefaultValue();
}
