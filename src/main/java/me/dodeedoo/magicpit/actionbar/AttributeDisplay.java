package me.dodeedoo.magicpit.actionbar;

import me.dodeedoo.magicpit.ConfigUtil;
import me.dodeedoo.magicpit.MagicPitCore;
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

}
