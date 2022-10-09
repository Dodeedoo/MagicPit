package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.ConfigUtil;
import me.dodeedoo.magicpit.PitPlayer;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.actionbar.AttributeDisplay;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.classes.PitClassHandler;
import me.dodeedoo.magicpit.classes.PlayerClassLoading;
import me.dodeedoo.magicpit.scoreboard.ScoreboardManager;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillHandler;
import me.dodeedoo.magicpit.skills.list.EggBall;
import me.dodeedoo.magicpit.skills.list.FireBall;
import me.dodeedoo.magicpit.skills.list.HoldRightClickSkillExample;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class Connection implements Listener {

    @EventHandler
    public void Playerjoin(PlayerJoinEvent event) {
        Util.handlePlayerAttributes(event.getPlayer());
        Util.updatePlayerAttributes(event.getPlayer());
        ConfigUtil.handlePlayerConfig(event.getPlayer());
        AttributeDisplay.handlePreferenceLoad(event.getPlayer());
        ScoreboardManager.loadPlayer(event.getPlayer());
        PitPlayer.handlePlayerLoad(event.getPlayer());
        SkillHandler.playerSkills.put(event.getPlayer(), new HashMap<>());
        PlayerClassLoading.handleSelectedClassLoad(event.getPlayer());
        PitClassHandler.loadData(event.getPlayer());
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event) {
        for (Attribute attribute : AttributesHandler.Attributes.values()) {
            attribute.getPlayerStats().remove(event.getPlayer());
        }
        AttributeDisplay.handlePreferenceUnload(event.getPlayer());
        PitPlayer.handlePlayerUnload(event.getPlayer());
        ScoreboardManager.boardMap.remove(event.getPlayer());
        PlayerClassLoading.handleSelectedClassUnload(event.getPlayer());
        PitClassHandler.utilListForPreviousClass.remove(event.getPlayer());
        PitClassHandler.unloadData(event.getPlayer());
    }


}
