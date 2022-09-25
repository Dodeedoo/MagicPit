package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.skills.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HoldRightClickSkillExample implements Skill, Serializable {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();
    public static HashMap<Player, Long> heldticksmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Long heldfor = heldticksmap.get(player);
        player.sendMessage("test " + heldfor);
        if (heldfor >= 60) {
            player.sendMessage("held for 3 seconds");
            heldticksmap.put(player, 0L);
            initiateCooldown(player);
        }
        Long before = heldticksmap.get(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(MagicPitCore.getInstance(), () -> {
            if (before.equals(heldticksmap.get(player))) {
                heldticksmap.put(player, 0L);
            }
        }, 10);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("test");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.HOLD_RIGHT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 25;
    }

    @Override
    public Long getCooldown() {
        return 5L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&6Poop ball &7", 0);
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
        return heldticksmap;
    }

    @Override
    public void initiateCooldown(Player player) {
        HashMap<Player, Long> cdmap = getCooldownMap();
        cdmap.put(player, TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()));
        setCooldownMap(cdmap);
    }
}
