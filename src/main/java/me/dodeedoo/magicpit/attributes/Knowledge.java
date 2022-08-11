package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Knowledge implements Attribute {

    public String name = "Knowledge";
    public String colorcode = "&5";
    public String icon = "";
    public HashMap<Player, Integer> playerKnowledgeMap = new HashMap<>();

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {

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
    public Object getDefaultValue() {

        return null;
    }
}
