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
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Brace implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Attribute attribute = AttributesHandler.Attributes.get("MagicDefense");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 100);
        attribute = AttributesHandler.Attributes.get("Defense");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 100);
        attribute = AttributesHandler.Attributes.get("Regeneration");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 5);
        new BukkitRunnable() {
            @Override
            public void run() {
                Attribute attribute = AttributesHandler.Attributes.get("MagicDefense");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 100);
                attribute = AttributesHandler.Attributes.get("Defense");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 100);
                attribute = AttributesHandler.Attributes.get("Regeneration");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 5);
            }
        }.runTaskLater(MagicPitCore.getInstance(), 40);
        for (Location loc : LocationLib.getHelix(new Location[]{player.getLocation()}, 0.5, 1, 1, 2)) {
            new ParticleBuilder(Particle.REDSTONE).color(Color.BLUE).location(loc).spawn();
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7&oBe prepared");
        lore.add("&bBrace &7against incoming attacks");
        lore.add("&7+100 Defense");
        lore.add("&7+100 Magical Defense");
        lore.add("&7+5 Regeneration");
        lore.add("&7Buffs last for 2 seconds");
        lore.add("&7Cost: None");
        lore.add("&7Cooldown: 4 seconds");
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
        return 0;
    }

    @Override
    public Long getCooldown() {
        return 4L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&bBrace", 0);
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
