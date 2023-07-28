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

public class Paladin implements PitClass {

    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();
    public static FileConfiguration data = new YamlConfiguration();
    public static File file = new File(MagicPitCore.getInstance().getDataFolder(), "/classes/" + Paladin.class.getSimpleName() + ".yml");

    public Paladin() {
        nodeMap.put(new PitClassProperty(
                "&eHoly Field",
                HolyField.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7Call on the strength of the greater one"),
                Material.GOLD_NUGGET
        ), new ArrayList<>());
        nodeMap.put(new PitClassProperty(
                        "&cIntimidating",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Threat"),
                        Util.returnStringList("&7&oGood or bad?", "&7&oHigher threat makes mobs target you instead of others"),
                        25,
                        Material.WITHER_SKELETON_SKULL),
                Util.returnList(0)
        );
        nodeMap.put(new PitClassProperty(
                "&eTaunt",
                Taunt.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7&o'Hey you look stupid Lol'"),
                Material.ANDESITE_SLAB
        ), Util.returnList(0, 0));
        nodeMap.put(new PitClassProperty(
                        "&2Defensive Stance",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Defense"),
                        Util.returnStringList("&7&oLess damage = good"),
                        100,
                        Material.WITHER_SKELETON_SKULL),
                Util.returnList(1)
        );
        nodeMap.put(new PitClassProperty(
                "&2Stone Skin",
                StoneSkin.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7&oOnly magic can get through"),
                Material.TURTLE_HELMET
        ), Util.returnList(1, 0));
        nodeMap.put(new PitClassProperty(
                        "&2Defensiver Stance",
                        PropertyType.ATTRIBUTE,
                        AttributesHandler.Attributes.get("Defense"),
                        Util.returnStringList("&7&oLess damage = good"),
                        150,
                        Material.WITHER_SKELETON_SKULL),
                Util.returnList(1, 0, 0)
        );
        nodeMap.put(new PitClassProperty(
                "&e&lRetribution",
                Retribution.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7&oThey must pay for their sins"),
                Material.GOLDEN_SHOVEL
        ), Util.returnList(1, 0));
    }

    @Override
    public Boolean isTestClass() {
        return false;
    }

    @Override
    public Material getGuiMaterial() {
        return Material.GOLDEN_CHESTPLATE;
    }

    @Override
    public List<String> getGuiLore() {
        return Util.returnStringList("&7Defense/Support");
    }

    @Override
    public String getFancyName() {
        return "&ePaladin";
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
                case "&eHoly Field": {
                    nodeMap.put(property, new ArrayList<>());
                    break;
                }
                case "&cIntimidating": {
                    nodeMap.put(property, Util.returnList(0));
                    break;
                }
                case "&eTaunt": {
                    nodeMap.put(property, Util.returnList(0, 0));
                    break;
                }
                case "&2Defensive Stance": {
                    nodeMap.put(property, Util.returnList(1));
                    break;
                }
                case "&2Stone Skin": {
                    nodeMap.put(property, Util.returnList(1, 0));
                    break;
                }
                case "&2Defensiver Stance": {
                    nodeMap.put(property, Util.returnList(1, 0, 0));
                    break;
                }
                case "&e&lRetribution": {
                    nodeMap.put(property, Util.returnList(1, 0, 0, 0));
                    break;
                }
            }
        }
    }
}
