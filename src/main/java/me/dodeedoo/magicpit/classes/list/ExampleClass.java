package me.dodeedoo.magicpit.classes.list;

import me.dodeedoo.magicpit.classes.PitClass;
import me.dodeedoo.magicpit.classes.PitClassData;
import me.dodeedoo.magicpit.classes.PitClassProperty;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class ExampleClass implements PitClass {

    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();


    @Override
    public String getFancyName() {
        return "&7&lTest Class";
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
    public void saveData(FileConfiguration data) {

    }

    @Override
    public FileConfiguration getData() {
        return null;
    }
}
