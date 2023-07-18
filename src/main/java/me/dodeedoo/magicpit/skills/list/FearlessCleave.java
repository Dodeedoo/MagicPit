package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FearlessCleave implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Vector direction = player.getEyeLocation().getDirection().normalize();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Clone your blade as &bMana");
        lore.add("&7Launch 3 cleaving attacks");
        lore.add("&7Each Cleave does damage based on defense");
        lore.add("&7Each consecutive cleave does 20% more dmg");
        lore.add("&7Cost: &b150 Mana");
        lore.add("&7Cooldown: 12 seconds");
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
        return 150;
    }

    @Override
    public Long getCooldown() {
        return 12L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&bFearless Cleave", 0);
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
