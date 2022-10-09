package me.dodeedoo.magicpit.classes.tree;

import me.dodeedoo.magicpit.classes.PitClassData;
import me.dodeedoo.magicpit.classes.list.ExampleClass;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitClassTreeNode implements ConfigurationSerializable {

    public Boolean activated = false;
    public List<PitClassTreeNode> branch = new ArrayList<>();
    public String name = "none";

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public void setBranch(List<PitClassTreeNode> branch) {
        this.branch = branch;
    }

    public void addBranch(PitClassTreeNode node) {
        this.branch.add(node);
    }

    public void setName(String name) {
        this.name = name;
    }

    public PitClassTreeNode() {

    }

    public PitClassTreeNode(String name) {
        this.name = name;
    }

    public PitClassTreeNode(String name, boolean activated, List<PitClassTreeNode> branch) {
        this.branch = branch;
        this.activated = activated;
        this.name = name;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("activated", activated);
        map.put("branch", branch);
        map.put("name", name);
        return map;
    }

    public static PitClassTreeNode deserialize(Map<String, Object> map) {
        return new PitClassTreeNode((String) map.get("name"), (Boolean) map.get("activated"), (List<PitClassTreeNode>) map.get("branch"));
    }
}
