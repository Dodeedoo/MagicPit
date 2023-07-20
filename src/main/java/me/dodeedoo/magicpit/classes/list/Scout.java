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

public class Scout implements PitClass {

    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();
    public static FileConfiguration data = new YamlConfiguration();
    public static File file = new File(MagicPitCore.getInstance().getDataFolder(), "/classes/" + Scout.class.getSimpleName() + ".yml");

    public Scout() {
        nodeMap.put(new PitClassProperty(
                "&fSpeed Boost",
                SpeedBoost.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Quick on your feet"),
                Material.CHAINMAIL_BOOTS
        ), new ArrayList<>());
        nodeMap.put(new PitClassProperty(
                        "&f&lSwift",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Swiftness"),
                        Util.returnStringList("&fSwiftness &7increases the chance of", "&7dodging incoming damage"),
                        20,
                        Material.BONE_MEAL),
                Util.returnList(1)
        );
    }

    @Override
    public Boolean isTestClass() {
        return false;
    }

    @Override
    public Material getGuiMaterial() {
        return Material.IRON_BOOTS;
    }

    @Override
    public List<String> getGuiLore() {
        return Util.returnStringList("&7Speed and survivability");
    }

    @Override
    public String getFancyName() {
        return "&fScout";
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
                case "&fSpeed Boost": {
                    nodeMap.put(property, new ArrayList<>());
                    break;
                }
                case "&f&lSwift": {
                    nodeMap.put(property, Util.returnList(1));
                    break;
                }
            }
        }
    }
}
