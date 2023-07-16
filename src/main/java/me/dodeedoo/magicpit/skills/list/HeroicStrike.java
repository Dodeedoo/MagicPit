package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HeroicStrike implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getEyeLocation().add(player.getLocation().getDirection().multiply(2));
        Vector forwardDir = player.getEyeLocation().getDirection();
        Vector toLeft = forwardDir.crossProduct(new Vector(0, 1, 0));
        Location leftLoc = player.getEyeLocation().add(toLeft).add(0, 1, 0);
        Vector toRight = forwardDir.crossProduct(new Vector(0, -1, 0));
        Location rightLoc = player.getEyeLocation().add(toRight).add(0, -1, 0);
        Location[] locs = LocationLib.getLine(new Location[]{leftLoc}, rightLoc, 2);
        Integer damage = ((((Integer) AttributesHandler.Attributes.get("Defense").getPlayer(player)) * 2) + 100); //defense x2 plus 100
        boolean it = true;
        Color coola;
        for (Location loc : locs) {
            if (it) {
                coola = Color.WHITE;
                it = false;
            }else{
                coola = Color.AQUA;
                it = true;
            }
            new ParticleBuilder(Particle.REDSTONE).color(coola).location(loc).spawn();
        }
        for (Entity victim : location.getNearbyEntities(1.5, 1.5, 1.5)) {
            if (victim instanceof LivingEntity && victim != player) {
                Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) victim, MagicDamageType.ICE, damage));
            }
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&eMagical Slash in front of user");
        lore.add("&7Deals more damage depending on your defense");
        lore.add("&7Cost: &b40 Mana");
        lore.add("&7Cooldown: 2 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.RIGHT_CLICK;
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
        return 2L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&3Heroic Strike", 0);
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
