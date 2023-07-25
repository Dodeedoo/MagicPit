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

public class HolyField implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        for (int i = 0; i < 6; i++) {
            Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {

                for (Location loc : LocationLib.getHelix(new Location[]{player.getLocation()}, 7, 4, 3, 10)) {
                    if (Math.random() > 0.8) new ParticleBuilder(Particle.REDSTONE).color(Color.YELLOW).location(loc).spawn();
                }

                for (Entity entity : player.getLocation().getNearbyEntities(7, 4, 7)) {
                    if (entity instanceof Player && PartySystem.sameParty((Player) entity, player)) {
                        Attribute attribute = AttributesHandler.Attributes.get("Curse");
                        if ((int) attribute.getPlayer((Player) entity) != 0) {
                            attribute.getPlayerStats().put((Player) entity, ((int) attribute.getPlayer((Player) entity)) - 5);
                        }
                        attribute = AttributesHandler.Attributes.get("MagicDefense");
                        attribute.getPlayerStats().put((Player) entity, ((int) attribute.getPlayer((Player) entity)) + 15);
                        attribute = AttributesHandler.Attributes.get("Defense");
                        attribute.getPlayerStats().put((Player) entity, ((int) attribute.getPlayer((Player) entity)) + 50);

                        Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {
                            Attribute attribute2 = AttributesHandler.Attributes.get("MagicDefense");
                            attribute2.getPlayerStats().put((Player) entity, ((int) attribute2.getPlayer((Player) entity)) - 15);
                            attribute2 = AttributesHandler.Attributes.get("Defense");
                            attribute2.getPlayerStats().put((Player) entity, ((int) attribute2.getPlayer((Player) entity)) - 50);
                        }, 20);
                    }
                }

            }, i * 20);
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Generate a holy field around you for 5s");
        lore.add("&7Range: 7 blocks");
        lore.add("&7While in the field, party members gain:");
        lore.add("&7+15 Magical Defense");
        lore.add("&7+50 Defense");
        lore.add("&7+20 Max mana");
        lore.add("&7-5 Curse");
        lore.add("&7Cost: &b50 Mana");
        lore.add("&7Cooldown: 10 seconds");
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
        return 10L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&eHoly Field", 0);
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
