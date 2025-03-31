package com.playground.data_structures.trees;

import java.util.ArrayList;
import java.util.List;

public class NestedSets {

    public Node node1;

    public static class Node
    {
        Node(String data){
            this.data = data;
        }
        String data;
        int lft;
        int rgt;
        List<Node> children = new ArrayList<>();
    }

    public void buildTree(){
        buildNodes();
        assignLeftRight(this.node1, 0);
    }

    private void buildNodes() {
        this.node1 = new Node("node1");
        Node node1_1 = new Node("node1_1");
        Node node1_1_1 = new Node("node1_1_1");
        Node node1_1_2 = new Node("node1_1_2");

        Node node1_2 = new Node("node1_2");
        Node node1_2_1 = new Node("node1_2_1");
        Node node1_2_2 = new Node("node1_2_2");

        this.node1.children.add(node1_1);
        this.node1.children.add(node1_2);

        node1_1.children.add(node1_1_1);
        node1_1.children.add(node1_1_2);

        node1_2.children.add(node1_2_1);
        node1_2.children.add(node1_2_2);
    }

    public int assignLeftRight(final Node node, int i){
        i++;
        node.lft = i;
        for(final Node child : node.children){
            i = assignLeftRight(child, i);
        }
        i++;
        node.rgt = i;
        return i;
    }

}
