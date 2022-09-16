package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EggBall implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        player.launchProjectile(Egg.class, player.getLocation().getDirection());
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
