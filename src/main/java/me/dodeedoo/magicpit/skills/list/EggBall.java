package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.PitPlayer;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import particles.LocationLib;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EggBall implements Skill, Serializable {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Egg firstegg = player.launchProjectile(Egg.class, player.getLocation().getDirection());
        for (Location location : LocationLib.getSphere(new Location[]{player.getLocation()},2, 3)) {
            Egg egg = player.getWorld().spawn(location, Egg.class);
            egg.setVelocity(firstegg.getVelocity());
            egg.setShooter(player);
        }
        PitPlayer.playerMap.get(player).setExp(PitPlayer.playerMap.get(player).exp + 25);
        initiateCooldown(player);
    }

    @Override
    public List<String> getLore() {
        return null;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.LEFT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.HEALTH;
    }

    @Override
    public Integer getCostAmount() {
        return 3;
    }

    @Override
    public Long getCooldown() {
        return 4L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&fEgg Ball", 0);
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
