package me.dodeedoo.magicpit.classes;

import me.dodeedoo.magicpit.classes.tree.PitClassDataTree;
import me.dodeedoo.magicpit.classes.tree.PitClassTreeNode;

import java.util.HashMap;
import java.util.List;

public class PitClassData {

    public Integer totalPoints;
    public Integer assignedPoints;
    public PitClassDataTree tree;

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

    public PitClassData(HashMap<PitClassProperty, List<Integer>> map) {
        this.tree = constructTreeFromNodeMap(map);
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
                if (map.get(property).size() == i) {
                    baseNode.branch.add(new PitClassTreeNode(property.name));
                }
            }
        }
        return new PitClassDataTree(baseNode);
    }
}
