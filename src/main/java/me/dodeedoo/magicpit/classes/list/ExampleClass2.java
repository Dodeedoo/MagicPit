package me.dodeedoo.magicpit.classes.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
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
        nodeMap.put(new PitClassProperty("FirstNode", EggBall.class.getName(), PropertyType.SKILL, null, Arrays.asList("lore", "lore"), Material.DIAMOND_AXE), new ArrayList<>());
        nodeMap.put(new PitClassProperty("SecondNode"), Util.returnList(0));
        nodeMap.put(new PitClassProperty("SecondOfTheNode"), Util.returnList(1));
        nodeMap.put(new PitClassProperty("ThirdNode"), Util.returnList(0, 0));
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
        return Util.returnStringList("poop", "foop");
    }

    @Override
    public String getFancyName() {
        return "&7&lTest Class 2";
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
            Bukkit.getLogger().info("eeeefcsdfas");
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
        nodeMap.clear();
        nodeMap.put(new PitClassProperty("FirstNode", EggBall.class.getName(), PropertyType.SKILL, null, Arrays.asList("lore", "lore"), Material.DIAMOND_AXE), new ArrayList<>());
        nodeMap.put(new PitClassProperty("SecondNode"), Util.returnList(0));
        nodeMap.put(new PitClassProperty("SecondOfTheNode"), Util.returnList(1));
        nodeMap.put(new PitClassProperty("ThirdNode"), Util.returnList(0, 0));
    }
}
