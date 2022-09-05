package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Weight implements Attribute {

    public String name = "Weight";

    @Override
    public ModifierPriority getPriority() {
        return null;
    }

    public String colorcode = "&8";
    public String icon = "";
    public HashMap<Player, Object> playerWeightMap = new HashMap<>();
    public Integer defaultval = 0;

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
        int weight = (int)getPlayer(player);
        if (weight > 30) {
            float diff = (((float)weight) - 30) / 25;
            player.setWalkSpeed(0.55F - diff);
        }else{
            player.setWalkSpeed(0.55F);
        }
    }

    @Override
    public void threeSecondModifier(Player player) {

    }

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerWeightMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerWeightMap.get(player);
    }


    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerWeightMap = map;
    }

    @Override
    public Integer getDefaultValue() {
        return this.defaultval;
    }

}
