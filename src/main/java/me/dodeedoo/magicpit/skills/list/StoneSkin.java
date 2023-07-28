package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StoneSkin implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        double add = 50;
        add *= 1 + (1 - (player.getHealth() / player.getMaxHealth()) * 3);

        Attribute attribute = AttributesHandler.Attributes.get("Defense");
        attribute.getPlayerStats().put(player, (int) attribute.getPlayer(player) + add);
        double finalAdd = add;
        Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {attribute.getPlayerStats().put(player, (int) attribute.getPlayer(player) - finalAdd);}, 40);

        for (Location loc : LocationLib.getHelix(new Location[]{player.getLocation()}, 1, 2.5, 3, 2)) {
            new ParticleBuilder(Particle.REDSTONE).color(Color.GRAY).location(loc).spawn();
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7+50 base defense for 2 seconds");
        lore.add("&7added defense is increased based on remaining health");
        lore.add("&7Cost: &b15 Mana");
        lore.add("&7Cooldown: 1 second");
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
        return 15;
    }

    @Override
    public Long getCooldown() {
        return 1L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&2Stone Skin", 0);
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
