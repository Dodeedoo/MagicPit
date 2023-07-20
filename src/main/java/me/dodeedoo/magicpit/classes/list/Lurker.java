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
import java.util.HashMap;
import java.util.List;

public class Lurker implements PitClass {
    public static HashMap<Player, PitClassData> dataMap = new HashMap<>();
    public static HashMap<PitClassProperty, List<Integer>> nodeMap = new HashMap<>();
    public static FileConfiguration data = new YamlConfiguration();
    public static File file = new File(MagicPitCore.getInstance().getDataFolder(), "/classes/" + Lurker.class.getSimpleName() + ".yml");

    public Lurker() {
        nodeMap.put(new PitClassProperty(
                "&8Cloak Ability",
                Cloak.class.getName(),
                PropertyType.SKILL,
                null,
                Util.returnStringList("&7&oThe first step to mastering the art of", "&7&oassassination is to not be seen"),
                Material.DIAMOND),
                new ArrayList<>()
        );
        nodeMap.put(new PitClassProperty(
                "&2Enhanced Bones",
                PropertyType.ATTRIBUTE,
                AttributesHandler.Attributes.get("Defense"),
                Util.returnStringList("&7&ocorruption feeds your bones..."),
                150,
                Material.GLOWSTONE),
                Util.returnList(1)
        );

        nodeMap.put(new PitClassProperty("&9Accurate Strikes", PropertyType.ATTRIBUTE, AttributesHandler.Attributes.get("CritChance"), Util.returnStringList("&7&oGo for the arteries"), 10, Material.GLOWSTONE), Util.returnList(0));

        nodeMap.put(new PitClassProperty("&fAgile",  PropertyType.ATTRIBUTE, AttributesHandler.Attributes.get("Weight"), Util.returnStringList("&7&oFaster with reduced weight"), 5, Material.GLOWSTONE), Util.returnList(0, 0));

        nodeMap.put(new PitClassProperty("&2Paralyzing Dart", ParalyzingDart.class.getName(), PropertyType.SKILL, null,
                Util.returnStringList("&7&oAn easier solution for easier kills"), Material.TIPPED_ARROW), Util.returnList(1, 0));

        nodeMap.put(new PitClassProperty("&4Reduced Health",  PropertyType.ATTRIBUTE, AttributesHandler.Attributes.get("Health"), Util.returnStringList("&7&oSacrifices Must be made", "&7&oin the name of corruption", "&7(Required to unlock &4Corruption &7)")
                , -15, Material.GLOWSTONE), Util.returnList(0, 0, 0));

        nodeMap.put(new PitClassProperty("&bArcane Knowledge",  PropertyType.ATTRIBUTE, AttributesHandler.Attributes.get("Knowledge"), Util.returnStringList("&7&oBasic knowledge of arcane", "&7&oboosts magic dmg"),
                10, Material.GLOWSTONE), Util.returnList(1, 1));

        nodeMap.put(new PitClassProperty("&fWind Strike", WindStrike.class.getName(), PropertyType.SKILL, null,
                Util.returnStringList("&7&oHarness the air around you"), Material.IRON_SWORD), Util.returnList(1, 1, 0));

        nodeMap.put(new PitClassProperty("&4Reduced Regeneration",  PropertyType.ATTRIBUTE, AttributesHandler.Attributes.get("Regeneration"), Util.returnStringList("&7&oSacrifices Must be made", "&7&oin the name of corruption", "&7(Required to unlock &8Death Grapple &7)"),
                -10, Material.GLOWSTONE), Util.returnList(0, 0, 0, 1));

        nodeMap.put(new PitClassProperty("&4Corruption", Corruption.class.getName(), PropertyType.SKILL, null,
                Util.returnStringList("&7&oEMBRACE IT!!!!!"), Material.REDSTONE), Util.returnList(0, 0, 0, 0));

        nodeMap.put(new PitClassProperty("&8Death Grapple", DeathGrapple.class.getName(), PropertyType.SKILL, null,
                Util.returnStringList("&7&odabbling in the curse arts"), Material.SCULK_VEIN), Util.returnList(0, 0, 0, 1, 0));
    }

    @Override
    public Boolean isTestClass() {
        return false;
    }

    @Override
    public Material getGuiMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public List<String> getGuiLore() {
        return Util.returnStringList("&7Glass Cannon","&7&oThe corrupted art of killing");
    }

    @Override
    public String getFancyName() {
        return "&4Lurker";
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
                case "&8Cloak Ability": {
                    nodeMap.put(property, new ArrayList<>());
                    break;
                }
                case "&9Accurate Strikes": {
                    nodeMap.put(property, Util.returnList(0));
                    break;
                }
                case "&2Enhanced Bones": {
                    nodeMap.put(property, Util.returnList(1));
                    break;
                }
                case "&fAgile": {
                    nodeMap.put(property, Util.returnList(0, 0));
                    break;
                }
                case "&2Paralyzing Dart": {
                    nodeMap.put(property, Util.returnList(1, 0));
                    break;
                }
                case "&4Reduced Health": {
                    nodeMap.put(property, Util.returnList(0, 0, 0));
                    break;
                }
                case "&bArcane Knowledge": {
                    nodeMap.put(property, Util.returnList(1, 1));
                    break;
                }
                case "&fWind Strike": {
                    nodeMap.put(property, Util.returnList(1, 1, 0));
                    break;
                }
                case "&4Reduced Regeneration": {
                    nodeMap.put(property, Util.returnList(0, 0, 0, 1));
                    break;
                }
                case "&4Corruption": {
                    nodeMap.put(property, Util.returnList(0, 0, 0 ,0));
                    break;
                }
                case "&8Death Grapple": {
                    nodeMap.put(property, Util.returnList(0, 0, 0, 1, 0));
                    break;
                }
            }
        }
    }
}
