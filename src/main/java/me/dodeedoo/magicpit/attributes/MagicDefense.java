package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class MagicDefense implements Attribute {
    public String name = "Magic Defense";
    public String colorcode = "&5";
    public String icon = "";
    public HashMap<Player, Object> playerMagicDefenseMap = new HashMap<>();
    public Integer defaultval = 5;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerMagicDefenseMap = map;
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

    }

    @Override
    public void secondModifier(Player player) {

    }

    @Override
    public void threeSecondModifier(Player player) {

    }

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerMagicDefenseMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerMagicDefenseMap.get(player);
    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getDefaultValue() {

        return this.defaultval;
    }
}
