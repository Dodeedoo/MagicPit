package me.dodeedoo.magicpit.classes;

import me.dodeedoo.magicpit.PitPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PitClassHandler {

    public static List<PitClass> classList = new ArrayList<>();

    public static void select(Player player, PitClass pitClass) {
        PitPlayer.playerMap.get(player).setPlayerClass(pitClass);
        if (!pitClass.getDataMap().containsKey(player)) {
            pitClass.getDataMap().put(player, new PitClassData(pitClass.getNodeMap()));
        }
    }

    public static void startClassWorker() {

    }

}
