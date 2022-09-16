package me.dodeedoo.magicpit.skills;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public interface Skill {
    void execute(Player player, String[] args);
    List<String> getLore();
    SkillExecuteAction getAction();
    SkillCost getCostType();
    Integer getCostAmount();
    Long getCooldown();
    SkillIndicator getIndicator();
    HashMap<Player, Long> getCooldownMap();
    void setCooldownMap(HashMap<Player, Long> map);
    Boolean overTime();
    HashMap<Player, Long> getHeldTicksMap();
    void initiateCooldown(Player player);
    //void setHeldTicksMap();
}
