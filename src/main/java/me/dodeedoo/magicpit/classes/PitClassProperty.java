package me.dodeedoo.magicpit.classes;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.scoreboard.ScoreboardManager;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PitClassProperty {

    public String name;
    public String skillClassName = null;
    public PropertyType type = PropertyType.SKILL;
    public Attribute attribute = null;
    public List<String> lore = new ArrayList<>();
    public Object amount;
    private boolean dummy = false;

    public PitClassProperty(String name) {
        this.name = name;
        this.dummy = true;
    }

    public PitClassProperty(String name, String skillClassName, PropertyType type, Attribute attribute, List<String> lore) {
        this.name = name;
        this.type = type;
        this.skillClassName = skillClassName;
        this.attribute = attribute;
        this.lore = lore;
    }

    public PitClassProperty(String name, String skillClassName, PropertyType type, Attribute attribute, List<String> lore, Object amount) {
        this.name = name;
        this.type = type;
        this.skillClassName = skillClassName;
        this.attribute = attribute;
        this.lore = lore;
        this.amount = amount;
    }

    public void apply(Player player) {
        if (dummy) {
            return;
        }
        if (type == PropertyType.SKILL) {
            HashMap<Skill, String[]> tempMap = SkillHandler.playerSkills.get(player);
            try {
                tempMap.put((Skill) Class.forName(skillClassName).newInstance(), new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Util.colorize("&cA critical error has happened regarding skill loading, please contact admins"));
            }
            SkillHandler.playerSkills.put(player, tempMap);
        }
        if (type == PropertyType.ATTRIBUTE) {
            attribute.getPlayerStats().put(player, ((int)attribute.getPlayer(player) + (int)amount));
        }
    }

    public void remove(Player player) {
        if (dummy) {
            return;
        }
        if (type == PropertyType.SKILL) {
            for (Skill appliedSkill : SkillHandler.playerSkills.get(player).keySet()) {
                if (appliedSkill.getClass().getName().equals(skillClassName)) {
                    SkillHandler.playerSkills.get(player).remove(appliedSkill);
                    ScoreboardManager.refreshSkill(player);
                }
            }
        }
        if (type == PropertyType.ATTRIBUTE) {
            attribute.getPlayerStats().put(player, ((int)attribute.getPlayer(player) - (int)amount));
        }
    }

}
