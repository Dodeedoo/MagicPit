package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Flash implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        List<Player> getFlashed = new ArrayList<>();
        for (Entity entity : player.getLocation().getNearbyEntities(10, 10, 10)) {
            if (entity instanceof Player && entity != player) {
                getFlashed.add((Player) entity);
                ParticleEffect.SMOKE_LARGE.display(((Player) entity).getEyeLocation());
            }
        }
        for (int i = 0; i < 10; i++) {
            ParticleEffect.FLASH.display(player.getLocation(), getFlashed);
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Bad for the eyes");
        lore.add("&7When you are hit, flash players in radius of 10");
        lore.add("&7Cost: &b20 Mana");
        lore.add("&7Cooldown: 3 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.DAMAGED;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 20;
    }

    @Override
    public Long getCooldown() {
        return 3L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&eFlash", 0);
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
