package me.dodeedoo.magicpit.attributes;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class Swiftness implements Attribute {

    public String name = "Swiftness";

    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.LOW;
    }

    public String colorcode = "&f";
    public String icon = "";
    public HashMap<Player, Object> playerSwiftnessMap = new HashMap<>();
    public Integer defaultval = 0;
    public HashMap<Player, BossBar> bar = new HashMap<>();

    @Override
    public void deathModifier(EntityDeathEvent event) {

    }

    @Override
    public void attackModifier(EntityDamageByEntityEvent event) {

    }

    @Override
    public void damagedModifier(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getEntity();
        if (Math.random() < ((double) (int) playerSwiftnessMap.get(player) / 100)) {
            if (bar.containsKey(player)) bar.get(player).removeAll();
            BossBar display = Bukkit.createBossBar(Util.colorize("&f&lDODGED ATTACK"), BarColor.WHITE, BarStyle.SEGMENTED_6);
            bar.put(player, display);
            bar.get(player).addPlayer(player);
            Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {
                bar.get(player).removeAll();
            }, 20);
            event.setCancelled(true);
        }
    }

    @Override
    public void secondModifier(Player player) {

    }

    @Override
    public void threeSecondModifier(Player player) {

    }

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return this.playerSwiftnessMap;
    }

    @Override
    public Object getPlayer(Player player) {
        return this.playerSwiftnessMap.get(player);
    }


    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public void setPlayerStat(HashMap<Player, Object> map) {
        this.playerSwiftnessMap = map;
    }

    @Override
    public Integer getDefaultValue() {
        return this.defaultval;
    }

}