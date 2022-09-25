package me.dodeedoo.magicpit.skills.list;

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
import particles.LocationLib;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FieryStomp implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();
        for (Location loc : LocationLib.getCircle(new Location[]{location}, 5, 3)) {
            if (Math.random() < 0.5) ParticleEffect.SMOKE_LARGE.display(loc);
            if (Math.random() < 0.2) ParticleEffect.FLAME.display(loc);
            if (Math.random() < 0.7) ParticleEffect.BLOCK_CRACK.display(loc);
        }
        for (Entity entity : location.getNearbyEntities(5, 5, 5)) {
            if (entity instanceof LivingEntity && entity != player) {
                Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) entity, MagicDamageType.FIRE, 15));
            }
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Stomps in 5 block radius");
        lore.add("&7Deals &c15 Fire Damage");
        lore.add("&7Cost: &b50 Mana");
        lore.add("&7Cooldown: 5 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.SNEAK;
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
        return 5L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&6FieryStomp", 0);
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
