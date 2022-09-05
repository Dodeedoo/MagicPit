package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Knowledge implements Attribute {

    public String name = "Knowledge";
    public String colorcode = "&5";
    public String icon = "";
    public HashMap<Player, Object> playerKnowledgeMap = new HashMap<>();
    public Integer defaultval = 10;

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerKnowledgeMap = map;
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
        return this.playerKnowledgeMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerKnowledgeMap.get(player);
    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getDefaultValue() {

        return this.defaultval;
    }
}
