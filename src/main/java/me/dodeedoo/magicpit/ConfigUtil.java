package me.dodeedoo.magicpit;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigUtil {

    public static HashMap<Player, FileConfiguration> configs = new HashMap<>();
    public static FileConfiguration itemconfig;
    public static MagicPitCore main = MagicPitCore.getInstance();

    public static void handlePlayerConfig(Player player) {
        FileConfiguration cfg;
        if (!new File(main.getDataFolder() + "/players/" + player.getUniqueId() + ".yml").exists()) {
            cfg =  createConfig(new File(main.getDataFolder() + "/player/" + player.getUniqueId() + ".yml"), new YamlConfiguration(), "/players/" + player.getUniqueId() + ".yml");
            cfg.set("Display", new String[]{"Strength", "Maxmana", "Defense", "Health"});
            MagicPitCore.getInstance().getLogger().info("EEEEEEEE");
            cfg.set("level", 0);
            cfg.set("exp", 0);
            cfg.set("protected", true);
            cfg.set("selectedclass", "ExampleClass");
            try {
                cfg.save(buildSaveFile(player));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            cfg = getRawConfig(player);
        }

        configs.put(player, cfg);
        MagicPitCore.getInstance().getLogger().info(configs.toString());
    }

    public static FileConfiguration getPlayerConfig(Player player) {
        return configs.get(player);
    }

    public static void setPlayerConfig(Player player, FileConfiguration configuration) {
        configs.put(player, configuration);
    }

    public static void handleItemConfig() {
        FileConfiguration cfg;
        if (!new File(main.getDataFolder() + "items.yml").exists()) {
            cfg = createConfig(new File("items.yml"), new YamlConfiguration(), "items.yml");
            cfg.set("testitem", new ItemStack(Material.DIAMOND));
            try {
                cfg.save(buildItemFile());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            cfg = getRawItemConfig();
        }
        itemconfig = cfg;
    }

    public static FileConfiguration createConfig(File ConfigFile, FileConfiguration Config, String name) {
        ConfigFile = new File(MagicPitCore.getInstance().getDataFolder(), name);
        if (!ConfigFile.exists()) {
            ConfigFile.getParentFile().mkdirs();
            try {
                ConfigFile.createNewFile();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        Config = new YamlConfiguration();
        try {
            Config.load(ConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return Config;
    }

    public static FileConfiguration getRawItemConfig() {
        File ConfigFile = new File(MagicPitCore.getInstance().getDataFolder(), "items.yml");
        YamlConfiguration conf = new YamlConfiguration();
        try {
            conf.load(ConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return conf;
    }

    public static FileConfiguration getRawConfig(Player player) {
        File ConfigFile = new File(MagicPitCore.getInstance().getDataFolder(), "/players/" + player.getUniqueId() + ".yml");
        YamlConfiguration conf = new YamlConfiguration();
        try {
            conf.load(ConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return conf;
    }

    public static File buildSaveFile(Player player) {
        File ConfigFile = new File(MagicPitCore.getInstance().getDataFolder(), "/players/" + player.getUniqueId() + ".yml");
        return ConfigFile;
    }

    public static File buildItemFile() {
        return new File(MagicPitCore.getInstance().getDataFolder(), "items.yml");
    }
}
