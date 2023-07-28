package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Taunt implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Sound tauntSound = Sound.sound(Key.key("item_goat_horn_sound_1"), Sound.Source.AMBIENT, 5f, 1f);
        player.playSound(tauntSound, Sound.Emitter.self());

        Attribute attribute = AttributesHandler.Attributes.get("Threat");
        attribute.getPlayerStats().put(player, (int) attribute.getPlayer(player) + 300);
        Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {attribute.getPlayerStats().put(player, (int) attribute.getPlayer(player) - 300);}, 100);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Make fun of surrounding mobs");
        lore.add("&7+300 Threat for 5 seconds");
        lore.add("&7Cost: &c10 HP");
        lore.add("&7Cooldown: 30 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.SNEAK_RIGHT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.HEALTH;
    }

    @Override
    public Integer getCostAmount() {
        return 10;
    }

    @Override
    public Long getCooldown() {
        return 30L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&eTaunt", 0);
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
