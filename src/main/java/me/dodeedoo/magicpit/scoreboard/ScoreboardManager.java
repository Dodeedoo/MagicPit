package me.dodeedoo.magicpit.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Player;

import java.util.HashMap;

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
        FastBoard board = new FastBoard(player);
        board.updateTitle(Util.colorize("&d&lMagicPit"));
        board.updateLine(0, Util.colorize("&e"));
        board.updateLine(1, Util.colorize("&7Balance: &6x$"));
        board.updateLine(2, Util.colorize("&7Level: &ax"));
        board.updateLine(3, Util.colorize("&7Power Lvl: &dx"));
        board.updateLine(4, Util.colorize("&e"));
        board.updateLine(5, Util.colorize("&6Fire Blast &7Charging... (20/50)"));
        boardMap.put(player, board);
    }
}
