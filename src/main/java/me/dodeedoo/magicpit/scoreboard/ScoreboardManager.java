package me.dodeedoo.magicpit.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.PitPlayer;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.classes.PitClass;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillHandler;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ScoreboardManager {

    /*
        title magicpit
        line 1 space
        line 2 balance
        line 3 level
        line 4 power level
        line 5 class
        line 5 space
        line 7-15 Skill display
     */

    public static HashMap<Player, FastBoard> boardMap = new HashMap<>();
    public static HashMap<Player, SkillIndicator> skillMap = new HashMap<>();

    public static void loadPlayer(Player player) {
        Bukkit.getScheduler().runTaskTimer(MagicPitCore.getInstance(), () -> {
            updatePlayer(player);
        }, 5, 5);
    }

    public static void updatePlayer(Player player) {
        FastBoard board;
        if (boardMap.containsKey(player)) {
            board = boardMap.get(player);
        }else {
            board = new FastBoard(player);
        }
        board.updateTitle(Util.colorize("&d&lMagicPit"));
        board.updateLine(0, Util.colorize("&e"));
        board.updateLine(1, Util.colorize("&7Balance: &6" + PitPlayer.playerMap.get(player).balance + "$"));
        board.updateLine(2, Util.colorize("&7Level: &a" + PitPlayer.playerMap.get(player).level + " &8(" + PitPlayer.playerMap.get(player).exp + "/" + (10 * (Math.pow(PitPlayer.playerMap.get(player).level, 3))) + ")"));
        board.updateLine(3, Util.colorize("&7Power Lvl: &dx" + PitPlayer.playerMap.get(player).powerlevel));
        board.updateLine(4, Util.colorize("&7Class: " + PitPlayer.playerMap.get(player).playerClass.getFancyName()));
        board.updateLine(5, Util.colorize("&e"));
        int line = 6;

        if (SkillHandler.playerSkills.containsKey(player)) {
            for (Skill skill : SkillHandler.playerSkills.get(player).keySet()) {
                long timeleft = 0L;
                try {
                    timeleft = System.currentTimeMillis() - skill.getCooldownMap().get(player);
                } catch (NullPointerException e) {
                    timeleft = System.currentTimeMillis();
                }
                if ((skill.getCooldown() - TimeUnit.MILLISECONDS.toSeconds(timeleft)) > 0) {
                    board.updateLine(line, Util.colorize(skill.getIndicator().indicatorString + " &c" + (skill.getCooldown() - TimeUnit.MILLISECONDS.toSeconds(timeleft)) + "s"));
                } else {
                    board.updateLine(line, Util.colorize(skill.getIndicator().indicatorString + " &aReady"));
                }
                line += 1;
            }
        }
        boardMap.put(player, board);
    }

    public static void refreshSkill(Player player) {
        FastBoard board = boardMap.get(player);

        for (int line = 5; line <= board.getLines().size(); line++) {
            board.removeLine(line);
        }
        boardMap.put(player, board);
    }

}
