package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.naming.ldap.SortKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ParalyzingDart implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location location = player.getLocation();
        location.setYaw(location.getYaw() - 6);
        Projectile arrow;
        for (int i = 0; i < 6; i++) {
            location.setYaw(location.getYaw() + i);
            arrow = player.launchProjectile(Arrow.class, location.getDirection().multiply(1.25));
            ((Arrow) arrow).addCustomEffect(PotionType.SLOWNESS.getEffectType().createEffect(60, 3), true);
            ((Arrow) arrow).addCustomEffect(PotionEffectType.BLINDNESS.createEffect(20, 1), true);
            ((Arrow) arrow).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            arrow.setTicksLived(10);
            Projectile finalArrow = arrow;
            new BukkitRunnable() {

                @Override
                public void run() {
                    finalArrow.remove();
                }
            }.runTaskLater(MagicPitCore.getInstance(), 20);
            new BukkitRunnable() {

                @Override
                public void run() {
                    new ParticleBuilder(Particle.SOUL_FIRE_FLAME).location(finalArrow.getLocation()).spawn();
                    if (finalArrow.isDead()) {
                        cancel();
                    }
                }
            }.runTaskTimer(MagicPitCore.getInstance(), 5, 5);
        }
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Throw 6 paralyzing darts");
        lore.add("&7darts apply slowness 3 and blindess");
        lore.add("&7Cost: &b100 Mana");
        lore.add("&7Cooldown: 10 seconds");
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
        return 100;
    }

    @Override
    public Long getCooldown() {
        return 10L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&2Paralyzing Dart", 0);
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
