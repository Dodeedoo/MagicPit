package me.dodeedoo.magicpit.items;

import me.dodeedoo.magicpit.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//storing items
public class ItemManager {

    public static HashMap<String, ItemStack> itemList = new HashMap<>();

    public static void loadItemsFromConfig() {
        FileConfiguration cfg = ConfigUtil.itemconfig;
        itemList.clear();
        for (String key : cfg.getKeys(false)) {
//            Bukkit.getLogger().info(key);
//            Bukkit.getLogger().info(cfg.getItemStack(key).getType().toString());
            try {
                itemList.put(key, PitItem.updateLoreOfItemStack(cfg.getItemStack(key)));
            }catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void registerItem(ItemStack item, String key) {
        FileConfiguration cfg = ConfigUtil.itemconfig;
        cfg.set(key, item);
        try {
            cfg.save(ConfigUtil.buildItemFile());
        }catch (Exception e) {
            e.printStackTrace();
        }
        loadItemsFromConfig();
    }

    public static void unregisterItem(String key) {
        FileConfiguration cfg = ConfigUtil.itemconfig;
        cfg.set(key, null);
        try {
            cfg.save(ConfigUtil.buildItemFile());
        }catch (Exception e) {
            e.printStackTrace();
        }
        loadItemsFromConfig();
    }

    public static Set<String> getKeys() {
        FileConfiguration cfg = ConfigUtil.itemconfig;
        return cfg.getKeys(false);
    }

}
