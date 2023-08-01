package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import me.dodeedoo.magicpit.social.party.PartySystem;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WellOfLife implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        for (int i = 0; i < 6; i++) {
            Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {

                for (int o = 1; o < 11; o++) {
                    for (Location loc : LocationLib.getHelix(new Location[]{player.getLocation()}, o, 3, 2, 3)) {
                        if (Math.random() > 0.9) {
                            if (Math.random() > 0.5) {
                                new ParticleBuilder(Particle.REDSTONE).color(Color.GREEN).location(loc).spawn();
                            }else{
                                new ParticleBuilder(Particle.REDSTONE).color(Color.LIME).location(loc).spawn();
                            }

                            if (Math.random() > 0.9) {
                                new ParticleBuilder(Particle.VILLAGER_HAPPY).location(loc).spawn();
                            }
                        }
                    }
                }

                for (Entity entity : player.getLocation().getNearbyEntities(10, 4, 10)) {
                    if (entity instanceof Player && PartySystem.sameParty((Player) entity, player)) {
                        ((Player) entity).setHealth(((Player) entity).getHealth() + (((Player) entity).getMaxHealth() * 0.02) + 20);
                        ((Player) entity).sendHealthUpdate();
                    }
                }

            }, i * 20);
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Create a healing aura around you");
        lore.add("&7Heals you and your allies");
        lore.add("&7Radius: 10 blocks");
        lore.add("&7Healing: 2% max hp + 20 / second");
        lore.add("&7Lasts 10 seconds");
        lore.add("&7Cost: &b280 Mana");
        lore.add("&7Cooldown: 20 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.DROP;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 280;
    }

    @Override
    public Long getCooldown() {
        return 20L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&a&lWell of Life", 0);
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
