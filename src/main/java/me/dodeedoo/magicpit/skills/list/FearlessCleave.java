package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamage;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamageType;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FearlessCleave implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    public static Location[] getArc(Double distanceAway, Double distanceSide, Player player) {
        Random random = new Random();
        double num = 0;

        Location location = player.getEyeLocation().add(player.getLocation().getDirection().multiply(distanceAway));
        Location right = LocationLib.getRightSide(player.getEyeLocation(), distanceSide);
        Location left = LocationLib.getLeftSide(player.getEyeLocation(), distanceSide);
        num = random.nextDouble();
        if (random.nextBoolean()) {
            num *= -1.25;
        }

        right.add(0, num, 0);

        num = random.nextDouble();
        if (random.nextBoolean()) {
            num *= -1;
        }

        left.add(0, num, 0);

        return LocationLib.getLine(new Location[]{right, left}, location, 5);
    }

    public static void cleave(Player player, Integer index, Integer damage) {
        Location location = player.getEyeLocation().add(player.getLocation().getDirection().multiply(2D));
        Location[] locs = getArc(2D, 2D, player);

        for (Location loc : locs) {
            new ParticleBuilder(Particle.REDSTONE).color(Color.WHITE).location(loc).spawn();
            if (Math.random() < 0.1) new ParticleBuilder(Particle.SWEEP_ATTACK).location(loc).spawn();
        }

        locs = getArc(2.25, 2.15, player);

        for (Location loc : locs) {
            new ParticleBuilder(Particle.REDSTONE).color(Color.AQUA).location(loc).spawn();
            if (Math.random() < 0.1) new ParticleBuilder(Particle.SWEEP_ATTACK).location(loc).spawn();
        }
        if (index > 1) {
            damage = (int) (damage * 1.2);
            locs = getArc(2.25, 2.15, player);

            for (Location loc : locs) {
                new ParticleBuilder(Particle.REDSTONE).color(Color.TEAL).location(loc).spawn();
                if (Math.random() < 0.05) new ParticleBuilder(Particle.SWEEP_ATTACK).location(loc).spawn();
            }

            if (index > 2) {
                damage = (int) (damage * 1.2);
                locs = getArc(2.5, 2.3, player);

                for (Location loc : locs) {
                    new ParticleBuilder(Particle.REDSTONE).color(Color.BLUE).location(loc).spawn();
                    if (Math.random() < 0.1) new ParticleBuilder(Particle.SWEEP_ATTACK).location(loc).spawn();
                }
            }
        }
        for (Entity victim : location.getNearbyEntities(1.5, 1.5, 1.5)) {
            if (victim instanceof LivingEntity && victim != player) {
                Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) victim, MagicDamageType.ICE, damage));
            }
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        Integer damage = (((int) AttributesHandler.Attributes.get("Defense").getPlayer(player)) *
                                ((int) AttributesHandler.Attributes.get("Crit").getPlayer(player) / 15));
        cleave(player, 1, damage);


        new BukkitRunnable() {
            @Override
            public void run() {
                cleave(player, 2, damage);

            }
        }.runTaskLater(MagicPitCore.getInstance(), 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                cleave(player, 3, damage);

            }

        }.runTaskLater(MagicPitCore.getInstance(), 45);

        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Clone your blade as &bMana");
        lore.add("&7Launch 3 cleaving attacks");
        lore.add("&7Each Cleave does damage based on defense&crit");
        lore.add("&7Each consecutive cleave does 20% more dmg");
        lore.add("&7Cost: &b150 Mana");
        lore.add("&7Cooldown: 12 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.SNEAK_LEFT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 150;
    }

    @Override
    public Long getCooldown() {
        return 12L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&bFearless Cleave", 0);
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
