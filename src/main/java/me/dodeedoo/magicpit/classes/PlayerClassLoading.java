package me.dodeedoo.magicpit.classes;

import me.dodeedoo.magicpit.ConfigUtil;
import me.dodeedoo.magicpit.PitPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerClassLoading {

    public static void handleSelectedClassLoad(Player player) {
        String pitClass = ConfigUtil.getPlayerConfig(player).getString("selectedclass");
        if (pitClass.equals("none")) {
            return;
        }
        PitClass tempClass = null;
        for (PitClass pitClass1 : PitClassHandler.classList) {
            if (pitClass1.getClass().getSimpleName().equals(pitClass)) {
                tempClass = pitClass1;
                break;
            }
        }
        PitPlayer.playerMap.get(player).setPlayerClass(tempClass);
//        try {
//            PitPlayer.playerMap.get(player).setPlayerClass((PitClass) Class.forName("me.dodeedoo.magicpit.classes." + pitClass).newInstance());
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void handleSelectedClassUnload(Player player) {
        PitClass pitClass = PitPlayer.playerMap.get(player).playerClass;
        if (pitClass == null) {
            return;
        }
        FileConfiguration conf = ConfigUtil.getPlayerConfig(player);
        conf.set("selectedclass", pitClass.getClass().getSimpleName());
        try {
            conf.save(ConfigUtil.buildSaveFile(player));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
