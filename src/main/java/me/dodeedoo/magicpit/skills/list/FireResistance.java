package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FireResistance implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        if ((int) AttributesHandler.Attributes.get("Scorch").getPlayer(player) > 5) {
            AttributesHandler.Attributes.get("Scorch").getPlayerStats().put(player, (int) AttributesHandler.Attributes.get("Scorch").getPlayer(player) - 5);
            initiateCooldown(player);
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7&6-5 Scorch &7Whenever you are hit");
        lore.add("&7Cooldown: 1 second");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.DAMAGED;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 0;
    }

    @Override
    public Long getCooldown() {
        return 1L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&6Fire Resistance", 0);
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
