package me.dodeedoo.magicpit.classes;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public interface PitClass {
    String getFancyName();
    Boolean isTestClass();
    Material getGuiMaterial();
    List<String> getGuiLore();

    HashMap<Player, PitClassData> getDataMap();
    HashMap<PitClassProperty, List<Integer>> getNodeMap();

    void saveData(FileConfiguration data);
    FileConfiguration getData();

    //workaround for random node map value changes
    void refreshNodeMap();
}
