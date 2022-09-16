package me.dodeedoo.magicpit.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
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
        line 5 space
        line 6-15 Skill display
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
        board.updateLine(1, Util.colorize("&7Balance: &6x$"));
        board.updateLine(2, Util.colorize("&7Level: &ax"));
        board.updateLine(3, Util.colorize("&7Power Lvl: &dx"));
        board.updateLine(4, Util.colorize("&e"));
        int line = 5;
        for (Skill skill : SkillHandler.playerSkills.get(player).keySet()) {
            long timeleft = 0L;
            try {
                timeleft = System.currentTimeMillis() - skill.getCooldownMap().get(player);
            }catch (NullPointerException e) {
                timeleft = System.currentTimeMillis();
            }
            if ((skill.getCooldown() - TimeUnit.MILLISECONDS.toSeconds(timeleft)) > 0) {
                board.updateLine(line, Util.colorize(skill.getIndicator().indicatorString + " &c" + (skill.getCooldown() - TimeUnit.MILLISECONDS.toSeconds(timeleft)) + "s"));
            }else{
                board.updateLine(line, Util.colorize(skill.getIndicator().indicatorString + " &aReady"));
            }
            line += 1;
        }
        boardMap.put(player, board);
    }
}
