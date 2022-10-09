package me.dodeedoo.magicpit.classes.tree;

import me.dodeedoo.magicpit.classes.PitClassData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitClassDataTree implements ConfigurationSerializable {

    public PitClassTreeNode baseNode;

    public PitClassTreeNode indexTo(List<Integer> index) {
        if (index.isEmpty()) {
            return baseNode;
        }
        PitClassTreeNode node = new PitClassTreeNode();
        try {
            node = baseNode.branch.get(index.get(0));
            index.remove(0);
//            for (PitClassTreeNode node1 : baseNode.branch) {
//                StringBuilder name = new StringBuilder(node1.name);
//                if (!node1.branch.isEmpty()) {
//                    for (PitClassTreeNode node2 : baseNode.branch) {
//                        name.append(" -> ").append(node2.name);
//                    }
//                }
//                Bukkit.broadcastMessage(name.toString());
//            }
            //Bukkit.broadcastMessage(String.valueOf(baseNode.branch));
            for (Integer integer : index) {
                //Bukkit.broadcastMessage(String.valueOf(integer));
                //Bukkit.broadcastMessage(node.branch.get(integer).name);
                node = node.branch.get(integer);
            }
        }catch (IndexOutOfBoundsException ignored) { }
        return node;
    }

    public Boolean isActivated(List<Integer> index) {
        PitClassTreeNode node = indexTo(index);
        return node.activated;
    }

    public void activate(List<Integer> index) {
        indexTo(index).setActivated(true);
    }

    public PitClassDataTree(PitClassTreeNode baseNode) {
        this.baseNode = baseNode;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("baseNode", baseNode);
        return map;
    }

    public static PitClassDataTree deserialize(Map<String, Object> map) {
        return new PitClassDataTree((PitClassTreeNode) map.get("baseNode"));
    }
}
