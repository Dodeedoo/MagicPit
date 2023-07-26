package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import me.dodeedoo.magicpit.social.party.PartySystem;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Disengage implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location front = player.getLocation().add(player.getLocation().getDirection().normalize().multiply(2));
        for (Entity entity : front.getNearbyEntities(1.5, 1.5 ,1.5)) {
            if (entity instanceof LivingEntity) {
                if (!(entity instanceof Player && entity != player && PartySystem.sameParty((Player) entity, player))) {
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 3));
                    ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 1));
                }
            }
        }

        Vector unitVector = new Vector(player.getLocation().getDirection().getX(), 0, player.getLocation().getDirection().getZ());
        unitVector = unitVector.normalize();
        player.setVelocity(unitVector.multiply(-1.75));

        for (Location loc : LocationLib.getHelix(new Location[]{player.getLocation()}, 1, 2, 1, 2)) {
            new ParticleBuilder(Particle.SMOKE_LARGE).location(loc).spawn();
        }

        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Jump backwards away from where");
        lore.add("&7you are facing, slowing and blinding");
        lore.add("&7any enemies in front of you.");
        lore.add("&7+5 Swiftness for 1 second");
        lore.add("&7Cost: &b40 Mana");
        lore.add("&7Cooldown: 5 seconds");
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
        return 40;
    }

    @Override
    public Long getCooldown() {
        return 5L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&fDisengage", 0);
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
