package me.dodeedoo.magicpit.classes.tree;

import java.util.List;

public class PitClassDataTree {

    public PitClassTreeNode baseNode;

    public PitClassTreeNode indexTo(List<Integer> index) {
        PitClassTreeNode node = new PitClassTreeNode();
        try {
            node = baseNode.branch.get(index.get(0));
            for (int i = 1; i <= index.size(); i++) {
                node = node.branch.get(i);
            }
        }catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
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

}
