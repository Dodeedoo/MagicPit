package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Mana implements Attribute {

    public String name = "Mana";
    public String colorcode = "&b";
    public String icon = "";
    public HashMap<Player, Object> playerManaMap = new HashMap<>();
    public Integer defaultval = 200;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerManaMap = map;
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

    //mana regeneration
    @Override
    public void secondModifier(Player player) {
        Attribute maxmana = AttributesHandler.Attributes.get("Maxmana");
        Integer regen = (Integer) maxmana.getPlayer(player) / 15;
        if ((Integer) getPlayer(player) + regen > (Integer) maxmana.getPlayer(player)) {
            playerManaMap.put(player, maxmana.getPlayer(player));
        }else{
            playerManaMap.put(player, (Integer) getPlayer(player) + regen);
        }
    }

    @Override
    public ModifierPriority getPriority() {
        return null;
    }

    @Override
    public void threeSecondModifier(Player player) {

    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerManaMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerManaMap.get(player);
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultval;
    }
}
