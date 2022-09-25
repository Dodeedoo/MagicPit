package me.dodeedoo.magicpit.skills.list;

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

public class FieryStrike implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location eyes = player.getLocation();
        Vector direction = player.getLocation().getDirection(); // player's direction, a Vector
        eyes.add(direction);
        for (Location loc : LocationLib.getHelix(new Location[]{eyes}, 2, 2, 1, 3)) {
            ParticleEffect.FLAME.display(loc);
        }
        for (Entity entity : eyes.getNearbyEntities(4, 2, 4)) {
            if (entity instanceof LivingEntity) {
                if (entity != player)
                    Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) entity, MagicDamageType.FIRE, ((int) AttributesHandler.Attributes.get("Strength").getPlayer(player) / 3)));
            }
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Swing with fury from your own blood");
        lore.add("&7deals &cStrength / 3 Fire Damage");
        lore.add("&7Cost: &45 hp");
        lore.add("&7Cooldown: 3 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.RIGHT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.HEALTH;
    }

    @Override
    public Integer getCostAmount() {
        return 5;
    }

    @Override
    public Long getCooldown() {
        return 3L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&6Fiery Strike", 0);
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
