package me.dodeedoo.magicpit.attributes;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import particles.LocationLib;

import java.util.HashMap;

public class Curse implements Attribute {

    public HashMap<Player, Object> playerCurseMap = new HashMap<>();
    public HashMap<Player, BossBar> bar = new HashMap<>();

    @Override
    public HashMap<Player, Object> getPlayerStats() {
        return playerCurseMap;
    }

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
    public void secondModifier(Player player) {
        if ((int) getPlayer(player) != 0) {
            playerCurseMap.put(player, (int) getPlayer(player) - 1);
        }
        if ((int) getPlayer(player) != 0) {
            Location location = player.getLocation();
            for (Location loc : LocationLib.getHelix(new Location[]{location}, 0.2, 2.3, 1, 7)) {
                if (Math.random() < 0.2) new ParticleBuilder(Particle.SPELL).location(loc).spawn();
            }
            if (bar.containsKey(player)) bar.get(player).removeAll();
            String n;
            BarColor color = BarColor.PURPLE;
            if ((int) getPlayer(player) > 24) {
                n = "&5&lCursed " + playerCurseMap.get(player) + "%";
                color = BarColor.RED;
            }else{
                n = "&5Cursed " + playerCurseMap.get(player) + "%";
            }
            BossBar display = Bukkit.createBossBar(Util.colorize(n), color, BarStyle.SEGMENTED_6);
            bar.put(player, display);
            bar.get(player).addPlayer(player);
        }else{
            if (bar.containsKey(player)) {
                bar.get(player).removeAll();
                bar.remove(player);
            }
        }
    }

    @Override
    public void threeSecondModifier(Player player) {

    }

    @Override
    public void killModifier(EntityDeathEvent event) {

    }

    @Override
    public Object getPlayer(Player player) {
        return playerCurseMap.get(player);
    }

    @Override
    public Object getDefaultValue() {
        return 0;
    }

    @Override
    public ModifierPriority getPriority() {
        return ModifierPriority.HIGH;
    }
}
