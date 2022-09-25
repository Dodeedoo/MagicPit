package me.dodeedoo.magicpit.classes.tree;

import me.dodeedoo.magicpit.classes.list.ExampleClass;

import java.util.ArrayList;
import java.util.List;

public class PitClassTreeNode {

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
}
