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
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LiftingSpirits implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        if (!PartySystem.isInParty(player)) {
            return;
        }
        for (Location loc : LocationLib.getSphere(new Location[]{player.getLocation()}, 10, 1)) {
            new ParticleBuilder(Particle.NOTE).location(loc).spawn();
        }
        int count = 0;
        for (Entity entity : player.getLocation().getNearbyEntities(10, 10, 10)) {
            if (entity instanceof Player && PartySystem.sameParty((Player) entity, player)) {
                if (count >= 3) {
                    break;
                }
                count += 1;
                ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
                Attribute attribute = AttributesHandler.Attributes.get("Swiftness");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 35);
                Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {
                    attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 35);
                }, 100);
            }
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Lift the spirits of everyone in your party");
        lore.add("&7within 10 blocks (max 4 people)");
        lore.add("&7+Speed II for 5 seconds");
        lore.add("&7+35 Swiftness for 5 seconds");
        lore.add("&7Cost: &b250 Mana");
        lore.add("&7Cooldown: 1 minute");
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
        return 250;
    }

    @Override
    public Long getCooldown() {
        return 60L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&f&lLifting Spirits", 0);
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
