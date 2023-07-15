package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
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
import org.bukkit.util.Vector;
import particles.LocationLib;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WindStrike implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location start = player.getLocation().add(0, 0.75, 0);
        final Location[] location = {start.clone()};
        Vector direction = location[0].getDirection().normalize();
        for (int i = 0; i < 10; i++) {
            Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {
                location[0] = location[0].add(direction);
                for (Location loc : LocationLib.getHelix(new Location[]{location[0]}, 1, 1, 1, 1)) {
                    if (Math.random() > 0.2) {
                        new ParticleBuilder(Particle.SWEEP_ATTACK).location(loc).spawn();
                    }
                    new ParticleBuilder(Particle.REDSTONE).color(Color.WHITE).location(loc).spawn();
                    new ParticleBuilder(Particle.WHITE_ASH).location(loc).spawn();
                }
                for (Entity entity : location[0].getNearbyEntities(1.5, 1.5, 1.5)) {
                    if (entity instanceof LivingEntity && entity != player) Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) entity, MagicDamageType.ARCANE, 100));
                }
            }, i * 4);
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Harness Wind and Mana to send");
        lore.add("&7razor sharp wind strikes forward");
        lore.add("&7Cost: &b40 Mana");
        lore.add("&7Cooldown: 4 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.LEFT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 40;
    }

    @Override
    public Long getCooldown() {
        return 4L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&fWind Strike", 0);
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
        return null;
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
