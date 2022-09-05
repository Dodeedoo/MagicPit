package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Random;

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
        Attribute critchance = AttributesHandler.Attributes.get("Critchance");
        //Float chance = ((Float) critchance.getPlayerStats().get((Player) event.getDamager())) * 0.01F;
        int random = new Random().nextInt(100-1) + 1;
        if (random <= (Integer) critchance.getPlayer((Player) event.getDamager())) {
            event.setDamage(event.getDamage() * ((Integer) getPlayer((Player) event.getDamager()) * 0.01));
        }
    }

    @Override
    public void damagedModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public ModifierPriority getPriority() {
        return null;
    }

    @Override
    public void secondModifier(Player player) {

    }

    @Override
    public void threeSecondModifier(Player player) {

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
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getDefaultValue() {

        return this.defaultval;
    }
}
