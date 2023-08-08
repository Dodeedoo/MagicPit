package me.dodeedoo.magicpit.classes.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.classes.PitClass;
import me.dodeedoo.magicpit.classes.PitClassData;
import me.dodeedoo.magicpit.classes.PitClassProperty;
import me.dodeedoo.magicpit.classes.PropertyType;
import me.dodeedoo.magicpit.skills.list.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Druid implements PitClass {

    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();
    public static FileConfiguration data = new YamlConfiguration();
    public static File file = new File(MagicPitCore.getInstance().getDataFolder(), "/classes/" + Druid.class.getSimpleName() + ".yml");

    public Druid() {
        nodeMap.put(new PitClassProperty(
                "&a&lWell of Life",
                WellOfLife.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7AOE healing"),
                Material.BIRCH_SAPLING
        ), new ArrayList<>());
        nodeMap.put(new PitClassProperty(
                        "&bWisdom",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Maxmana"),
                        Util.returnStringList("&7&oFor the apple is not the sapling", "&7&oAnd the sapling is not the apple"),
                        100,
                        Material.CYAN_CANDLE),
                Util.returnList(0)
        );
        nodeMap.put(new PitClassProperty(
                "&aTotem",
                Totem.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Mystery"),
                Material.OAK_SAPLING
        ), Util.returnList(0, 0));
    }

    @Override
    public Boolean isTestClass() {
        return false;
    }

    @Override
    public Material getGuiMaterial() {
        return Material.EMERALD;
    }

    @Override
    public List<String> getGuiLore() {
        return Util.returnStringList("wqdsa", "asfagdgf");
    }

    @Override
    public String getFancyName() {
        return "&aDruid";
    }

    @Override
    public HashMap<Player, PitClassData> getDataMap() {
        return dataMap;
    }

    @Override
    public HashMap<PitClassProperty, List<Integer>> getNodeMap() {
        return nodeMap;
    }

    @Override
    public void saveData(FileConfiguration newData) {
        data = newData;
        try {
            newData.save(file);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getData() {
        FileConfiguration dt = data;
        if (!file.exists()) {
            try {
                file.createNewFile();
                dt.load(file);
            }catch (Exception e) {
                e.printStackTrace();
            }
            dt.set("players.player", new PitClassData(nodeMap, 0, 0));
            saveData(dt);
        }else if (!dt.contains("players")){
            try {
                dt.load(file);
            }catch (Exception e) {
                e.printStackTrace();
            }
            Bukkit.getLogger().info(dt.getKeys(true).toString());
        }
        return dt;
    }

    @Override
    public void refreshNodeMap() {
        for (PitClassProperty property : nodeMap.keySet()) {
            switch (property.name) {
                case "&a&lWell of Life": {
                    nodeMap.put(property, new ArrayList<>());
                    break;
                }
                case "&bWisdom": {
                    nodeMap.put(property, Util.returnList(0));
                    break;
                }
                case "&aTotem": {
                    nodeMap.put(property, Util.returnList(0, 0));
                    break;
                }
            }
        }
    }
}
