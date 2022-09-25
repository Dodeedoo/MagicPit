package me.dodeedoo.magicpit.commands;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamage;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamageType;
import me.dodeedoo.magicpit.scoreboard.ScoreboardManager;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillHandler;
import me.dodeedoo.magicpit.skills.list.HoldRightClickSkillExample;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class setStrength implements CommandExecutor  {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        try {
            AttributesHandler.Attributes.get("Strength").getPlayerStats().put(player, Integer.parseInt(strings[0]));
            //DisplayGui.showDisplayGuiToPlayer(player);
            //AttributesHandler.Attributes.get("Mana").getPlayerStats().put(player, 0);
            //AttributesHandler.Attributes.get("Scorch").getPlayerStats().put(player, 30);
            Bukkit.getPluginManager().callEvent(new MagicDamage(player, player, MagicDamageType.FIRE, 1200));
            Bukkit.getPluginManager().callEvent(new MagicDamage(player, player, MagicDamageType.CURSE, 1200));
            ScoreboardManager.refreshSkill(player);
        }catch (IndexOutOfBoundsException e) {
            player.sendMessage(Util.colorize("&cusage: /setstrength <number>"));
        }catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Util.colorize("&cunknown error occured! contact dodeedoo (eqi)"));
        }
        return false;
    }
}
