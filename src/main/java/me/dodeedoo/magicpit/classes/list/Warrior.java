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

public class Warrior implements PitClass {

    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();
    public static FileConfiguration data = new YamlConfiguration();
    public static File file = new File(MagicPitCore.getInstance().getDataFolder(), "/classes/" + Warrior.class.getSimpleName() + ".yml");

    public Warrior() {
        nodeMap.put(new PitClassProperty(
                "&3Heroic Strike",
                HeroicStrike.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Take it out"),
                Material.IRON_SWORD
        ), new ArrayList<>());
        nodeMap.put(new PitClassProperty(
                        "&9Thick Skull",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Defense"),
                        Util.returnStringList("&7&oGood or bad?"),
                        300,
                        Material.SKELETON_SKULL),
                Util.returnList(1)
        );
        nodeMap.put(new PitClassProperty(
                        "&cStrong &7&o(healthy) &cAppetite",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Strength"),
                        Util.returnStringList("&7&o6 meals a day"),
                        150,
                        Material.GLOWSTONE),
                Util.returnList(0)
        );
        nodeMap.put(new PitClassProperty(
                "&bBrace",
                Brace.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Brace for impact!!!11!"),
                Material.IRON_BARS
        ), Util.returnList(1, 0));
        nodeMap.put(new PitClassProperty(
                "&b&oFearless Cleave",
                FearlessCleave.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Technique derived from Glimp himself"),
                Material.DIAMOND_SWORD
        ), Util.returnList(0, 0));
        nodeMap.put(new PitClassProperty(
                "&3Defensive Rush",
                DefensiveRush.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Charge at your foes with your shield up"),
                Material.LEATHER_BOOTS
        ), Util.returnList(1, 0, 1));
    }


    @Override
    public Boolean isTestClass() {
        return false;
    }

    @Override
    public Material getGuiMaterial() {
        return Material.IRON_HELMET;
    }

    @Override
    public List<String> getGuiLore() {
        return Util.returnStringList("&7Balance of Defense and Offense");
    }

    @Override
    public String getFancyName() {
        return "&bWarrior";
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
            dt.set("players.player", new PitClassData(nodeMap,0, 0));
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
                case "&3Heroic Strike": {
                    nodeMap.put(property, new ArrayList<>());
                    break;
                }
                case "&9Thick Skull": {
                    nodeMap.put(property, Util.returnList(1));
                    break;
                }
                case "&cStrong &7&o(healthy) &cAppetite": {
                    nodeMap.put(property, Util.returnList(0));
                    break;
                }
                case "&bBrace": {
                    nodeMap.put(property, Util.returnList(1, 0));
                    break;
                }
                case "&b&oFearless Cleave": {
                    nodeMap.put(property, Util.returnList(0, 0));
                    break;
                }
                case "&3Defensive Rush": {
                    nodeMap.put(property, Util.returnList(1, 0, 1));
                    break;
                }
            }
        }
    }
}
