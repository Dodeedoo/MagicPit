package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamage;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamageType;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import particles.LocationLib;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FieryOutrage implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();
        for (Entity entity : location.getNearbyEntities(5, 5, 5)) {
            if (entity instanceof LivingEntity && entity != player) {
                Vector unitVector = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                entity.setVelocity(unitVector.multiply(3));
                Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) entity, MagicDamageType.FIRE, 30));
                Util.updateHealth(player, 10);
            }
        }
        for (Location loc : LocationLib.getSphere(new Location[]{location}, 3, 3)) {
            if (Math.random() < 0.2) ParticleEffect.SMOKE_LARGE.display(loc);
            if (Math.random() < 0.4) ParticleEffect.FLASH.display(loc);
            if (Math.random() < 0.5) ParticleEffect.FLAME.display(loc);
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Channel Mana into blood...blood into &6&lFire");
        lore.add("&7Knocks enemies back in a 5 block radius");
        lore.add("&7deals &c30 Fire damage &7heals 10 hp for every enemy hit");
        lore.add("&7Cost: &b120 Mana");
        lore.add("&7Cooldown: 25 seconds");
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
        return 120;
    }

    @Override
    public Long getCooldown() {
        return 25L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&6Fiery Outrage", 0);
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
