package me.dodeedoo.magicpit.classes;

import me.dodeedoo.magicpit.classes.tree.PitClassDataTree;
import me.dodeedoo.magicpit.classes.tree.PitClassTreeNode;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PitClassData implements ConfigurationSerializable {

    public Integer totalPoints;
    public Integer assignedPoints = 0;
    public PitClassDataTree tree;
    public Integer exp = 0;
    public Integer level = 0;

    //returns true if new exp levels up, false otherwise
    public boolean setExp(Integer exp) {
        this.exp = exp;
        if (exp >= this.level * 20 * (Math.pow(2, this.level))) {
            this.exp = 0;
            this.level += 1;
            return true;
        }
        return false;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setAssignedPoints(Integer assignedPoints) {
        this.assignedPoints = assignedPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setTree(PitClassDataTree tree) {
        this.tree = tree;
    }

    public PitClassData() {

    }

    public PitClassData(HashMap<PitClassProperty, List<Integer>> map, Integer exp, Integer level) {
        this.tree = constructTreeFromNodeMap(map);
    }

    public PitClassData(PitClassDataTree tree) {
        this.tree = tree;
    }

    public static PitClassDataTree constructTreeFromNodeMap(HashMap<PitClassProperty, List<Integer>> map) {
        PitClassTreeNode baseNode = new PitClassTreeNode();
        for (PitClassProperty property : map.keySet()) {
            if (map.get(property).isEmpty()) {
                baseNode = new PitClassTreeNode(property.name);
                break;
            }
        }

        int max = 1;
        for (List<Integer> list : map.values()) {
            if (list.size() > max) {
                max = list.size();
            }
        }
        for (int i = 1; i <= max; i++) {
            for (PitClassProperty property : map.keySet()) {
                //Bukkit.broadcastMessage(property.name + " " + map.get(property));
                if (map.get(property).size() == i) {
                    PitClassTreeNode tempNode = baseNode;
                    for (int i2 = 0; i2 < (i-1); i2++) {
                        //Bukkit.getLogger().info(String.valueOf(i2));
                        try {
                            tempNode = tempNode.branch.get(map.get(property).get(i2));
                        }catch (IndexOutOfBoundsException ignored) { }
                    }
                    for (int i3 = 1; i3 <= i; i3++) {
                        if (tempNode.branch.size() > i3) {
                            break;
                        }
                        tempNode.branch.add(new PitClassTreeNode("NONE"));
                    }
                    try {
                        tempNode.branch.set(map.get(property).get(i - 1), new PitClassTreeNode(property.name));
                    }catch (Exception LolezignoreproblemandwrapintrycatchLol) { }
                }
            }
        }

        return new PitClassDataTree(baseNode);

    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalPoints", totalPoints);
        map.put("assignedPoints", assignedPoints);
        map.put("tree", tree);
        map.put("exp", exp);
        map.put("level", level);
        return map;
    }

    public static PitClassData deserialize(Map<String, Object> map) {
        return new PitClassData((PitClassDataTree) map.get("tree"));
    }
}
