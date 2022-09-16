package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.ConfigUtil;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.actionbar.AttributeDisplay;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
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
        HashMap<Skill, String[]> map = new HashMap<>();
        map.put(new FireBall(), new String[]{});
        map.put(new EggBall(), new String[]{});
        SkillHandler.playerSkills.put(event.getPlayer(), map);
        Util.handlePlayerAttributes(event.getPlayer());
        Util.updatePlayerAttributes(event.getPlayer());
        ConfigUtil.handlePlayerConfig(event.getPlayer());
        AttributeDisplay.handlePreferenceLoad(event.getPlayer());
        ScoreboardManager.loadPlayer(event.getPlayer());
//      HashMap<Skill, String[]> map = new HashMap<>();
//      map.put(new HoldRightClickSkillExample(), new String[]{});
//      SkillHandler.playerSkills.put(event.getPlayer(), map);
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event) {
        for (Attribute attribute : AttributesHandler.Attributes.values()) {
            attribute.getPlayerStats().remove(event.getPlayer());
        }
        AttributeDisplay.handlePreferenceUnload(event.getPlayer());
        ScoreboardManager.boardMap.remove(event.getPlayer());
    }


}
