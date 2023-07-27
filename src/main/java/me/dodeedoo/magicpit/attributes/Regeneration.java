package me.dodeedoo.magicpit.attributes;

import me.dodeedoo.magicpit.Util;
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
    public void secondModifier(Player player) {

    }

    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.LOW;
    }

    @Override
    public void threeSecondModifier(Player player) {
        int health = (int) getPlayer(player);
        if ((int) AttributesHandler.Attributes.get("Curse").getPlayer(player) > 0) {
            double curseamount = 1D - ((int) AttributesHandler.Attributes.get("Curse").getPlayer(player) * 0.01D);
            health *= curseamount;
        }
        Util.updateHealth(player, health);
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

    @Override
    public boolean isAddedToPowerLevel() {
        return true;
    }

}
