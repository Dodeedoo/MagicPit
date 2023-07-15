package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import particles.LocationLib;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.ParticleColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Corruption implements Skill {
    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        final int[] in = {0};
        final int[] step = {0};
        new BukkitRunnable() {

            @Override
            public void run() {
                in[0]++;
                if (in[0] == 20) {
                    cancel();
                }
                Location location = player.getLocation().clone();
                location.setZ(location.getZ() - 2.5);
                location.setX(location.getX() + 1);
                for (int i = 45; i <= 360; i += 45) {
                    double radians = Math.toRadians(i);
                    double rotatedX = ((Math.cos(radians)) + (Math.sin(radians)));
                    double rotatedZ = ((Math.sin(radians)) - (Math.cos(radians)));

                    //Bukkit.broadcastMessage(rotatedX + " " + rotatedZ);

                    location.setX(rotatedX + location.getX());
                    location.setZ(rotatedZ + location.getZ());

                    ParticleEffect.FLAME.display(location);
//                    Location[] showLoc = LocationLib.getSphere(new Location[]{location}, 1, 2);
//
//                    for (Location loc : showLoc) {
//                        ParticleEffect.SOUL_FIRE_FLAME.display(loc);
//                    }

                    step[0] = step[0] + 10;
                }

                Location[] locs = LocationLib.getHelix(new Location[]{player.getLocation()}, 1, player.getHeight(), 1, 2);
                for (Location loc : locs) {
                    if (Math.random() > 0.75) new ParticleBuilder(ParticleEffect.REDSTONE, loc).setColor(Color.BLACK).display();
                }


            }
        }.runTaskTimer(MagicPitCore.getInstance(), 5, 5);
        Attribute attribute = AttributesHandler.Attributes.get("Weight");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 30);
        attribute = AttributesHandler.Attributes.get("Strength");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 400);
        attribute = AttributesHandler.Attributes.get("Crit");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 25);
        attribute = AttributesHandler.Attributes.get("Regeneration");
        attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 120);
        new BukkitRunnable() {
            @Override
            public void run() {
                Attribute attribute = AttributesHandler.Attributes.get("Weight");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 30);
                attribute = AttributesHandler.Attributes.get("Strength");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 400);
                attribute = AttributesHandler.Attributes.get("Crit");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 25);
                attribute = AttributesHandler.Attributes.get("Regeneration");
                attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 120);
            }
        }.runTaskLater(MagicPitCore.getInstance(), 100);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&4Embrace Corruption for a short time");
        lore.add("&7-30 Weight");
        lore.add("&7+400 Strength");
        lore.add("&7+25 Crit");
        lore.add("&7+120 Regeneration");
        lore.add("&7Cost: &b100 Mana");
        lore.add("&7Cooldown: 25 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.SNEAK_LEFT_CLICK;
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
        return 25L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&4Corruption", 0);
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
