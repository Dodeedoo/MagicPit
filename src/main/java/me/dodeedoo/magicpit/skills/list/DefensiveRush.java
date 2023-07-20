package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DefensiveRush implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Vector unitVector = new Vector(player.getLocation().getDirection().getX(), 0, player.getLocation().getDirection().getZ());
        unitVector = unitVector.normalize();
        player.setVelocity(unitVector.multiply(4.5));
        new BukkitRunnable() {

            @Override
            public void run() {
                Location[] locs = LocationLib.getSphere(new Location[]{player.getLocation()}, 3.5, 5);
                for (Location loc : locs) {
                    if (Math.random() < 0.5) new ParticleBuilder(Particle.REDSTONE).color(Color.AQUA).location(loc).spawn();
                }
            }
        }.runTaskLater(MagicPitCore.getInstance(), 10);

        Attribute attribute = AttributesHandler.Attributes.get("Defense");
        int defenseBefore = ((int) attribute.getPlayer(player));
        int defenseAfter = (int) (defenseBefore * 1.15);
        attribute.getPlayerStats().put(player, defenseAfter);

        new BukkitRunnable() {

            @Override
            public void run() {
                int gainedSince = (int) attribute.getPlayer(player) - defenseAfter;
                attribute.getPlayerStats().put(player, defenseBefore + gainedSince);
            }
        }.runTaskLater(MagicPitCore.getInstance(), 60);

    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Rush Forwards");
        lore.add("&7Gain +15% defense for 3s");
        lore.add("&7Cost: &b50 Mana");
        lore.add("&7Cooldown: 8 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.SNEAK_RIGHT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 50;
    }

    @Override
    public Long getCooldown() {
        return 8L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&3Defensive Rush", 0);
    }

    @Override
    public HashMap<Player, Long> getCooldownMap() {
        return cooldownmap;
    }

    @Override
    public void setCooldownMap(HashMap<Player, Long> map) {
        cooldownmap = map;
    }

    @Override
    public Boolean overTime() {
        return false;
    }

    @Override
    public HashMap<Player, Long> getHeldTicksMap() {
        return null;
    }

    @Override
    public void initiateCooldown(Player player) {
        HashMap<Player, Long> cdmap = getCooldownMap();
        cdmap.put(player, TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()));
        setCooldownMap(cdmap);
    }
}
