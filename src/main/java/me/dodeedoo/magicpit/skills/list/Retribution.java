package me.dodeedoo.magicpit.skills.list;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamage;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamageType;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import me.dodeedoo.magicpit.social.party.PartySystem;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import particles.LocationLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Retribution implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Vector direction = player.getEyeLocation().getDirection().multiply(0.2);

        Location spiral2Loc = player.getEyeLocation();
        Location spiral1Loc = player.getEyeLocation();


        //right/up = true, left/down = false ; vice versa for other spiral
        boolean rightOrLeft = true;

        //each iteration is 1 block
        for (int i = 1; i <= 6; i++) {

            //toggle
            rightOrLeft = !rightOrLeft;

            //each iteration is 0.2 blocks
            for (int o = 0; o <= 5; o++) {
                if (rightOrLeft) {
                    spiral1Loc = LocationLib.getRightSide(spiral1Loc.add(direction), 0.2).add(0, 0.3, 0);
                    spiral2Loc = LocationLib.getLeftSide(spiral2Loc.add(direction), 0.2).add(0, -0.3, 0);
                }else{
                    spiral1Loc = LocationLib.getLeftSide(spiral1Loc.add(direction), 0.2).add(0, -0.3, 0);
                    spiral2Loc = LocationLib.getRightSide(spiral2Loc.add(direction), 0.2).add(0, 0.3, 0);
                }

                Location finalSpiral1Loc = spiral1Loc;
                Location finalSpiral2Loc = spiral2Loc;
                Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {
                    for (Location loc : LocationLib.getSphere(new Location[]{finalSpiral1Loc}, 0.1, 2)) {
                        new ParticleBuilder(Particle.REDSTONE).color(Color.YELLOW).location(loc).spawn();
                    }
                    for (Location loc : LocationLib.getSphere(new Location[]{finalSpiral2Loc}, 0.1, 2)) {
                        new ParticleBuilder(Particle.REDSTONE).color(Color.ORANGE).location(loc).spawn();
                    }

                    for (Object iter : ArrayUtils.addAll(finalSpiral1Loc.getNearbyEntities(0.25, 0.25, 0.25).toArray(), finalSpiral1Loc.getNearbyEntities(0.25, 0.25, 0.25).toArray())) {
                        Entity entity = (Entity) iter;
                        double damage = (Integer) AttributesHandler.Attributes.get("Threat").getPlayer(player);
                        damage *= 1 + (1 - (player.getHealth() / player.getMaxHealth()));

                        if (entity instanceof Player && entity != player && !PartySystem.sameParty(player, (Player) entity)) {
                            Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) entity, MagicDamageType.ICE, (int) damage));
                        }else if (entity instanceof LivingEntity) {
                            Bukkit.getPluginManager().callEvent(new MagicDamage(player, (LivingEntity) entity, MagicDamageType.ICE, (int) damage));
                        }
                    }
                }, (i / 2) * 20);
            }

        }

    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Send a Vortex of Energy ahead of you");
        lore.add("&7Base damage depends on Threat level");
        lore.add("&7Damage increased based on how low your health is");
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
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&e&lRetribution", 0);
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
