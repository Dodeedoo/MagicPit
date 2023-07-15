package me.dodeedoo.magicpit.classes.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.classes.PitClass;
import me.dodeedoo.magicpit.classes.PitClassData;
import me.dodeedoo.magicpit.classes.PitClassProperty;
import me.dodeedoo.magicpit.classes.PropertyType;
import me.dodeedoo.magicpit.skills.list.EggBall;
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

public class ExampleClass2 implements PitClass {
    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();
    public static FileConfiguration data = new YamlConfiguration();
    public static File file = new File(MagicPitCore.getInstance().getDataFolder(), "/classes/" + ExampleClass2.class.getSimpleName() + ".yml");

    public ExampleClass2() {
        nodeMap.put(new PitClassProperty("FirstNode", PropertyType.ATTRIBUTE, AttributesHandler.Attributes.get("Defense"), Util.returnStringList("give", "attribute"), 150, Material.GLOWSTONE), new ArrayList<>());
    }

    @Override
    public Boolean isTestClass() {
        return true;
    }

    @Override
    public Material getGuiMaterial() {
        return Material.EMERALD;
    }

    @Override
    public List<String> getGuiLore() {
        return Util.returnStringList("poop", "foop");
    }

    @Override
    public String getFancyName() {
        return "&7No Class selected";
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
            dt.set("players.player", new PitClassData(nodeMap));
            saveData(dt);
        }else if (!dt.contains("players")){
            try {
                dt.load(file);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dt;
    }

    @Override
    public void refreshNodeMap() {
        for (PitClassProperty property : nodeMap.keySet()) {
            switch (property.name) {
                case "FirstNode": {
                    nodeMap.put(property, new ArrayList<>());
                    break;
                }
            }
        }
    }
}
