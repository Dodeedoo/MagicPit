package me.dodeedoo.magicpit.classes;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public interface PitClass {
    String getFancyName();

    HashMap<Player, PitClassData> getDataMap();
    HashMap<PitClassProperty, List<Integer>> getNodeMap();

    void saveData(FileConfiguration data);
    FileConfiguration getData();
}
