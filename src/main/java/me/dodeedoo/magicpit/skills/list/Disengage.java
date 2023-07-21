package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Disengage implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {

    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();

        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.DROP;
    }

    @Override
    public SkillCost getCostType() {
        return null;
    }

    @Override
    public Integer getCostAmount() {
        return null;
    }

    @Override
    public Long getCooldown() {
        return null;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, " ", 0);
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
