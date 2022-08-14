package me.dodeedoo.magicpit.actionbar;

import me.dodeedoo.magicpit.ConfigUtil;
import me.dodeedoo.magicpit.MagicPitCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttributeDisplay {

    public static HashMap<Player, List<Object>> preferences = new HashMap<>();

    public static void handlePreferenceLoad(Player player) {
        List<Object> list = (List<Object>) ConfigUtil.getPlayerConfig(player).getList("Display");
        MagicPitCore.getInstance().getLogger().info(ConfigUtil.getPlayerConfig(player).getCurrentPath());
        preferences.put(player, list);
    }

    public static void handlePreferenceUnload(Player player) {
        List<Object> list = preferences.get(player);
        FileConfiguration conf = ConfigUtil.getPlayerConfig(player);
        conf.set("Display", list);
        try {
            conf.save(ConfigUtil.buildSaveFile(player));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
